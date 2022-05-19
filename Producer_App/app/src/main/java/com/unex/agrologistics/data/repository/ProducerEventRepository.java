package com.unex.agrologistics.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unex.agrologistics.data.local.AppDatabase;
import com.unex.agrologistics.data.remote.APIRetrofitClient;
import com.unex.agrologistics.data.remote.PostResponse;
import com.unex.agrologistics.data.remote.ProducerEventResponse;
import com.unex.agrologistics.data.remote.ProducerEventResponseItem;
import com.unex.agrologistics.model.ProducerEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProducerEventRepository {

    // TAG for Log messaging
    private static final String TAG = ProducerEventRepository.class.getSimpleName();

    // Singleton class instance
    private static ProducerEventRepository mInstance;

    // Retrofit places API
    private APIRetrofitClient retrofit = APIRetrofitClient.getInstance();

    // Local database
    private AppDatabase database;

    /**
     * Get the ProducerEventRepository Singleton instance
     *
     * @param application Application context
     * @return ProducerEventRepository Singleton instance
     */
    public static synchronized ProducerEventRepository getInstance(Application application) {
        if (mInstance == null) {
            mInstance = new ProducerEventRepository(application);
        }

        return mInstance;
    }

    /**
     * ProducerEventRepository constructor
     *
     * @param application Application
     */
    private ProducerEventRepository(Application application) {
        database = AppDatabase.getDatabase(application);
    }

    /**
     * Return all the producer events of a given producer stored in the database
     *
     * @param producer_id ProducerEvent producer identifier to filter
     * @return LiveData List of all the producer events of a given producer stored in the database
     */
    public LiveData<List<ProducerEvent>> getProducerEventsByProducer(int producer_id) {
        return database.getProducerEventDAO().getFromProducer(producer_id);
    }

    /**
     * Return all the producer events of a given logistic center in a given date stored in the database
     *
     * @param logistic_center_id ProducerEvent logistic center identifier to filter
     * @param ini_date           producer events minimum date to filter
     * @param fin_date           producer events maximum date to filter
     * @return LiveData List of all the producer events of a given logistic center in a given date stored in the database
     */
    public LiveData<List<ProducerEvent>> getProducerEventsFromLogisticCenterByDate(int logistic_center_id, String ini_date, String fin_date) {
        return database.getProducerEventDAO().getFromLogisticCenterByDate(logistic_center_id, ini_date, fin_date);
    }

    /**
     * Return all the producer events of a given logistic center in a given date stored in the database
     *
     * @param logistic_center_id ProducerEvent logistic center identifier to filter
     * @param ini_date           producer events minimum date to filter
     * @param fin_date           producer events maximum date to filter
     * @param storage_type       producer events storage type to filter
     * @return LiveData List of all the producer events of a given logistic center in a given date stored in the database
     */
    public LiveData<List<ProducerEvent>> getProducerEventsFiltered(int logistic_center_id, String ini_date, String fin_date, String storage_type) {
        return database.getProducerEventDAO().getFromLogisticCenterByDateAndType(logistic_center_id, ini_date, fin_date, storage_type);
    }

    /**
     * Return all the producer events of a given logistic center in a given date stored in the database
     *
     * @param logistic_center_id ProducerEvent logistic center identifier to filter
     * @param year               producer events year to filter
     * @param month              producer events month to filter
     * @param day                producer events day to filter
     * @param storage_type       producer events storage type to filter
     * @return LiveData List of all the producer events of a given logistic center in a given date stored in the database
     */
    public LiveData<List<ProducerEvent>> getProducerEventsFromLogisticCenterByDay(int logistic_center_id, String year, String month, String day, String storage_type) {
        return database.getProducerEventDAO().getFromLogisticCenterByDayAndType(logistic_center_id, year + "-" + month + "-" + day + " 00:00:00", year + "-" + month + "-" + day + " 23:59:59", storage_type);
    }

    /**
     * Return a producer event stored in the database given an identifier
     *
     * @param id ProducerEvent identifier to filter
     * @return LiveData of the producer event stored in the database with a given identifier
     */
    @SuppressWarnings("unused")
    public LiveData<ProducerEvent> getProducerEventById(int id) {
        return database.getProducerEventDAO().get(id);
    }

    /**
     * Load the producer events from the API and stores it in the database
     */
    public void loadProducerEvents() {
        // Make the retrofit call to the service
        Call<ProducerEventResponse> producerEventResponseCall = retrofit.getProducerEventServiceAPI().getProducerEvents();

        // Enqueue the response
        producerEventResponseCall.enqueue(new Callback<ProducerEventResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProducerEventResponse> call, @NonNull Response<ProducerEventResponse> response) {
                if (response.isSuccessful()) {
                    // Retrieve the response body
                    ProducerEventResponse producerEventResponse = response.body();

                    assert producerEventResponse != null;

                    // Retrieve the results of the body
                    ArrayList<ProducerEventResponseItem> listProducerEvents = producerEventResponse.getMessage();

                    if (listProducerEvents != null) {
                        for (ProducerEventResponseItem producerEventResponseItem : listProducerEvents) {
                            // Insert the producer events in the database
                            insertProducerEvent(new MutableLiveData<>(processResponseItem(producerEventResponseItem)));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProducerEventResponse> call, @NonNull Throwable t) {
                Log.d(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    /**
     * Parse a ProducerEventResponseItem object to a ProducerEvent object
     *
     * @param producerEventResponseItem ProducerEventResponseItem object
     * @return Parsed ProducerEvent object
     */
    private ProducerEvent processResponseItem(ProducerEventResponseItem producerEventResponseItem) {
        // Create the Producer Event item with its attributes
        ProducerEvent producerEvent = new ProducerEvent();

        producerEvent.setId(producerEventResponseItem.getId());
        producerEvent.setProduct_id(producerEventResponseItem.getProduct_id());
        producerEvent.setProduct_name(producerEventResponseItem.getProduct_name());
        producerEvent.setLogistic_center_id(producerEventResponseItem.getLogistic_center_id());
        producerEvent.setLogistic_center_name(producerEventResponseItem.getLogistic_center_name());
        producerEvent.setProducer_id(producerEventResponseItem.getProducer_id());
        producerEvent.setProducer_name(producerEventResponseItem.getProducer_name());
        producerEvent.setProduct_category(producerEventResponseItem.getProduct_category());
        producerEvent.setAmount_kg(producerEventResponseItem.getAmount_kg());
        producerEvent.setDate(producerEventResponseItem.getDate());
        producerEvent.setPrice(producerEventResponseItem.getPrice());
        producerEvent.setStorage_type(producerEventResponseItem.getStorage_type());

        return producerEvent;
    }

    /**
     * Insert a ProducerEvent in the database
     *
     * @param producerEvent LiveData of the ProducerEvent object
     */
    private void insertProducerEvent(LiveData<ProducerEvent> producerEvent) {
        AppDatabase.databaseWriteExecutor.execute(() -> database.getProducerEventDAO().insert(producerEvent.getValue()));
    }

    /**
     * Insert a ProducerEvent in the database
     *
     * @param producerEvent ProducerEvent object to insert
     */
    public void insertProducerEvent(ProducerEvent producerEvent) {
        AppDatabase.databaseWriteExecutor.execute(() -> database.getProducerEventDAO().insert(producerEvent));
    }


    public void postProducerEvent(ProducerEvent producerEvent) {
        // Perform post method to the API
        Call<PostResponse> producerEventResponseCall = retrofit.getProducerEventServiceAPI().postProducerEvent(producerEvent);

        // Enqueue the response
        producerEventResponseCall.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(@NonNull Call<PostResponse> call, @NonNull Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("New", response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostResponse> call, @NonNull Throwable t) {
                Log.d(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
