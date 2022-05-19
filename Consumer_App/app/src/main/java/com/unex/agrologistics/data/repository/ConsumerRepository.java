package com.unex.agrologistics.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unex.agrologistics.data.local.AppDatabase;
import com.unex.agrologistics.data.remote.APIRetrofitClient;
import com.unex.agrologistics.data.remote.ConsumerResponseItem;
import com.unex.agrologistics.data.remote.PostResponse;
import com.unex.agrologistics.data.remote.ConsumerResponse;
import com.unex.agrologistics.model.Consumer;
import com.unex.agrologistics.model.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsumerRepository {

    // TAG for Log messaging
    private static final String TAG = ConsumerRepository.class.getSimpleName();

    // Singleton class instance
    private static ConsumerRepository mInstance;

    // Retrofit places API
    private APIRetrofitClient retrofit = APIRetrofitClient.getInstance();

    // Local database
    private AppDatabase database;

    /**
     * Get the ConsumerRepository Singleton instance
     *
     * @param application Application context
     * @return ConsumerRepository Singleton instance
     */
    public static synchronized ConsumerRepository getInstance(Application application) {
        if (mInstance == null) {
            mInstance = new ConsumerRepository(application);
        }

        return mInstance;
    }

    /**
     * ConsumerRepository constructor
     *
     * @param application Application
     */
    private ConsumerRepository(Application application) {
        database = AppDatabase.getDatabase(application);
    }

    /**
     * Return all the consumers stored in the database
     *
     * @return LiveData List of all the consumers stored in the database
     */
    public LiveData<List<Consumer>> getAllConsumers() {
        return database.getConsumerDAO().getAll();
    }

    /**
     * Return a consumer stored in the database given an identifier
     *
     * @param id Consumer identifier to filter
     * @return LiveData of the consumer stored in the database with a given identifier
     */
    @SuppressWarnings("unused")
    public LiveData<Consumer> getConsumerById(int id) {
        return database.getConsumerDAO().get(id);
    }

    /**
     * Return a consumer stored in the database given an email
     *
     * @param email Consumer email to filter
     * @return LiveData of the consumer stored in the database with a given email
     */
    public LiveData<Consumer> getConsumerByEmail(String email) {
        return database.getConsumerDAO().getByEmail(email);
    }

    /**
     * Load the producers from the API and stores it in the database
     */
    public void loadProducers() {
        // Make the retrofit call to the service
        Call<ConsumerResponse> producerResponseCall = retrofit.getConsumerServiceAPI().getConsumers();

        // Enqueue the response
        producerResponseCall.enqueue(new Callback<ConsumerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ConsumerResponse> call, @NonNull Response<ConsumerResponse> response) {
                if (response.isSuccessful()) {
                    // Retrieve the response body
                    ConsumerResponse consumerResponse = response.body();
                    assert consumerResponse != null;

                    // Retrieve the results of the body
                    ArrayList<ConsumerResponseItem> listConsumers = consumerResponse.getMessage();

                    if (listConsumers != null) {
                        for (ConsumerResponseItem producerResponseItem : listConsumers) {
                            // Insert the producers in the database
                            insertConsumer(new MutableLiveData<>(processResponseItem(producerResponseItem)));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ConsumerResponse> call, @NonNull Throwable t) {
                Log.d(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    /**
     * Parse a ConsumerResponseItem object to a Producer object
     *
     * @param consumerResponseItem ConsumerResponseItem object
     * @return Parsed Consumer object
     */
    private Consumer processResponseItem(ConsumerResponseItem consumerResponseItem) {
        // Create the Producer item with its attributes
        Consumer consumer = new Consumer();

        consumer.setId(consumerResponseItem.getId());
        consumer.setName(consumerResponseItem.getName());
        consumer.setEmail(consumerResponseItem.getEmail());
        consumer.setPassword(consumerResponseItem.getPassword());

        return consumer;
    }

    /**
     * Insert a Consumer in the database
     *
     * @param consumer LiveData of the Consumer object
     */
    public void insertConsumer(LiveData<Consumer> consumer) {
        AppDatabase.databaseWriteExecutor.execute(() -> database.getConsumerDAO().insert(consumer.getValue()));
    }

    public void postConsumer(Consumer consumer) {
        // Perform post method to the API
        Call<PostResponse> consumerResponseCall = retrofit.getConsumerServiceAPI().postConsumer(consumer);

        // Enqueue the response
        consumerResponseCall.enqueue(new Callback<PostResponse>() {
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
