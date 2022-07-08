package com.unex.agrologistics.ui.delivery.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unex.agrologistics.R;
import com.unex.agrologistics.model.ConsumerEvent;
import com.unex.agrologistics.ui.delivery.ListHoursAdapter;
import com.unex.agrologistics.ui.delivery.viewmodel.CenterConsumerEventsViewModel;
import com.unex.agrologistics.ui.delivery.viewmodel.CentersViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DeliveryHourFragment extends Fragment {

    /**
     * onCreateView method
     *
     * @param inflater           Fragment inflater
     * @param container          Fragment container
     * @param savedInstanceState Bundle where instance is saved
     * @return Fragment View
     */
    @SuppressLint({"UseRequireInsteadOfGet", "SimpleDateFormat"})
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate fragment
        View root = inflater.inflate(R.layout.fragment_delivery_hour, container, false);

        // UI fields
        TextView logisticCenterName = root.findViewById(R.id.logisticCenterName);

        // Get the CenterProducerEventsViewModel
        CenterConsumerEventsViewModel eventsViewModel = new ViewModelProvider(requireActivity()).get(
                CenterConsumerEventsViewModel.class);

        // Get the CentersViewModel
        CentersViewModel centersViewModel = new ViewModelProvider(requireActivity()).get(
                CentersViewModel.class);

        // Get selected and set UI fields values
        centersViewModel.getSelected().observe(requireActivity(), logisticCenterItem ->
                logisticCenterName.setText(logisticCenterItem.getName()));

        ArrayList<Hour> hours = new ArrayList<>();

        // Create the ListHoursAdapter object
        ListHoursAdapter listHoursAdapter = new ListHoursAdapter(this.getContext(), eventsViewModel);

        // Get all the news and save it to the adapter
        listHoursAdapter.addNewsList(hours);

        // RecyclerView settings
        RecyclerView recyclerView = root.findViewById(R.id.hoursRecyclerView);
        recyclerView.setAdapter(listHoursAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(null, 4);
        recyclerView.setLayoutManager(layoutManager);

        // Set initial date as selected date
        String ini_date = eventsViewModel.getSelectedDate();
        Date date = new Date();

        // Define the date format
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(ini_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Get calendar instance
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        // Increase the day on the calendar
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        // Set end date
        @SuppressLint("SimpleDateFormat") String fin_date = new
                SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        // Get producer events by range of dates
        eventsViewModel.getConsumerEventsFromLogisticCenterByDate(ini_date + " 00:00:00",
                fin_date + " 00:00:00").observe(requireActivity(), producerEventsList -> {

            // Define variable to store all the hours
            Map<String, Integer> fullHours = new HashMap<>();

            // Initialize the number of events for all hours to 0
            for (ConsumerEvent consumerEvent : producerEventsList)
                fullHours.put(consumerEvent.getDate().substring(11, 16), 0);

            // Define variable to store the free hours
            ArrayList<Hour> freeHours = new ArrayList<>();

            // Iterate over the allowed hours and add them
            for (int i = 8; i < 22; i++) {
                String s = i < 10 ? "0" + i : "" + i;
                for (int j = 0; j < 2; j++) {
                    String hour = j == 0 ? s + ":00" : s + ":30";
                    freeHours.add(new Hour(hour, fullHours.get(hour) == null));
                }
            }
            // Add
            listHoursAdapter.addNewsList(freeHours);
        });

        return root;
    }

    public class Hour {

        // Define hour variable
        private String hour;
        private boolean free;

        /**
         * Hour initializer
         */
        public Hour() {
            hour = "";
            free = true;
        }

        /**
         * Hour parametrized
         * @param hour Hour
         * @param free Is free
         */
        public Hour(String hour, boolean free) {
            this.hour = hour;
            this.free = free;
        }

        /**
         * Return the hour
         * @return Hour
         */
        public String getHour() {
            return hour;
        }

        /**
         * Set the hour
         * @param hour Hour
         */
        @SuppressWarnings("unused")
        public void setHour(String hour) {
            this.hour = hour;
        }

        /**
         * Return if the hour is free
         * @return Is free
         */
        public boolean isFree() {
            return free;
        }

        /**
         * Set the hour free status
         * @param free Free status
         */
        public void setFree(boolean free) {
            this.free = free;
        }
    }
}
