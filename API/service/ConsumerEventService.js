'use strict';

const connection = require("../config/config");

/**
 * Delete consumer event
 * Delete consumer event
 *
 * consumerEventID Integer Consumer event ID

 * returns String
 **/
module.exports.deleteConsumerEvent = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create a query
    var query = "DELETE FROM consumer_event WHERE id = " + req.consumerEventID.originalValue

    // Execute query
    connection.query(query, function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};


/**
 * Returns all the consumer events
 * Returns all the consumer events
 *
 * day Integer Day (optional)
 * month Integer Month (optional)
 * year Integer Year (optional)

 * returns String
 **/
module.exports.getConsumerEvents = function(req, res, next) {
    //Parameters
    console.log(req);

    // Process the dates
    var day = req.day.originalValue;
    var month = req.month.originalValue;
    var year = req.year.originalValue;

    var query = "";

    if (day != undefined && month != undefined && year != undefined){
        query = "SELECT * FROM consumer_event WHERE YEAR(date) = '" + year + "' AND MONTH(date) = " + month + " AND DAY(date) = " + day;
    } else if (month != undefined && year != undefined){
        query = "SELECT * FROM consumer_event WHERE YEAR(date) = '" + year + "' AND MONTH(date) = " + month;
    } else if (year != undefined){
        query = "SELECT * FROM consumer_event WHERE YEAR(date) = '" + year + "'";
    } else {
        query = "SELECT * FROM consumer_event";
    }

    if (query){
        // Execute query
        connection.query(query, function (error, results, fields) {
            if (error) throw error;
            console.log('The solution is: ', results);
            res.send({
                message: results
            });
        });
    }
};


/**
 * Creates new consumer event
 * Creates new consumer event
 *
 * consumerEvent ConsumerEvent 

 * returns String
 **/
module.exports.postConsumerEvent = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = 'INSERT INTO consumer_event SET ?'

    // Create data
    var data = {
        product_id: req.undefined.originalValue.product_id,
        logistic_center_id: req.undefined.originalValue.logistic_center_id,
        product_category: req.undefined.originalValue.product_category,
        consumer_id: req.undefined.originalValue.consumer_id,
        amount_kg: req.undefined.originalValue.amount_kg,
        date: req.undefined.originalValue.date,
        price: req.undefined.originalValue.price,
        storage_type: req.undefined.originalValue.storage_type
    }

    // Execute query
    connection.query(query, [data], function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};


/**
 * Modifies a consumer event
 * Modifies a consumer event
 *
 * consumerEvent ConsumerEvent 

 * returns String
 **/
module.exports.putConsumerEvent = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = 'UPDATE consumer_event SET ? WHERE id = ' + req.undefined.originalValue.id
    
    // Create data
    var data = {
        product_id: req.undefined.originalValue.product_id,
        logistic_center_id: req.undefined.originalValue.logistic_center_id,
        product_category: req.undefined.originalValue.product_category,
        consumer_id: req.undefined.originalValue.consumer_id,
        amount_kg: req.undefined.originalValue.amount_kg,
        date: req.undefined.originalValue.date,
        price: req.undefined.originalValue.price,
        storage_type: req.undefined.originalValue.storage_type
    }

    // Execute query
    connection.query(query, [data], function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};




