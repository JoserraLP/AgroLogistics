package com.unex.agrologistics.ui.events.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unex.agrologistics.data.repository.ConsumerEventRepository;
import com.unex.agrologistics.model.ConsumerEvent;
import com.unex.agrologistics.model.ProducerEvent;

import java.util.List;

public class EventsViewModel extends AndroidViewModel {

    // LiveData of the consumer events
    private LiveData<List<ConsumerEvent>> mAllConsumerEvents;

    // Selected news item
    private MutableLiveData<ConsumerEvent> selected = new MutableLiveData<>();

    // Consumer event repository
    ConsumerEventRepository mRepository;

    // Set default product and logistic center id
    private int productId = -1;
    private int logisticCenterId = -1;

    /**
     * EventsViewModel constructor
     * @param application Application
     */
    public EventsViewModel(Application application) {
        super(application);
        // Get repository instance
        mRepository = ConsumerEventRepository.getInstance(application);
    }

    /**
     * Get all consumer events
     * @return LiveData list with all consumer events in the database
     */
    public LiveData<List<ConsumerEvent>> getAllConsumerEvents() {
        return mAllConsumerEvents;
    }

    /**
     * Get selected consumer event item
     * @return ConsumerEvent item selected
     */
    public LiveData<ConsumerEvent> getSelected() {
        return selected;
    }

    /**
     * Select a consumer event item
     * @param consumerEventItem Selected consumer event item
     */
    public void select(ConsumerEvent consumerEventItem) {
        // Set value of producer event item
        selected.setValue(consumerEventItem);
        // Store product id
        productId = consumerEventItem.getProduct_id();
        // Store logistic center id
        logisticCenterId = consumerEventItem.getLogistic_center_id();
    }

    /**
     * Return product id
     * @return Product id
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Return logistic center id
     * @return Logistic center id
     */
    public int getLogisticCenterId() {
        return logisticCenterId;
    }

    /**
     * Set consumer id
     * @param id Consumer id
     */
    public void setConsumerId(int id) {
        mAllConsumerEvents = mRepository.getConsumerEventsByConsumer(id);
    }
}