package com.unex.agrologistics.ui.delivery.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.unex.agrologistics.R;
import com.unex.agrologistics.model.ProducerEvent;
import com.unex.agrologistics.model.Product;
import com.unex.agrologistics.ui.delivery.viewmodel.CenterProducerEventsViewModel;
import com.unex.agrologistics.ui.delivery.viewmodel.CentersViewModel;
import com.unex.agrologistics.ui.delivery.viewmodel.ProductsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryDetailsFragment extends Fragment {

    // Context
    Context context;

    // Products list
    Map<String, Integer> productsList = new HashMap<String, Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_delivery_details, container, false);

        // Get context
        context = this.getContext();

        // UI fields and spinner
        TextView logisticCenterName = root.findViewById(R.id.logisticCenterName);
        TextView errorMessage = root.findViewById(R.id.detailsError);
        EditText amountText = root.findViewById(R.id.editTextAmount);
        Button button = root.findViewById(R.id.confirmDetailsButton);

        Spinner spinnerCategory = root.findViewById(R.id.spinnerCategory);
        Spinner spinnerStorageType = root.findViewById(R.id.spinnerStorageType);
        Spinner spinnerProducts = root.findViewById(R.id.spinnerProducts);

        // Define empty products list
        List<String> emptyProducts = new ArrayList<>();

        // Set adapters for the spinners
        ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(context,
                R.array.categories, R.layout.spinner);
        categoriesAdapter.setDropDownViewResource(R.layout.spinner);
        spinnerCategory.setAdapter(categoriesAdapter);

        ArrayAdapter<CharSequence> storageTypeAdapter = ArrayAdapter.createFromResource(context,
                R.array.storage_types, R.layout.spinner);
        storageTypeAdapter.setDropDownViewResource(R.layout.spinner);
        spinnerStorageType.setAdapter(storageTypeAdapter);

        ArrayAdapter<String> productsAdapter = new ArrayAdapter<>(context, R.layout.spinner,
                emptyProducts);
        productsAdapter.setDropDownViewResource(R.layout.spinner);
        spinnerProducts.setAdapter(productsAdapter);

        // Get the CentersViewModel
        CentersViewModel centersViewModel = new ViewModelProvider(this).get(
                CentersViewModel.class);

        // Get selected and set UI fields values
        centersViewModel.getSelected().observe(requireActivity(), logisticCenterItem ->
                logisticCenterName.setText(logisticCenterItem.getName()));

        // Get the ProductsViewModel
        ProductsViewModel productsViewModel = new ViewModelProvider(this).get(
                ProductsViewModel.class);

        // Get all products and add them to the products adapter
        productsViewModel.getAllProducts().observe(requireActivity(), products -> {
            List<String> newProducts = new ArrayList<>();

            // Store the products
            for(Product product : products) {
                newProducts.add(product.getName());
                productsList.put(product.getName(), product.getId());
            }
            // Clear and add all the products to the adapter
            productsAdapter.clear();
            productsAdapter.addAll(newProducts);
            productsAdapter.notifyDataSetChanged();
        });

        // Get the CenterProducerEventsViewModel
        CenterProducerEventsViewModel eventsViewModel = new ViewModelProvider(this).get(
                CenterProducerEventsViewModel.class);

        // Define on click for confirmation
        button.setOnClickListener(v -> {
            try {
                // Retrieve selected values
                String product = (String) spinnerProducts.getSelectedItem();
                String category = (String) spinnerCategory.getSelectedItem();
                String storageType = (String) spinnerStorageType.getSelectedItem();
                int amount = Integer.parseInt(amountText.getText().toString());

                // Get new event
                ProducerEvent newEvent = eventsViewModel.getNewEvent();

                // Set product id to the producer event
                newEvent.setProduct_id(productsList.get(product));

                // Set the category
                switch (category) {
                    case "High quality":
                        newEvent.setProduct_category("high_quality");
                        break;
                    case "Medium quality":
                        newEvent.setProduct_category("medium_quality");
                        break;
                    case "Low quality":
                        newEvent.setProduct_category("low_quality");
                        break;
                }

                // Set the storage type
                if(storageType.equals("Boxes")) newEvent.setStorage_type("automatic");
                else if(storageType.equals("Pallets")) newEvent.setStorage_type("manual");

                // Set the amount of KG
                newEvent.setAmount_kg(amount);
                if(amount > 0) {
                    // Set the event
                    eventsViewModel.setNewEvent(newEvent);
                    Navigation.findNavController((Activity) context,
                            R.id.nav_host_fragment).navigate(R.id.nav_delivery_date);
                }
                else
                    errorMessage.setVisibility(View.VISIBLE);
            } catch(NumberFormatException e) {
                errorMessage.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }
}