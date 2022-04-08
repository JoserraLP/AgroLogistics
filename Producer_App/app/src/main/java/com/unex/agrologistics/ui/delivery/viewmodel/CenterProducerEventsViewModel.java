package com.unex.agrologistics.ui.delivery.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unex.agrologistics.data.repository.ProducerEventRepository;
import com.unex.agrologistics.model.ProducerEvent;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CenterProducerEventsViewModel extends AndroidViewModel {

    // Producer event repository
    ProducerEventRepository mRepository;

    // Producer events in selected date
    private MutableLiveData<List<ProducerEvent>> selected = new MutableLiveData<>();

    // Local variables
    private String date = "";
    private ProducerEvent newEvent = new ProducerEvent();
    private int logistic_center_id = -1;

    /**
     * EventsViewModel constructor
     * @param application Application
     */
    public CenterProducerEventsViewModel(Application application) {
        super(application);
        // Get repository instance
        mRepository = ProducerEventRepository.getInstance(application);
    }

    /**
     * Get all producer events from logistic center between two given dates
     * @param ini_date producer events minimum date to filter
     * @param fin_date producer events maximum date to filter
     * @return LiveData list with all producer events from logistic center between two given dates
     */
    public LiveData<List<ProducerEvent>> getProducerEventsFromLogisticCenterByDate(String ini_date, String fin_date) {
        return mRepository.getProducerEventsFiltered(logistic_center_id, ini_date, fin_date, newEvent.getStorage_type());
    }

    /**
     * Get all producer events from logistic center between two given dates
     * @param month producer events minimum date to filter
     * @return LiveData list with all producer events from logistic center between two given dates
     */
    public LiveData<List<ProducerEvent>> getProducerEventsFromLogisticCenterByDay(String year, String month, String day) {
        return mRepository.getProducerEventsFromLogisticCenterByDay(logistic_center_id, year, month, day, newEvent.getStorage_type());
    }

    /**
     * Select a list of producer events in a given date
     * @param date producer events minimum date to filter
     */
    public void select(String date) {
        this.date = date;
    }

    /**
     * Sets a new logistic center identifier to filter producer events
     * @param id new logistic center identifier
     */
    public void setId(int id) {
        this.logistic_center_id = id;
        this.newEvent.setLogistic_center_id(id);
    }

    /**
     * Returns the producer event
     * @return ProducerEvent with the details specified by the user
     */
    public ProducerEvent getNewEvent() {
        return this.newEvent;
    }

    /**
     * Sets a new producer event
     * @param newEvent new producer event
     */
    public void setNewEvent(ProducerEvent newEvent) {
        this.newEvent = newEvent;
    }

    /**
     * Get selected logistic center item
     * @return LiveData list of producer events selected
     */
    public LiveData<List<ProducerEvent>> getSelected() {
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
    public void insertProducerEvent() {
        mRepository.insertProducerEvent(newEvent);
    }

    /**
     * Perform a POST method to store the producer event on the API
     */
    public void postProducerEvent() {
        mRepository.postProducerEvent(newEvent);
    }
}
