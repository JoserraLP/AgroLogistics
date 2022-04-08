package com.unex.agrologistics.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unex.agrologistics.data.local.AppDatabase;
import com.unex.agrologistics.data.remote.APIRetrofitClient;
import com.unex.agrologistics.data.remote.PostResponse;
import com.unex.agrologistics.data.remote.ProducerResponse;
import com.unex.agrologistics.data.remote.ProducerResponseItem;
import com.unex.agrologistics.model.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProducerRepository {

    // TAG for Log messaging
    private static final String TAG = ProducerRepository.class.getSimpleName();

    // Singleton class instance
    private static ProducerRepository mInstance;

    // Retrofit places API
    private APIRetrofitClient retrofit = APIRetrofitClient.getInstance();

    // Local database
    private AppDatabase database;

    /**
     * Get the ProducerRepository Singleton instance
     *
     * @param application Application context
     * @return ProducerRepository Singleton instance
     */
    public static synchronized ProducerRepository getInstance(Application application) {
        if (mInstance == null) {
            mInstance = new ProducerRepository(application);
        }

        return mInstance;
    }

    /**
     * ProducerRepository constructor
     *
     * @param application Application
     */
    private ProducerRepository(Application application) {
        database = AppDatabase.getDatabase(application);
    }

    /**
     * Return all the producers stored in the database
     *
     * @return LiveData List of all the producers stored in the database
     */
    public LiveData<List<Producer>> getAllProducers() {
        return database.getProducerDAO().getAll();
    }

    /**
     * Return a producer stored in the database given an identifier
     *
     * @param id Producer identifier to filter
     * @return LiveData of the producer stored in the database with a given identifier
     */
    @SuppressWarnings("unused")
    public LiveData<Producer> getProducerById(int id) {
        return database.getProducerDAO().get(id);
    }

    /**
     * Return a producer stored in the database given an email
     *
     * @param email Producer email to filter
     * @return LiveData of the producer stored in the database with a given email
     */
    public LiveData<Producer> getProducerByEmail(String email) {
        return database.getProducerDAO().getByEmail(email);
    }

    /**
     * Load the producers from the API and stores it in the database
     */
    public void loadProducers() {
        // Make the retrofit call to the service
        Call<ProducerResponse> producerResponseCall = retrofit.getProducerServiceAPI().getProducers();

        // Enqueue the response
        producerResponseCall.enqueue(new Callback<ProducerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProducerResponse> call, @NonNull Response<ProducerResponse> response) {
                if (response.isSuccessful()) {
                    // Retrieve the response body
                    ProducerResponse producerResponse = response.body();
                    assert producerResponse != null;

                    // Retrieve the results of the body
                    ArrayList<ProducerResponseItem> listProducers = producerResponse.getMessage();

                    if (listProducers != null) {
                        for (ProducerResponseItem producerResponseItem : listProducers) {
                            // Insert the producers in the database
                            insertProducer(new MutableLiveData<>(processResponseItem(producerResponseItem)));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProducerResponse> call, @NonNull Throwable t) {
                Log.d(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    /**
     * Parse a ProducerResponseItem object to a Producer object
     *
     * @param producerResponseItem ProducerResponseItem object
     * @return Parsed Producer object
     */
    private Producer processResponseItem(ProducerResponseItem producerResponseItem) {
        // Create the Producer item with its attributes
        Producer producer = new Producer();

        producer.setId(producerResponseItem.getId());
        producer.setName(producerResponseItem.getName());
        producer.setEmail(producerResponseItem.getEmail());
        producer.setPassword(producerResponseItem.getPassword());

        return producer;
    }

    /**
     * Insert a Producer in the database
     *
     * @param producer LiveData of the Producer object
     */
    public void insertProducer(LiveData<Producer> producer) {
        AppDatabase.databaseWriteExecutor.execute(() -> database.getProducerDAO().insert(producer.getValue()));
    }

    public void postProducer(Producer producer) {
        // Perform post method to the API
        Call<PostResponse> producerResponseCall = retrofit.getProducerServiceAPI().postProducer(producer);

        // Enqueue the response
        producerResponseCall.enqueue(new Callback<PostResponse>() {
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
