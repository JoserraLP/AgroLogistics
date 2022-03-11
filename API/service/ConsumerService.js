'use strict';

const connection = require("../config/config");

/**
 * Delete consumer
 * Delete consumer
 *
 * consumerID Integer Consumer ID

 * returns String
 **/
module.exports.deleteConsumer = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = "DELETE FROM consumer WHERE id = " + req.consumerID.originalValue

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
 * Get one consumer's information
 * Get one consumer
 *
 * id Integer ID of the consumer to get information

 * returns String
 **/
module.exports.getConsumer = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create query
    var query = "SELECT * FROM consumer WHERE id = '" + req.id.originalValue + "'"

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
 * Returns all the consumers
 * Returns all the consumers
 *

 * returns String
 **/
module.exports.getConsumers = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create query
    var query = "SELECT * FROM consumer"

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
 * Creates new consumer
 * Creates new consumer
 *
 * consumer Consumer 

 * returns String
 **/
module.exports.postConsumer = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = 'INSERT INTO consumer SET ?'

    // Create data
    var data = {
        name: req.undefined.originalValue.name
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
 * Modifies a consumer
 * Modifies a consumer
 *
 * consumer Consumer 

 * returns String
 **/
module.exports.putConsumer = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = 'UPDATE consumer SET ? WHERE id = ' + req.undefined.originalValue.id

    // Create data
    var data = {
        name: req.undefined.originalValue.name
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




