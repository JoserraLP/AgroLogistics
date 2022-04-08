package com.unex.agrologistics.ui.events.view;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unex.agrologistics.R;
import com.unex.agrologistics.ui.delivery.viewmodel.CentersViewModel;
import com.unex.agrologistics.ui.events.ListEventsAdapter;
import com.unex.agrologistics.ui.events.viewmodel.EventsViewModel;

import java.util.Objects;

public class EventsFragment extends Fragment {

    /**
     * onCreateView method
     * @param inflater Fragment inflater
     * @param container Fragment container
     * @param savedInstanceState Bundle where instance is saved
     * @return Fragment View
     */
    @SuppressLint("UseRequireInsteadOfGet")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate fragment
        View root = inflater.inflate(R.layout.fragment_list_events, container, false);

        // Get the EventsViewModel
        @SuppressWarnings("deprecation") EventsViewModel eventsViewModel = new ViewModelProvider(requireActivity()).get(EventsViewModel.class);

        // Get shared preferences
        SharedPreferences preferences = Objects.requireNonNull(this.getActivity()).
                getSharedPreferences("LoggedUser", 0);

        // Get producer id from preferences
        int producerId = preferences.getInt("id", -1);

        // Search the events by producer id
        eventsViewModel.setProducerId(producerId);

        // Create the ListEventsAdapter object
        ListEventsAdapter listEventsAdapter = new ListEventsAdapter(this.getContext(),
                eventsViewModel);

        // Get all the events and save it to the adapter
        eventsViewModel.getAllProducerEvents().observe(requireActivity(),
                listEventsAdapter::addEventList);

        // RecyclerView settings
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(listEventsAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(null, 1);
        recyclerView.setLayoutManager(layoutManager);

        return root;
    }
}
