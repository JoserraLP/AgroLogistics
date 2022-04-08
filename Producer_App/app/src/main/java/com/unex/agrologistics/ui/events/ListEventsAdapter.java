package com.unex.agrologistics.ui.events;

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
import com.unex.agrologistics.model.ProducerEvent;
import com.unex.agrologistics.ui.events.viewmodel.EventsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListEventsAdapter extends RecyclerView.Adapter<ListEventsAdapter.ViewHolder> {

    // EventsViewModel to retrieve the information
    private EventsViewModel eventsViewModel;

    // Producer events data
    private ArrayList<ProducerEvent> data;

    // Adapter context
    private Context context;

    /**
     * ListEventsAdapter constructor
     * @param context Adapter context
     * @param eventsViewModel NewsTypeViewModel
     */
    public ListEventsAdapter(Context context, EventsViewModel eventsViewModel) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    /**
     * onBindViewHolder
     * @param holder ViewHolder
     * @param position Item position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // Get the producer events item
        ProducerEvent producerEventItem = data.get(position);

        // Define local strings to represent the information
        String producer_event_id = "Product " + producerEventItem.getProduct_id() + ", " +
                producerEventItem.getAmount_kg() + " kg";
        String producer_event_price = producerEventItem.getPrice() + " €";
        String producer_event_date = producerEventItem.getDate().substring(0,10) + " " +
                producerEventItem.getDate().substring(12,16);

        // Set values on UI fields
        holder.producerEventId.setText(producer_event_id);
        holder.producerEventPrice.setText(producer_event_price);
        holder.producerEventDate.setText(producer_event_date);

        // Define onClick callback
        holder.linearLayout.setOnClickListener(view -> {
            eventsViewModel.select(producerEventItem);
            Navigation.findNavController((Activity) context,
                    R.id.nav_host_fragment).navigate(R.id.nav_news_item);
        });
    }

    /**
     * Get all the news in the adapter
     * @return News in the adapter
     */
    @SuppressWarnings("unused")
    public ArrayList<ProducerEvent> getProducerEvents(){
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
     * Add a eventList to the current list
     * @param producerEventsList new event list
     */
    public void addEventList (List<ProducerEvent> producerEventsList) {
        // Clear list
        data.clear();
        // Add new items to list
        data.addAll(producerEventsList);
        // Notify that the list has been changed
        notifyDataSetChanged();
    }

    // ViewHolder class to represent the news in the adapter
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView producerEventId;
        private TextView producerEventPrice;
        private TextView producerEventDate;
        private LinearLayout linearLayout;

        /**
         * ViewHolder constructor
         * @param itemView View
         */
        ViewHolder(View itemView) {
            super(itemView);

            producerEventId = itemView.findViewById(R.id.event_product);
            producerEventPrice = itemView.findViewById(R.id.event_price);
            producerEventDate = itemView.findViewById(R.id.event_date);
            linearLayout = itemView.findViewById(R.id.layout_news_item);

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
        for (ProducerEvent producerEventItem : data)
            total.append(producerEventItem.toString());
        return total.toString();
    }
}
