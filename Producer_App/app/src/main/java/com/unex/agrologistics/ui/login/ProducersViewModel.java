package com.unex.agrologistics.ui.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.unex.agrologistics.data.repository.ProducerRepository;
import com.unex.agrologistics.model.Producer;

import java.util.List;

public class ProducersViewModel extends AndroidViewModel {

    // LiveData of the products
    private LiveData<List<Producer>> mAllProducers;

    // Producer repository
    private ProducerRepository mRepository;

    /**
     * ProducersViewModel constructor
     * @param application Application
     */
    public ProducersViewModel(Application application) {
        super(application);
        // Get repository instance
        mRepository = ProducerRepository.getInstance(application);
        // Get all producers
        mAllProducers = mRepository.getAllProducers();
    }

    /**
     * Get all producers
     * @return LiveData list with all producers in the database
     */
    @SuppressWarnings("unused")
    public LiveData<List<Producer>> getAllProducers() {
        return mAllProducers;
    }

    /**
     * Get a producer given an email
     * @param email Producer email to filter
     * @return LiveData of the producer stored in the database with a given email
     */
    public LiveData<Producer> getProducerByEmail(String email) {
        return mRepository.getProducerByEmail(email);
    }

    /**
     * Get a producer given an id
     * @param id Producer id to filter
     * @return LiveData of the producer stored in the database with a given id
     */
    public LiveData<Producer> getProducerById(int id) {
        return mRepository.getProducerById(id);
    }
}