package com.unex.agrologistics.data.remote;

import java.util.ArrayList;

public class ConsumerEventResponse {

    // Results field
    private Results results;

    // Message field
    private ArrayList<ConsumerEventResponseItem> message;

    /**
     * Return all the results of the response
     * @return A ProducerEvents list
     */
    public ArrayList<ConsumerEventResponseItem> getMessage() { return message; }

    /**
     * Set the message of the response
     * @param message Message with the Logistic center information
     */
    public void setMessage(ArrayList<ConsumerEventResponseItem> message) {
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

        // Results array of ConsumerEventResponseItem
        private ArrayList<ConsumerEventResponseItem> items;

        /**
         * Return all the ConsumerEventResponseItem of the response
         * @return A ArrayList of ConsumerEventResponseItem
         */
        @SuppressWarnings("unused")
        public ArrayList<ConsumerEventResponseItem> getItems() {
            return items;
        }

        /**
         * Set the items (ConsumerEventResponseItem) of the results
         * @param items ArrayList of ConsumerEventResponseItem
         */
        @SuppressWarnings("unused")
        public void setItems(ArrayList<ConsumerEventResponseItem> items) {
            this.items = items;
        }
    }
}
