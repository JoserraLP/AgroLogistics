package com.unex.agrologistics.data.remote;

import java.util.ArrayList;

public class ProducerEventResponse {

    // Results field
    private Results results;

    // Message field
    private ArrayList<ProducerEventResponseItem> message;

    /**
     * Return all the results of the response
     * @return A ProducerEvents list
     */
    public ArrayList<ProducerEventResponseItem> getMessage() { return message; }

    /**
     * Set the message of the response
     * @param message Message with the Logistic center information
     */
    public void setMessage(ArrayList<ProducerEventResponseItem> message) {
        this.message = message;
    }

    /**
     * Return all the Results of the response
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

        // Results array of ProducerEventResponseItem
        private ArrayList<ProducerEventResponseItem> items;

        /**
         * Return all the ProducerEventResponseItem of the response
         * @return A ArrayList of ProducerEventResponseItem
         */
        @SuppressWarnings("unused")
        public ArrayList<ProducerEventResponseItem> getItems() {
            return items;
        }

        /**
         * Set the items (ProducerEventResponseItem) of the results
         * @param items ArrayList of ProducerEventResponseItem
         */
        @SuppressWarnings("unused")
        public void setItems(ArrayList<ProducerEventResponseItem> items) {
            this.items = items;
        }
    }
}
