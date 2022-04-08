package com.unex.agrologistics.ui.events.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unex.agrologistics.data.repository.ProducerEventRepository;
import com.unex.agrologistics.model.ProducerEvent;

import java.util.List;

public class EventsViewModel extends AndroidViewModel {

    // LiveData of the news
    private LiveData<List<ProducerEvent>> mAllProducerEvents;

    // Selected news item
    private MutableLiveData<ProducerEvent> selected = new MutableLiveData<>();

    // Producer event repository
    ProducerEventRepository mRepository;

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
        mRepository = ProducerEventRepository.getInstance(application);
    }

    /**
     * Get all producer events
     * @return LiveData list with all producer events in the database
     */
    public LiveData<List<ProducerEvent>> getAllProducerEvents() {
        return mAllProducerEvents;
    }

    /**
     * Get selected producer event item
     * @return ProducerEvent item selected
     */
    public LiveData<ProducerEvent> getSelected() {
        return selected;
    }

    /**
     * Select a producer event item
     * @param producerEventItem Selected producer event item
     */
    public void select(ProducerEvent producerEventItem) {
        // Set value of producer event item
        selected.setValue(producerEventItem);
        // Store product id
        productId = producerEventItem.getProduct_id();
        // Store logistic center id
        logisticCenterId = producerEventItem.getLogistic_center_id();
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
     * Set producer id
     * @param id Producer id
     */
    public void setProducerId(int id) {
        mAllProducerEvents = mRepository.getProducerEventsByProducer(id);
    }
}