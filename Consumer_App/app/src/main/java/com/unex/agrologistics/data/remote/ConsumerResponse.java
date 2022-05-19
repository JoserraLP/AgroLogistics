package com.unex.agrologistics.data.remote;

import java.util.ArrayList;

public class ConsumerResponse {

    // Results field
    private Results results;

    // Message field
    private ArrayList<ConsumerResponseItem> message;

    /**
     * Return all the results of the response
     * @return A Products list
     */
    public ArrayList<ConsumerResponseItem> getMessage() { return message; }

    /**
     * Set the message of the response
     * @param message Message with the producer information
     */
    public void setMessage(ArrayList<ConsumerResponseItem> message) {
        this.message = message;
    }

    /**
     * Return all the results of the response
     * @return A Result class
     */
    public Results getResults() { return results; }

    /**
     * Set the results of the response
     * @param results Result class
     */
    @SuppressWarnings("unused")
    public void setResults(Results results) {
        this.results = results;
    }

    public static class Results {

        // Results array of ConsumerResponseItem
        private ArrayList<ConsumerResponseItem> items;

        /**
         * Return all the ConsumerResponseItem of the response
         * @return A ArrayList of ConsumerResponseItem
         */
        public ArrayList<ConsumerResponseItem> getItems() {
            return items;
        }

        /**
         * Set the items (ConsumerResponseItem) of the results
         * @param items ArrayList of ConsumerResponseItem
         */
        @SuppressWarnings("unused")
        public void setItems(ArrayList<ConsumerResponseItem> items) {
            this.items = items;
        }
    }
}
