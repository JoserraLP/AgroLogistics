'use strict';

const { prependOnceListener } = require("../config/config");
const connection = require("../config/config");

/**
 * Delete producer event
 * Delete producer event
 *
 * producerEventID Integer Producer event ID

 * returns String
 **/
module.exports.deleteProducerEvent = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create a query
    var query = "DELETE FROM producer_event WHERE id = " + req.producerEventID.originalValue

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
 * Returns all the producer events
 * Returns all the producer events
 *
 * day Integer Day (optional)
 * month Integer Month (optional)
 * year Integer Year (optional)

 * returns String
 **/
module.exports.getProducerEvents = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Process the dates
    var day = req.day.originalValue;
    var month = req.month.originalValue;
    var year = req.year.originalValue;
    var logistic_center_id = req.logistic_center_id.originalValue;
    var product_id = req.product_id.originalValue;
    var producer_id = req.producer_id.originalValue;

    var query =  "SELECT producer_event.id, producer_event.product_id, product.name AS product_name, producer_event.logistic_center_id, "
    + "logistic_center.name AS logistic_center_name, producer_event.producer_id, producer.name AS producer_name, producer_event.product_category, "
    + "producer_event.amount_kg, producer_event.date, producer_event.price, producer_event.storage_type "
    + "FROM producer_event INNER JOIN product ON producer_event.product_id = product.id " 
    + "INNER JOIN logistic_center ON producer_event.logistic_center_id = logistic_center.id "
    + "INNER JOIN producer ON producer_event.producer_id = producer.id";
    var conditions = [];

    if (day != undefined && month != undefined && year != undefined){
        conditions.push(" YEAR(date) = '" + year + "' AND MONTH(date) = " + month + " AND DAY(date) = " + day);
    } else if (month != undefined && year != undefined){
        conditions.push(" YEAR(date) = '" + year + "' AND MONTH(date) = " + month);
    } else if (year != undefined){
        conditions.push(" YEAR(date) = '" + year + "'");
    }

    if (logistic_center_id != undefined){ 
        conditions.push(" logistic_center_id = " + logistic_center_id);
    }
    if (product_id != undefined){
        conditions.push(" product_id = " + product_id);
    }
    if (producer_id != undefined){
        conditions.push(" producer_id = " + producer_id);
    }

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
 * Creates new producer event
 * Creates new producer event
 *
 * producerEvent ProducerEvent 

 * returns String
 **/
module.exports.postProducerEvent = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create a query
    var query = 'INSERT INTO producer_event SET ?'

    // Create data
    var data = {
        product_id: req.undefined.originalValue.product_id,
        logistic_center_id: req.undefined.originalValue.logistic_center_id,
        product_category: req.undefined.originalValue.product_category,
        producer_id: req.undefined.originalValue.producer_id,
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
                                ' FROM consumer_event ' +
                                ' WHERE product_id = ' + req.undefined.originalValue.product_id +
                                ' AND logistic_center_id = ' + req.undefined.originalValue.logistic_center_id + ' AND product_category = "' +
                                req.undefined.originalValue.product_category + "\" AND DATE(date) < '" + req.undefined.originalValue.date  + '\' ' +
                                ' ) AS stock;'

    console.log(previous_stock_query)

    // Create new value based on the previous information
    connection.query(previous_stock_query, function (error, results, fields) {
        if (error) throw error;
        if (typeof results !== 'undefined'){
            var previous_stock = results[0];

            var new_estimated_stock = {
                'product_id': req.undefined.originalValue.product_id,
                'logistic_center_id': req.undefined.originalValue.logistic_center_id,
                'product_category': req.undefined.originalValue.product_category,
                'date': req.undefined.originalValue.date,
                'amount_kg': previous_stock['amount_kg'] + req.undefined.originalValue.amount_kg
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
        var update_future_stock = 'UPDATE estimated_stock SET amount_kg = amount_kg + ' + req.undefined.originalValue.amount_kg + 
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
 * Modifies a producer event
 * Modifies a producer event
 *
 * producerEvent ProducerEvent 

 * returns String
 **/
module.exports.putProducerEvent = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create a query
    var query = 'UPDATE producer_event SET ? WHERE id = ' + req.undefined.originalValue.id
    
    // Create data
    var data = {
        product_id: req.undefined.originalValue.product_id,
        logistic_center_id: req.undefined.originalValue.logistic_center_id,
        product_category: req.undefined.originalValue.product_category,
        producer_id: req.undefined.originalValue.producer_id,
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




