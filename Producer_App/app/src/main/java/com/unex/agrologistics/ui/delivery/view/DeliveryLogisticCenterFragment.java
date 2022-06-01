package com.unex.agrologistics.ui.delivery.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unex.agrologistics.R;
import com.unex.agrologistics.data.repository.ProducerEventRepository;
import com.unex.agrologistics.ui.delivery.ListCentersAdapter;
import com.unex.agrologistics.ui.delivery.viewmodel.CentersViewModel;

import java.util.Objects;

public class DeliveryLogisticCenterFragment extends Fragment {

    /**
     * onCreateView method
     * @param inflater Fragment inflater
     * @param container Fragment container
     * @param savedInstanceState Bundle where instance is saved
     * @return Fragment View
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate fragment
        View root = inflater.inflate(R.layout.fragment_delivery_logistics_centers, container, false);

        // Get the EventsViewModel
        CentersViewModel centersViewModel = new ViewModelProvider(requireActivity()).get(
                CentersViewModel.class);

        // Create the ListEventsAdapter object
        ListCentersAdapter listCentersAdapter = new ListCentersAdapter(this.getContext(), centersViewModel);

        // Get all the centers and save it to the adapter
        centersViewModel.getAllLogisticCenters().observe(requireActivity(),
                listCentersAdapter::addCentersList);

        // RecyclerView settings
        RecyclerView recyclerView = root.findViewById(R.id.centersRecyclerView);
        recyclerView.setAdapter(listCentersAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(null, 1);
        recyclerView.setLayoutManager(layoutManager);

        // Define search text field and button
        EditText searchText = root.findViewById(R.id.searchText);
        ImageButton searchButton = root.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(v -> centersViewModel.getLogisticCentersBySearch(
                searchText.getText().toString()).observe(requireActivity(),
                listCentersAdapter::addCentersList));

        return root;
    }
}
