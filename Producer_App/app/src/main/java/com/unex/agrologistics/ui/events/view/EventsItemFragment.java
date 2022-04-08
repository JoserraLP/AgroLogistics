package com.unex.agrologistics.ui.events.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.unex.agrologistics.R;
import com.unex.agrologistics.ui.delivery.viewmodel.CentersViewModel;
import com.unex.agrologistics.ui.delivery.viewmodel.ProductsViewModel;
import com.unex.agrologistics.ui.events.viewmodel.EventsViewModel;

import java.util.Objects;

public class EventsItemFragment extends Fragment {

    /**
     * onCreateView method
     *
     * @param inflater           Fragment inflater
     * @param container          Fragment container
     * @param savedInstanceState Bundle where instance is saved
     * @return Fragment View
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Inflate fragment
        View root = inflater.inflate(R.layout.fragment_events_info, container, false);

        // UI fields
        TextView eventLogisticCenter = root.findViewById(R.id.centerInfo);
        TextView eventDate = root.findViewById(R.id.dateInfo);
        TextView eventHour = root.findViewById(R.id.hourInfo);
        TextView eventProduct = root.findViewById(R.id.productInfo);
        TextView eventCategory = root.findViewById(R.id.categoryInfo);
        TextView eventStorageType = root.findViewById(R.id.storageTypeInfo);
        TextView eventAmount = root.findViewById(R.id.amountInfo);
        TextView eventPrice = root.findViewById(R.id.priceInfo);

        // Get the EventsViewModel
        @SuppressWarnings("deprecation") @SuppressLint("UseRequireInsteadOfGet")
        EventsViewModel eventsViewModel = new ViewModelProvider(requireActivity()).get(EventsViewModel.class);

        // Get selected and set UI fields values
        eventsViewModel.getSelected().observe(requireActivity(), producerEventItem -> {
            eventDate.setText(producerEventItem.getDate().substring(0, 10));
            eventHour.setText(producerEventItem.getDate().substring(11, 16));
            eventCategory.setText(producerEventItem.getProduct_category());
            eventStorageType.setText(producerEventItem.getStorage_type());
            eventAmount.setText(String.format("%s kg", producerEventItem.getAmount_kg()));
            eventPrice.setText(String.format("%s €", producerEventItem.getPrice()));
        });

        // Get the ProductsViewModel
        @SuppressWarnings("deprecation") @SuppressLint("UseRequireInsteadOfGet")
        ProductsViewModel productsViewModel = new ViewModelProvider(requireActivity()).get(ProductsViewModel.class);

        // Get product by id to set its name
        productsViewModel.getProductById(eventsViewModel.getProductId()).observe(requireActivity(),
                product -> eventProduct.setText(product.getName()));

        // Get the CenterViewModel
        @SuppressWarnings("deprecation") @SuppressLint("UseRequireInsteadOfGet")
        CentersViewModel centersViewModel = new ViewModelProvider(requireActivity()).get(CentersViewModel.class);

        Log.d("TT", String.valueOf(eventsViewModel.getLogisticCenterId()));

        // Get logistic center by id to set its name
        centersViewModel.getLogisticCenterById(eventsViewModel.getLogisticCenterId()).observe(
                requireActivity(), logisticCenter -> {
                        eventLogisticCenter.setText(logisticCenter.getName());
                });

        return root;
    }
}
