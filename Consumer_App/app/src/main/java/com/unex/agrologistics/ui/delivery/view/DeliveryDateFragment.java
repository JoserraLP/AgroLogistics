package com.unex.agrologistics.ui.delivery.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.unex.agrologistics.R;
import com.unex.agrologistics.model.ConsumerEvent;
import com.unex.agrologistics.model.ProducerEvent;
import com.unex.agrologistics.ui.delivery.viewmodel.CenterProducerEventsViewModel;
import com.unex.agrologistics.ui.delivery.viewmodel.CentersViewModel;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DeliveryDateFragment extends Fragment implements OnNavigationButtonClickedListener {

    // Custom calendar
    CustomCalendar customCalendar;

    // Dates
    Map<Integer, Object> maps = new HashMap<>();
    
    // Context
    Context context;

    /**
     * onCreateView method
     * @param inflater Fragment inflater
     * @param container Fragment container
     * @param savedInstanceState Bundle where instance is saved
     * @return Fragment View
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Activity activity = getActivity();

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        //Find the currently focused view, so we can grab the correct window token from it.
        View v = activity.getCurrentFocus();

        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (v == null) {
            v = new View(activity);
        }
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        // Inflate fragment
        View root = inflater.inflate(R.layout.fragment_center_item, container, false);

        // Get the context
        context = this.getContext();
        // Get calendar instance
        Calendar calendar = Calendar.getInstance();

        // UI fields
        TextView logisticCenterName = root.findViewById(R.id.logisticCenterName);
        customCalendar = root.findViewById(R.id.custom_calendar);

        // Iterate over the months
        for(int i=0; i<12; i++) {
            // Define array per each month
            Map<Integer, Object>[] arr = new Map[2];
            arr[0] = new HashMap<>();

            // Iterate over the days and set the day to low
            for(int j=1; j<=31; j++)
                arr[0].put(j,"low");

            // Set the date on the calendar
            if(i == (LocalDateTime.now().getMonthValue()-1) )
                customCalendar.setDate(calendar, arr[0]);

            // Append the dates
            maps.put(i, arr);
        }

        // Get the CentersViewModel
        CentersViewModel centersViewModel = new ViewModelProvider(requireActivity()).get(
                CentersViewModel.class);

        // Get the CenterProducerEventsViewModel
        CenterProducerEventsViewModel eventsViewModel = new
                ViewModelProvider(requireActivity()).get(CenterProducerEventsViewModel.class);

        // Get selected and set UI fields values
        centersViewModel.getSelected().observe(requireActivity(), logisticCenterItem -> {
            logisticCenterName.setText(logisticCenterItem.getName());
            eventsViewModel.setId(logisticCenterItem.getId());
        });

        // Iterate over the dates
        for(int i=1; i<=12; i++) {
            String ini, fin;

            if(i < 10) ini = "2022-0"+ i + "-01";
            else ini = "2022-"+ i + "-01";
            i++;

            if(i < 10) fin = "2022-0"+ i + "-01";
            else fin = "2022-"+ i + "-01";
            i--;

            // Get producer events from logistic center by date
            eventsViewModel.getConsumerEventsFromLogisticCenterByDate(ini,fin).observe(
                    requireActivity(), consumerEventsList -> {

                // If there are producer events
                if(consumerEventsList.size()>0){
                    // Get month
                    int month = Integer.parseInt(consumerEventsList.get(0).getDate().substring(5,7));

                    Map<Integer, Object>[] arr = (Map<Integer, Object>[]) maps.get(month-1);


                    // Set the day to medium occupied
                    for (ConsumerEvent consumerEvent : consumerEventsList)
                        arr[0].put(Integer.parseInt(consumerEvent.getDate().substring(8, 10)),
                                "medium");

                    // Put the new array
                    maps.put(month-1,arr);

                    // Set month dates to the calendar
                    if(LocalDateTime.now().getMonthValue() == month) {
                        customCalendar.setDate(calendar, arr[0]);
                    }
                }
            });
        }

        // Initialize description hashmap
        HashMap<Object, Property> descHashMap=new HashMap<>();

        // For low events date
        Property low = new Property();
        low.layoutResource=R.layout.low_events_view;
        low.dateTextViewResource=R.id.text_view;
        descHashMap.put("low",low);

        // For medium events date
        Property medium = new Property();
        medium.layoutResource = R.layout.medium_events_view;
        medium.dateTextViewResource = R.id.text_view;
        descHashMap.put("medium",medium);

        // For high events date
        Property high = new Property();
        high.layoutResource = R.layout.high_events_view;
        high.dateTextViewResource = R.id.text_view;
        descHashMap.put("high",high);

        // For full events date
        Property full = new Property();
        full.layoutResource = R.layout.full_events_view;
        full.dateTextViewResource = R.id.text_view;
        descHashMap.put("full",full);

        // Set custom calendar items
        customCalendar.setMapDescToProp(descHashMap);
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, this);
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.NEXT, this);

        // Define callback on selected date
        customCalendar.setOnDateSelectedListener((view, selectedDate, desc) -> {

            // Get date, month and year
            int day = selectedDate.get(Calendar.DAY_OF_MONTH);
            int month = selectedDate.get(Calendar.MONTH) + 1;
            int year = selectedDate.get(Calendar.YEAR);

            // Parse to valid format
            String d = ((day > 9) ? String.valueOf(day) : "0" + day);
            String m = ((month > 9) ? String.valueOf(month) : "0" + month);
            String y = String.valueOf(year);
            String date = y + "-" + m + "-" + d;

            // Select the date
            eventsViewModel.select(date);

            // Redirect to select hour
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(
                    R.id.nav_delivery_hour);
        });

        return root;
    }

    @Override
    public Map<Integer, Object>[] onNavigationButtonClicked(int whichButton, Calendar newMonth) {
        return (Map<Integer, Object>[]) maps.get(newMonth.get(Calendar.MONTH));
    }
}