package com.unex.agrologistics.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unex.agrologistics.data.local.AppDatabase;
import com.unex.agrologistics.data.remote.APIRetrofitClient;
import com.unex.agrologistics.data.remote.PostResponse;
import com.unex.agrologistics.data.remote.ConsumerEventResponse;
import com.unex.agrologistics.data.remote.ConsumerEventResponseItem;
import com.unex.agrologistics.model.Consumer;
import com.unex.agrologistics.model.ConsumerEvent;
import com.unex.agrologistics.model.ProducerEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsumerEventRepository {

    // TAG for Log messaging
    private static final String TAG = ConsumerEventRepository.class.getSimpleName();

    // Singleton class instance
    private static ConsumerEventRepository mInstance;

    // Retrofit places API
    private APIRetrofitClient retrofit = APIRetrofitClient.getInstance();

    // Local database
    private AppDatabase database;

    /**
     * Get the ConsumerEventRepository Singleton instance
     *
     * @param application Application context
     * @return ConsumerEventRepository Singleton instance
     */
    public static synchronized ConsumerEventRepository getInstance(Application application) {
        if (mInstance == null) {
            mInstance = new ConsumerEventRepository(application);
        }

        return mInstance;
    }

    /**
     * ConsumerEventRepository constructor
     *
     * @param application Application
     */
    private ConsumerEventRepository(Application application) {
        database = AppDatabase.getDatabase(application);
    }

    /**
     * Return all the consumer events of a given consumer stored in the database
     *
     * @param consumer_id ConsumerEvent consumer identifier to filter
     * @return LiveData List of all the consumer events of a given consumer stored in the database
     */
    public LiveData<List<ConsumerEvent>> getConsumerEventsByConsumer(int consumer_id) {
        return database.getConsumerEventDAO().getFromConsumer(consumer_id);
    }

    /**
     * Return all the consumer events of a given logistic center in a given date stored in the database
     *
     * @param logistic_center_id ConsumerEvent logistic center identifier to filter
     * @param ini_date           consumer events minimum date to filter
     * @param fin_date           consumer events maximum date to filter
     * @return LiveData List of all the consumer events of a given logistic center in a given date stored in the database
     */
    public LiveData<List<ConsumerEvent>> getConsumerEventsFromLogisticCenterByDate(int logistic_center_id, String ini_date, String fin_date) {
        return database.getConsumerEventDAO().getFromLogisticCenterByDate(logistic_center_id, ini_date, fin_date);
    }

    /**
     * Return all the consumer events of a given logistic center in a given date stored in the database
     *
     * @param logistic_center_id ConsumerEvent logistic center identifier to filter
     * @param ini_date           consumer events minimum date to filter
     * @param fin_date           consumer events maximum date to filter
     * @param storage_type       consumer events storage type to filter
     * @return LiveData List of all the consumer events of a given logistic center in a given date stored in the database
     */
    public LiveData<List<ConsumerEvent>> getConsumerEventsFiltered(int logistic_center_id, String ini_date, String fin_date, String storage_type) {
        return database.getConsumerEventDAO().getFromLogisticCenterByDateAndType(logistic_center_id, ini_date, fin_date, storage_type);
    }

    /**
     * Return all the consumer events of a given logistic center in a given date stored in the database
     *
     * @param logistic_center_id ConsumerEvent logistic center identifier to filter
     * @param year               consumer events year to filter
     * @param month              consumer events month to filter
     * @param day                consumer events day to filter
     * @param storage_type       consumer events storage type to filter
     * @return LiveData List of all the consumer events of a given logistic center in a given date stored in the database
     */
    public LiveData<List<ConsumerEvent>> getConsumerEventsFromLogisticCenterByDay(int logistic_center_id, String year, String month, String day, String storage_type) {
        return database.getConsumerEventDAO().getFromLogisticCenterByDayAndType(logistic_center_id, year + "-" + month + "-" + day + " 00:00:00", year + "-" + month + "-" + day + " 23:59:59", storage_type);
    }

    /**
     * Return a consumer event stored in the database given an identifier
     *
     * @param id ConsumerEvent identifier to filter
     * @return LiveData of the consumer event stored in the database with a given identifier
     */
    @SuppressWarnings("unused")
    public LiveData<ConsumerEvent> getConsumerEventById(int id) {
        return database.getConsumerEventDAO().get(id);
    }

    /**
     * Load the producer events from the API and stores it in the database
     */
    public void loadConsumerEvents() {
        // Make the retrofit call to the service
        Call<ConsumerEventResponse> consumerEventResponseCall = retrofit.getConsumerEventServiceAPI().getConsumerEvents();

        // Enqueue the response
        consumerEventResponseCall.enqueue(new Callback<ConsumerEventResponse>() {
            @Override
            public void onResponse(@NonNull Call<ConsumerEventResponse> call, @NonNull Response<ConsumerEventResponse> response) {
                if (response.isSuccessful()) {
                    // Retrieve the response body
                    ConsumerEventResponse consumerEventResponse = response.body();

                    assert consumerEventResponse != null;

                    // Retrieve the results of the body
                    ArrayList<ConsumerEventResponseItem> listConsumerEvents = consumerEventResponse.getMessage();

                    if (listConsumerEvents != null) {
                        for (ConsumerEventResponseItem consumerEventResponseItem : listConsumerEvents) {
                            // Insert the producer events in the database
                            insertConsumerEvent(new MutableLiveData<>(processResponseItem(consumerEventResponseItem)));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ConsumerEventResponse> call, @NonNull Throwable t) {
                Log.d(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    /**
     * Parse a ConsumerEventResponseItem object to a ConsumerEvent object
     *
     * @param consumerEventResponseItem ConsumerEventResponseItem object
     * @return Parsed ConsumerEvent object
     */
    private ConsumerEvent processResponseItem(ConsumerEventResponseItem consumerEventResponseItem) {
        // Create the Producer Event item with its attributes
        ConsumerEvent consumerEvent = new ConsumerEvent();

        consumerEvent.setId(consumerEventResponseItem.getId());
        consumerEvent.setProduct_id(consumerEventResponseItem.getProduct_id());
        consumerEvent.setProduct_name(consumerEventResponseItem.getProduct_name());
        consumerEvent.setLogistic_center_id(consumerEventResponseItem.getLogistic_center_id());
        consumerEvent.setLogistic_center_name(consumerEventResponseItem.getLogistic_center_name());
        consumerEvent.setConsumer_id(consumerEventResponseItem.getConsumer_id());
        consumerEvent.setConsumer_name(consumerEventResponseItem.getConsumer_name());
        consumerEvent.setProduct_category(consumerEventResponseItem.getProduct_category());
        consumerEvent.setAmount_kg(consumerEventResponseItem.getAmount_kg());
        consumerEvent.setDate(consumerEventResponseItem.getDate());
        consumerEvent.setPrice(consumerEventResponseItem.getPrice());
        consumerEvent.setStorage_type(consumerEventResponseItem.getStorage_type());

        return consumerEvent;
    }

    /**
     * Insert a ConsumerEvent in the database
     *
     * @param consumerEvent LiveData of the ConsumerEvent object
     */
    private void insertConsumerEvent(LiveData<ConsumerEvent> consumerEvent) {
        AppDatabase.databaseWriteExecutor.execute(() -> database.getConsumerEventDAO().insert(consumerEvent.getValue()));
    }

    /**
     * Insert a ConsumerEvent in the database
     *
     * @param consumerEvent ConsumerEvent object to insert
     */
    public void insertConsumerEvent(ConsumerEvent consumerEvent) {
        AppDatabase.databaseWriteExecutor.execute(() -> database.getConsumerEventDAO().insert(consumerEvent));
    }

    public void postConsumerEvent(ConsumerEvent consumerEvent) {
        // Perform post method to the API
        Call<PostResponse> consumerEventResponseCall = retrofit.getConsumerEventServiceAPI().postConsumerEvent(consumerEvent);

        // Enqueue the response
        consumerEventResponseCall.enqueue(new Callback<PostResponse>() {
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
