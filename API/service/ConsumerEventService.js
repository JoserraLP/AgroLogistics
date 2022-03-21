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
    var logistic_center_id = req.logistic_center_id.originalValue;
    var product_id = req.product_id.originalValue;
    var consumer_id = req.consumer_id.originalValue;

    var query =  "SELECT consumer_event.id, consumer_event.product_id, product.name AS product_name, consumer_event.logistic_center_id, "
    + "logistic_center.name AS logistic_center_name, consumer_event.consumer_id, consumer.name AS consumer_name, consumer_event.product_category, "
    + "consumer_event.amount_kg, consumer_event.date, consumer_event.price, consumer_event.storage_type "
    + "FROM consumer_event INNER JOIN product ON consumer_event.product_id = product.id " 
    + "INNER JOIN logistic_center ON consumer_event.logistic_center_id = logistic_center.id "
    + "INNER JOIN consumer ON consumer_event.consumer_id = consumer.id";
    var conditions = [];

    if (day != undefined && month != undefined && year != undefined){
        conditions.push(" YEAR(date) = '" + year + "' AND MONTH(date) = " + month + " AND DAY(date) = " + day);
    } else if (month != undefined && year != undefined){
        conditions.push(" YEAR(date) = '" + year + "' AND MONTH(date) = " + month);
    } else if (year != undefined){
        conditions.push(" YEAR(date) = '" + year + "'");
    }

    if (logistic_center_id != undefined)  conditions.push(" logistic_center_id = " + logistic_center_id);
    
    if (product_id != undefined) conditions.push(" product_id = " + product_id);
    
    if (consumer_id != undefined) conditions.push(" consumer_id = " + consumer_id);

    if (conditions.length > 0){
        query = query + " WHERE " + conditions.join(" AND ");
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

    // Update the estimated stock for a given date
    var stock_query = 'INSERT INTO estimated_stock SET ?'

    var estimated_stock_query = 'SELECT * FROM estimated_stock WHERE product_id = ' + req.undefined.originalValue.product_id
        + ' AND logistic_center_id = ' + req.undefined.originalValue.logistic_center_id + ' AND product_category = "' 
        + req.undefined.originalValue.product_category + "\" AND DATE(date) < '" + req.undefined.originalValue.date  + '\' ORDER BY id DESC LIMIT 1'

    // Execute query
    connection.query(estimated_stock_query, function (error, results, fields) {
        if (error) throw error;
        var estimated_stock = results[0];

        var new_estimated_stock = {
            'product_id': estimated_stock.product_id,
            'logistic_center_id': estimated_stock.logistic_center_id,
            'product_category': estimated_stock.product_category,
            'date': req.undefined.originalValue.date,
            'amount_kg': estimated_stock['amount_kg'] - req.undefined.originalValue.amount_kg
        }
        // Execute query
        connection.query(stock_query, [new_estimated_stock], function (error, results, fields) {
            if (error) throw error;
        });
    });

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




