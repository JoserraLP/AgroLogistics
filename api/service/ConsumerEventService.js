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

    // Calculate current stock based on previous data (consumer and transaction events)
    var previous_stock_query = 'SELECT SUM(amount_kg) AS amount_kg, product_id, logistic_center_id, product_category  ' +
                                ' FROM ' +
                                '(SELECT SUM(amount_kg) AS amount_kg, product_id, logistic_center_id, product_category '+
                                ' FROM producer_event' + 
                                ' WHERE product_id = ' + req.undefined.originalValue.product_id +
                                ' AND logistic_center_id = ' + req.undefined.originalValue.logistic_center_id + ' AND product_category = "' +
                                req.undefined.originalValue.product_category + "\" AND DATE(date) < '" + req.undefined.originalValue.date  + '\' ' +
                                ' UNION ALL ' +
                                'SELECT -SUM(amount_kg) AS amount_kg, product_id, logistic_center_id, product_category ' +
                                ' FROM consumer_event' +
                                ' WHERE product_id = ' + req.undefined.originalValue.product_id +
                                ' AND logistic_center_id = ' + req.undefined.originalValue.logistic_center_id + ' AND product_category = "' +
                                req.undefined.originalValue.product_category + "\" AND DATE(date) < '" + req.undefined.originalValue.date  + '\' ' +
                                ' ) AS stock;'
    // Execute query
    connection.query(previous_stock_query, function (error, results, fields) {
        if (error) throw error;
        if (typeof results !== 'undefined'){

            var previous_stock = results[0];

            var new_estimated_stock = {
                'product_id': req.undefined.originalValue.product_id,
                'logistic_center_id': req.undefined.originalValue.logistic_center_id,
                'product_category': req.undefined.originalValue.product_category,
                'date': req.undefined.originalValue.date,
                'amount_kg': previous_stock['amount_kg'] - req.undefined.originalValue.amount_kg
            }
            
            // Execute query
            connection.query(stock_query, [new_estimated_stock], function (error, results, fields) {
                if (error) throw error;
            });
        
        } else { // No previous values

            var new_estimated_stock = {
                'product_id': req.undefined.originalValue.product_id,
                'logistic_center_id': req.undefined.originalValue.logistic_center_id,
                'product_category': req.undefined.originalValue.product_category,
                'date': req.undefined.originalValue.date,
                'amount_kg': req.undefined.originalValue.amount_kg
            }

            // Execute query
            connection.query(stock_query, [new_estimated_stock], function (error, results, fields) {
                if (error) throw error;
            });
        }

        // Update the stock of the future stock with the new stock value
        var update_future_stock = 'UPDATE estimated_stock SET amount_kg = amount_kg - ' + req.undefined.originalValue.amount_kg +  
            ' WHERE product_id = ' + req.undefined.originalValue.product_id +
            ' AND logistic_center_id = ' + req.undefined.originalValue.logistic_center_id + ' AND product_category = "' +
            req.undefined.originalValue.product_category + "\" AND DATE(date) > '" + req.undefined.originalValue.date  + '\'';

        // Create new value based on the previous information
        connection.query(update_future_stock, function (error, results, fields) {
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




