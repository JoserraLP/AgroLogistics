package com.unex.agrologistics.ui.delivery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.unex.agrologistics.R;
import com.unex.agrologistics.model.LogisticCenter;
import com.unex.agrologistics.ui.delivery.viewmodel.CentersViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListCentersAdapter extends RecyclerView.Adapter<ListCentersAdapter.ViewHolder> {

    // CentersViewModel to retrieve the information
    private CentersViewModel centersViewModel;

    // Logistic centers data
    private ArrayList<LogisticCenter> data;

    // Adapter context
    private Context context;

    /**
     * ListCentersAdapter constructor
     * @param context Adapter context
     * @param centersViewModel CentersViewModel
     */
    public ListCentersAdapter(Context context, CentersViewModel centersViewModel) {
        // Initialize local variables
        this.context = context;
        this.data = new ArrayList<>();
        this.centersViewModel = centersViewModel;
    }

    /**
     * onCreateViewHolder method
     * @param parent ViewGroup parent
     * @param viewType ViewType
     * @return ViewHolder with the adapter
     */
    @NonNull
    @Override
    public ListCentersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logistic_center,
                parent, false);
        return new ListCentersAdapter.ViewHolder(view);
    }

    /**
     * onBindViewHolder
     * @param holder ViewHolder
     * @param position Item position
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ListCentersAdapter.ViewHolder holder, final int position) {
        // Get the producer events item
        LogisticCenter logisticCenterItem = data.get(position);

        // Set values on UI fields
        holder.logisticCenterName.setText(logisticCenterItem.getName());

        // Define onClick callback
        holder.linearLayout.setOnClickListener(view -> {
            centersViewModel.select(logisticCenterItem);

            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_delivery_details);
        });
    }

    /**
     * Get all the logistic centers in the adapter
     * @return LogisticCenters in the adapter
     */
    @SuppressWarnings("unused")
    public ArrayList<LogisticCenter> getLogisticCenters(){
        return data;
    }

    /**
     * Get the number of elements in the adapter
     * @return Number of elements in the adapter
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Add a newsList to the current list
     * @param logisticCentersList new logistic centers list
     */
    public void addCentersList (List<LogisticCenter> logisticCentersList) {
        // Clear list
        data.clear();
        // Add new items to list
        data.addAll(logisticCentersList);
        // Notify that the list has been changed
        notifyDataSetChanged();
    }

    // ViewHolder class to represent the news in the adapter
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView logisticCenterName;
        private LinearLayout linearLayout;

        /**
         * ViewHolder constructor
         * @param itemView View
         */
        ViewHolder(View itemView) {
            super(itemView);

            logisticCenterName = itemView.findViewById(R.id.center_name);
            linearLayout = itemView.findViewById(R.id.layout_logisticCenter_item);
        }
    }

    /**
     * toString method
     * @return String representing the adapter
     */
    @NonNull
    @Override
    public String toString() {
        StringBuilder total = new StringBuilder();
        for (LogisticCenter logisticCenterItem : data)
            total.append(logisticCenterItem.toString());
        return total.toString();
    }
}
