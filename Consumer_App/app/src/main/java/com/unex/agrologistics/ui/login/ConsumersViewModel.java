package com.unex.agrologistics.ui.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.unex.agrologistics.data.repository.ConsumerRepository;
import com.unex.agrologistics.model.Consumer;
import com.unex.agrologistics.model.Producer;

import java.util.List;

public class ConsumersViewModel extends AndroidViewModel {

    // LiveData of the consumers
    private LiveData<List<Consumer>> mAllConsumers;

    // Consumer repository
    private ConsumerRepository mRepository;

    /**
     * ConsumersViewModel constructor
     * @param application Application
     */
    public ConsumersViewModel(Application application) {
        super(application);
        // Get repository instance
        mRepository = ConsumerRepository.getInstance(application);
        // Get all producers
        mAllConsumers = mRepository.getAllConsumers();
    }

    /**
     * Get all consumers
     * @return LiveData list with all consumers in the database
     */
    @SuppressWarnings("unused")
    public LiveData<List<Consumer>> getAllConsumers() {
        return mAllConsumers;
    }

    /**
     * Get a consumer given an email
     * @param email Consumer email to filter
     * @return LiveData of the consumer stored in the database with a given email
     */
    public LiveData<Consumer> getConsumerByEmail(String email) {
        return mRepository.getConsumerByEmail(email);
    }

    /**
     * Get a consumer given an id
     * @param id Consumer id to filter
     * @return LiveData of the consumer stored in the database with a given id
     */
    public LiveData<Consumer> getConsumerById(int id) {
        return mRepository.getConsumerById(id);
    }
}