package com.unex.agrologistics.ui.delivery;

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
import com.unex.agrologistics.model.ConsumerEvent;
import com.unex.agrologistics.model.ProducerEvent;
import com.unex.agrologistics.ui.delivery.view.DeliveryHourFragment;
import com.unex.agrologistics.ui.delivery.viewmodel.CenterProducerEventsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListHoursAdapter extends RecyclerView.Adapter<ListHoursAdapter.ViewHolder> {

    // Hours data
    private ArrayList<DeliveryHourFragment.Hour> data;

    // CenterProducerEventsViewModel to retrieve the information
    private CenterProducerEventsViewModel eventsViewModel;

    // Adapter context
    private Context context;

    /**
     * ListHoursAdapter constructor
     * @param context Adapter context
     */
    public ListHoursAdapter(Context context, CenterProducerEventsViewModel eventsViewModel) {
        // Initialize local variables
        this.context = context;
        this.data = new ArrayList<>();
        this.eventsViewModel = eventsViewModel;
    }

    /**
     * onCreateViewHolder method
     * @param parent ViewGroup parent
     * @param viewType ViewType
     * @return ViewHolder with the adapter
     */
    @NonNull
    @Override
    public ListHoursAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hour_view,
                parent, false);
        return new ListHoursAdapter.ViewHolder(view);
    }

    /**
     * onBindViewHolder
     * @param holder ViewHolder
     * @param position Item position
     */
    @Override
    public void onBindViewHolder(ListHoursAdapter.ViewHolder holder, final int position) {
        // Get the item
        final DeliveryHourFragment.Hour hourItem = data.get(position);

        // Set values on UI fields
        holder.hour.setText(hourItem.getHour());

        // If hour is free
        if (hourItem.isFree()) {
            holder.linearLayout.setOnClickListener(view -> {
                // If clicked, create new event and set selected date
                ConsumerEvent newEvent = eventsViewModel.getNewEvent();
                newEvent.setDate(eventsViewModel.getSelectedDate() + " " + hourItem.getHour() + ":00");
                eventsViewModel.setNewEvent(newEvent);

                // Move to event confirmation
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(
                        R.id.nav_delivery_confirmation);
            });
        }
        else {
            // Set different color when there is one event
            holder.hour.setTextColor(0xFFFFFFFF);
            holder.linearLayout.setBackgroundColor(0xFF828784);
            holder.linearLayout.setClickable(false);
        }
    }

    /**
     * Get all the logistic centers in the adapter
     * @return LogisticCenters in the adapter
     */
    @SuppressWarnings("unused")
    public ArrayList<DeliveryHourFragment.Hour> getLogisticCenters(){
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
     * Add a hoursList to the current list
     * @param hoursList new hours list
     */
    public void addNewsList (List<DeliveryHourFragment.Hour> hoursList) {
        // Clear list
        data.clear();
        // Add new items to list
        data.addAll(hoursList);
        // Notify that the list has been changed
        notifyDataSetChanged();
    }

    // ViewHolder class to represent the hour in the adapter
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView hour;
        private LinearLayout linearLayout;

        /**
         * ViewHolder constructor
         * @param itemView View
         */
        ViewHolder(View itemView) {
            super(itemView);
            hour = itemView.findViewById(R.id.hour);
            linearLayout = itemView.findViewById(R.id.layout_hour_item);
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
        for (DeliveryHourFragment.Hour hourItem : data)
            total.append(hourItem.toString());
        return total.toString();
    }
}

