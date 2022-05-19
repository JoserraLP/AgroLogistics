package com.unex.agrologistics.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unex.agrologistics.data.local.AppDatabase;
import com.unex.agrologistics.data.remote.APIRetrofitClient;
import com.unex.agrologistics.data.remote.LogisticCenterResponse;
import com.unex.agrologistics.data.remote.LogisticCenterResponseItem;
import com.unex.agrologistics.model.LogisticCenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogisticCenterRepository {

    // TAG for Log messaging
    private static final String TAG = LogisticCenterRepository.class.getSimpleName();

    // Singleton class instance
    private static LogisticCenterRepository mInstance;

    // Retrofit places API
    private APIRetrofitClient retrofit = APIRetrofitClient.getInstance();

    // Local database
    private AppDatabase database;

    /**
     * Get the LogisticCenterRepository Singleton instance
     *
     * @param application Application context
     * @return LogisticCenterRepository Singleton instance
     */
    public static synchronized LogisticCenterRepository getInstance(Application application) {
        if (mInstance == null) {
            mInstance = new LogisticCenterRepository(application);
        }

        return mInstance;
    }

    /**
     * LogisticCenterRepository constructor
     *
     * @param application Application
     */
    private LogisticCenterRepository(Application application) {
        database = AppDatabase.getDatabase(application);
    }

    /**
     * Return all the logistic centers stored in the database
     *
     * @return LiveData List of all the logistic centers stored in the database
     */
    public LiveData<List<LogisticCenter>> getAllLogisticCenters() {
        return database.getLogisticCenterDAO().getAll();
    }

    /**
     * Return all the logistic centers stored in the database that match a search
     *
     * @return LiveData List of all the logistic centers stored in the database that match a search
     */
    public LiveData<List<LogisticCenter>> getLogisticCentersBySearch(String search) {
        return database.getLogisticCenterDAO().getByName("%" + search + "%");
    }

    /**
     * Return a logistic center stored in the database given an identifier
     *
     * @param id LogisticCenter identifier to filter
     * @return LiveData of the logistic center stored in the database with a given identifier
     */
    public LiveData<LogisticCenter> getLogisticCenterById(int id) {
        return database.getLogisticCenterDAO().get(id);
    }

    /**
     * Load the logistic centers from the API and stores it in the database
     */
    public void loadLogisticCenters() {
        // Make the retrofit call to the service
        Call<LogisticCenterResponse> logisticCenterResponseCall = retrofit.getLogisticCenterServiceAPI().getLogisticCenters();

        // Enqueue the response
        logisticCenterResponseCall.enqueue(new Callback<LogisticCenterResponse>() {
            @Override
            public void onResponse(@NonNull Call<LogisticCenterResponse> call, @NonNull Response<LogisticCenterResponse> response) {
                if (response.isSuccessful()) {
                    // Retrieve the response body
                    LogisticCenterResponse logisticCenterResponse = response.body();
                    assert logisticCenterResponse != null;

                    // Retrieve the results of the body
                    ArrayList<LogisticCenterResponseItem> listLogisticCenters = logisticCenterResponse.getMessage();

                    if (listLogisticCenters != null) {
                        for (LogisticCenterResponseItem logisticCenterResponseItem : listLogisticCenters) {
                            // Insert the logistic center in the database
                            insertLogisticCenter(new MutableLiveData<>(processResponseItem(logisticCenterResponseItem)));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LogisticCenterResponse> call, @NonNull Throwable t) {
                Log.d(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    /**
     * Parse a LogisticCenterResponseItem object to a LogisticCenter object
     *
     * @param logisticCenterResponseItem LogisticCenterResponseItem object
     * @return Parsed LogisticCenter object
     */
    private LogisticCenter processResponseItem(LogisticCenterResponseItem logisticCenterResponseItem) {
        // Create the Logistic Center item with its attributes
        // Email and Password empty as are not used
        LogisticCenter logisticCenter = new LogisticCenter();

        logisticCenter.setId(logisticCenterResponseItem.getId());
        logisticCenter.setName(logisticCenterResponseItem.getName());
        logisticCenter.setCapacity_kg(logisticCenterResponseItem.getCapacityKg());
        logisticCenter.setCooled_capacity_kg(logisticCenterResponseItem.getCooledCapacityKg());

        return logisticCenter;

    }

    /**
     * Insert a LogisticCenter in the database
     *
     * @param logisticCenter LiveData of the LogisticCenter object
     */
    private void insertLogisticCenter(LiveData<LogisticCenter> logisticCenter) {
        AppDatabase.databaseWriteExecutor.execute(() -> database.getLogisticCenterDAO().insert(logisticCenter.getValue()));
    }
}
