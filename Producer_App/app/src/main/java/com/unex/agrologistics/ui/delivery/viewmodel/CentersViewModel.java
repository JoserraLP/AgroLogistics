package com.unex.agrologistics.ui.delivery.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unex.agrologistics.data.repository.LogisticCenterRepository;
import com.unex.agrologistics.model.LogisticCenter;

import java.util.List;

public class CentersViewModel extends AndroidViewModel {

    // LiveData of the logistic centers
    private LiveData<List<LogisticCenter>> mAllLogisticCenters;

    // Selected news item
    private MutableLiveData<LogisticCenter> selected = new MutableLiveData<>();

    // Logistic centers repository
    LogisticCenterRepository mRepository;

    /**
     * CentersViewModel constructor
     * @param application Application
     */
    public CentersViewModel(Application application) {
        super(application);
        // Get repository instance
        mRepository = LogisticCenterRepository.getInstance(application);
    }

    /**
     * Get all logistic centers
     * @return LiveData list with all logistic centers in the database
     */
    public LiveData<List<LogisticCenter>> getAllLogisticCenters() {
        return mRepository.getAllLogisticCenters();
    }

    /**
     * Get all logistic centers
     * @return LiveData list with all logistic centers in the database
     */
    public LiveData<List<LogisticCenter>> getLogisticCentersBySearch(String search) {
        return mRepository.getLogisticCentersBySearch(search);
    }

    /**
     * Get a logistic center given an identifier
     * @param id Logistic center identifier to filter
     * @return LiveData of the logistic center stored in the database with a given identifier
     */
    public LiveData<LogisticCenter> getLogisticCenterById(int id) {
        return mRepository.getLogisticCenterById(id);
    }

    /**
     * Get selected logistic center item
     * @return LogisticCenter item selected
     */
    public LiveData<LogisticCenter> getSelected() {
        return selected;
    }

    /**
     * Select a logistic center item
     * @param logisticCenterItem Selected logistic center item
     */
    public void select(LogisticCenter logisticCenterItem) {
        selected.setValue(logisticCenterItem);
    }
}
