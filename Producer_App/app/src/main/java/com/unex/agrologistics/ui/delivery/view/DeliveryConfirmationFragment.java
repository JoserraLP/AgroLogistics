package com.unex.agrologistics.ui.delivery.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.unex.agrologistics.DrawerActivity;
import com.unex.agrologistics.R;
import com.unex.agrologistics.model.ProducerEvent;
import com.unex.agrologistics.ui.delivery.viewmodel.CenterProducerEventsViewModel;
import com.unex.agrologistics.ui.delivery.viewmodel.CentersViewModel;
import com.unex.agrologistics.ui.delivery.viewmodel.ProductsViewModel;
import com.unex.agrologistics.ui.login.ProducersViewModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DeliveryConfirmationFragment extends Fragment {

    // Context
    Context context;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_delivery_confirmation, container, false);

        // Get context
        context = getContext();

        // UI fields
        TextView logisticCenterName = root.findViewById(R.id.logisticCenterName);
        EditText dateText = root.findViewById(R.id.finalDate);
        EditText hourText = root.findViewById(R.id.finalHour);
        EditText productText = root.findViewById(R.id.finalProduct);
        EditText categoryText = root.findViewById(R.id.finalCategory);
        EditText storageTypeText = root.findViewById(R.id.finalStorageType);
        EditText amountText = root.findViewById(R.id.finalAmount);
        TextView priceText = root.findViewById(R.id.finalPrice);
        Button confirm = root.findViewById(R.id.confirmDetailsButton);

        // Set UI fields status
        dateText.setEnabled(false);
        hourText.setEnabled(false);
        productText.setEnabled(false);
        categoryText.setEnabled(false);
        storageTypeText.setEnabled(false);
        amountText.setEnabled(false);

        // Get the CenterProducerEventsViewModel
        CenterProducerEventsViewModel eventsViewModel =
                new ViewModelProvider(requireActivity()).get(CenterProducerEventsViewModel.class);

        // Get the event created
        ProducerEvent newEvent = eventsViewModel.getNewEvent();

        // Get shared preferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("LoggedUser",0);

        // Retrieve producer id and name
        int producerId = preferences.getInt("id", -1);
        String producerName = preferences.getString("name", "");

        // Set the producer id and name values
        newEvent.setProducer_id(producerId);
        newEvent.setProducer_name(producerName);

        // Get the CenterViewModel
        CentersViewModel centersViewModel = new ViewModelProvider(requireActivity()).get(
                CentersViewModel.class);

        // Get selected and set logistic center name value
        centersViewModel.getSelected().observe(requireActivity(), logisticCenterItem -> {
            logisticCenterName.setText(logisticCenterItem.getName());
            newEvent.setLogistic_center_name(logisticCenterItem.getName());
        });

        // Set the category value
        String category = newEvent.getProduct_category();

        switch (category) {
            case "high_quality":
                categoryText.setText("High quality");
                break;
            case "medium_quality":
                categoryText.setText("Medium quality");
                break;
            case "low_quality":
                categoryText.setText("Low quality");
                break;
        }

        // Set the storage type value
        String storageType = newEvent.getStorage_type();

        if (storageType.equals("automatic"))
            storageTypeText.setText("Boxes");
        else if(storageType.equals("manual"))
            storageTypeText.setText("Pallets");

        // Set the amount value
        amountText.setText(String.format("%s", newEvent.getAmount_kg()));

        // Get date and hours
        String[] date = newEvent.getDate().split(" ");

        // Set the date value
        dateText.setText(date[0]);

        // Set the hour value
        hourText.setText(date[1].substring(0,5));

        // Get zone offset of the date
        int year = Integer.parseInt(date[0].substring(0,4));
        int month = Integer.parseInt(date[0].substring(5,7));
        int day = Integer.parseInt(date[0].substring(8,10));
        int hour = Integer.parseInt(date[1].substring(0,2));
        int min = Integer.parseInt(date[1].substring(3,5));

        ZoneId zone = ZoneId.of("Europe/Berlin");
        ZoneOffset zoneOffSet = zone.getRules().getOffset(LocalDateTime.of(year, month, day, hour, min));

        int newHour = Integer.parseInt(date[1].substring(0,2)) + zoneOffSet.getTotalSeconds()/3600;
        newEvent.setDate(date[0] + " " + newHour + date[1].substring(2,5));

        // Set the price value
        // TODO default is 20
        newEvent.setPrice(20);
        priceText.setText(String.format("%s €", newEvent.getPrice()));

        // Get the ProductsViewModel
        ProductsViewModel productsViewModel = new ViewModelProvider(requireActivity()).get(
                ProductsViewModel.class);

        // Get product by id
        productsViewModel.getProductById(newEvent.getProduct_id()).observe(requireActivity(), product -> {
            productText.setText(product.getName());
            newEvent.setProduct_name(product.getName());
        });

        // On click perform the insertion on the DB and POST method
        confirm.setOnClickListener(v -> {

            // Set new event
            eventsViewModel.setNewEvent(newEvent);

            // Create POST method to API
            eventsViewModel.postProducerEvent();

            // Insert new event on the DB
            // eventsViewModel.insertProducerEvent();

            // Start the drawer activity
            startActivity(new Intent(getActivity(), DrawerActivity.class));
        });

        return root;
    }
}