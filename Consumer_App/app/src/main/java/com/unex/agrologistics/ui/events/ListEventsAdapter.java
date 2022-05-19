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
import com.unex.agrologistics.model.ConsumerEvent;
import com.unex.agrologistics.model.ProducerEvent;
import com.unex.agrologistics.ui.delivery.viewmodel.CentersViewModel;
import com.unex.agrologistics.ui.delivery.viewmodel.ProductsViewModel;
import com.unex.agrologistics.ui.events.viewmodel.EventsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListEventsAdapter extends RecyclerView.Adapter<ListEventsAdapter.ViewHolder> {

    // EventsViewModel to retrieve the information
    private EventsViewModel eventsViewModel;

    // Producer events data
    private ArrayList<ConsumerEvent> data;

    // Adapter context
    private Context context;

    /**
     * ListEventsAdapter constructor
     * @param context Adapter context
     * @param eventsViewModel EventsViewModel
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
        ConsumerEvent consumerEventItem = data.get(position);

        // Define local strings to represent the information
        String consumer_event_id = consumerEventItem.getProduct_name() + ", " +
                consumerEventItem.getAmount_kg() + " kg";
        String consumer_event_date = consumerEventItem.getDate().substring(0,10) + " " +
                consumerEventItem.getDate().substring(11,16);
        String consumer_event_center = consumerEventItem.getLogistic_center_name();

        // Set values on UI fields
        holder.consumerEventId.setText(consumer_event_id);
        holder.consumerEventPrice.setText(consumer_event_center + ", " + consumer_event_date);

        // Define onClick callback
        holder.linearLayout.setOnClickListener(view -> {
            eventsViewModel.select(consumerEventItem);
            Navigation.findNavController((Activity) context,
                    R.id.nav_host_fragment).navigate(R.id.nav_news_item);
        });
    }

    /**
     * Get all the consumer events in the adapter
     * @return Consumer events in the adapter
     */
    @SuppressWarnings("unused")
    public ArrayList<ConsumerEvent> getConsumerEvents(){
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
     * @param consumerEventsList new event list
     */
    public void addEventList (List<ConsumerEvent> consumerEventsList) {
        // Clear list
        data.clear();
        // Add new items to list
        data.addAll(consumerEventsList);
        // Notify that the list has been changed
        notifyDataSetChanged();
    }

    // ViewHolder class to represent the news in the adapter
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView consumerEventId;
        private TextView consumerEventPrice;
        private LinearLayout linearLayout;

        /**
         * ViewHolder constructor
         * @param itemView View
         */
        ViewHolder(View itemView) {
            super(itemView);

            consumerEventId = itemView.findViewById(R.id.event_product);
            consumerEventPrice = itemView.findViewById(R.id.event_price);
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
        for (ConsumerEvent consumerEventItem : data)
            total.append(consumerEventItem.toString());
        return total.toString();
    }
}
