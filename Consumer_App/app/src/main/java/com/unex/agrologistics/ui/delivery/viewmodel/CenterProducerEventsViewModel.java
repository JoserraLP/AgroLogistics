package com.unex.agrologistics.ui.delivery.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unex.agrologistics.data.repository.ConsumerEventRepository;
import com.unex.agrologistics.model.ConsumerEvent;
import com.unex.agrologistics.model.ProducerEvent;

import java.util.List;

public class CenterProducerEventsViewModel extends AndroidViewModel {

    // Consumer event repository
    ConsumerEventRepository mRepository;

    // Consumer events in selected date
    private MutableLiveData<List<ConsumerEvent>> selected = new MutableLiveData<>();

    // Local variables
    private String date = "";
    private ConsumerEvent newEvent = new ConsumerEvent();
    private int logistic_center_id = -1;

    /**
     * EventsViewModel constructor
     * @param application Application
     */
    public CenterProducerEventsViewModel(Application application) {
        super(application);
        // Get repository instance
        mRepository = ConsumerEventRepository.getInstance(application);
    }

    /**
     * Get all consumer events from logistic center between two given dates
     * @param ini_date consumer events minimum date to filter
     * @param fin_date consumer events maximum date to filter
     * @return LiveData list with all consumer events from logistic center between two given dates
     */
    public LiveData<List<ConsumerEvent>> getConsumerEventsFromLogisticCenterByDate(String ini_date, String fin_date) {
        return mRepository.getConsumerEventsFiltered(logistic_center_id, ini_date, fin_date, newEvent.getStorage_type());
    }

    /**
     * Get all consumer events from logistic center between two given dates
     * @param month consumer events minimum date to filter
     * @return LiveData list with all consumer events from logistic center between two given dates
     */
    public LiveData<List<ConsumerEvent>> getConsumerEventsFromLogisticCenterByDay(String year, String month, String day) {
        return mRepository.getConsumerEventsFromLogisticCenterByDay(logistic_center_id, year, month, day, newEvent.getStorage_type());
    }

    /**
     * Select a list of consumer events in a given date
     * @param date consumer events minimum date to filter
     */
    public void select(String date) {
        this.date = date;
    }

    /**
     * Sets a new logistic center identifier to filter consumer events
     * @param id new logistic center identifier
     */
    public void setId(int id) {
        this.logistic_center_id = id;
        this.newEvent.setLogistic_center_id(id);
    }

    /**
     * Returns the consumer event
     * @return ConsumerEvent with the details specified by the user
     */
    public ConsumerEvent getNewEvent() {
        return this.newEvent;
    }

    /**
     * Sets a new consumer event
     * @param newEvent new consumer event
     */
    public void setNewEvent(ConsumerEvent newEvent) {
        this.newEvent = newEvent;
    }

    /**
     * Get selected logistic center item
     * @return LiveData list of consumer events selected
     */
    public LiveData<List<ConsumerEvent>> getSelected() {
        return selected;
    }

    /**
     * Get selected date
     * @return LiveData selected string date
     */
    public String getSelectedDate() {
        return date;
    }

    /**
     * Insert producer event on the db
     */
    public void insertConsumerEvent() {
        mRepository.insertConsumerEvent(newEvent);
    }

    /**
     * Perform a POST method to store the producer event on the API
     */
    public void postConsumerEvent() {
        mRepository.postConsumerEvent(newEvent);
    }
}
