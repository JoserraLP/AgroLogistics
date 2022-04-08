package com.unex.agrologistics.data.remote;

import java.util.ArrayList;

public class ProducerResponse {

    // Results field
    private Results results;

    // Message field
    private ArrayList<ProducerResponseItem> message;

    /**
     * Return all the results of the response
     * @return A Products list
     */
    public ArrayList<ProducerResponseItem> getMessage() { return message; }

    /**
     * Set the message of the response
     * @param message Message with the producer information
     */
    public void setMessage(ArrayList<ProducerResponseItem> message) {
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

        // Results array of ProducerResponseItem
        private ArrayList<ProducerResponseItem> items;

        /**
         * Return all the ProducerResponseItem of the response
         * @return A ArrayList of ProducerResponseItem
         */
        public ArrayList<ProducerResponseItem> getItems() {
            return items;
        }

        /**
         * Set the items (ProducerResponseItem) of the results
         * @param items ArrayList of ProducerResponseItem
         */
        @SuppressWarnings("unused")
        public void setItems(ArrayList<ProducerResponseItem> items) {
            this.items = items;
        }
    }
}
