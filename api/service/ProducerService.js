'use strict';

const connection = require("../config/config");

/**
 * Delete producer
 * Delete producer
 *
 * producerID Integer Producer ID

 * returns String
 **/
module.exports.deleteProducer = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = "DELETE FROM producer WHERE id = " + req.producerID.originalValue

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
 * Get one producer's information
 * Get one producer
 *
 * id Integer ID of the producer to get information

 * returns String
 **/
module.exports.getProducer = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create query
    var query = "SELECT * FROM producer WHERE id = '" + req.id.originalValue + "'"
    
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
 * Returns all the producers
 * Returns all the producers
 *

 * returns String
 **/
module.exports.getProducers = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create query
    var query = "SELECT * FROM producer"

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
 * Creates new producer
 * Creates new producer
 *
 * producer Producer 

 * returns String
 **/
module.exports.postProducer = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = 'INSERT INTO producer SET ?'

    // Create data
    var data = {
        name: req.undefined.originalValue.name,
        email: req.undefined.originalValue.email,
        password: req.undefined.originalValue.password
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
 * Modifies a producer
 * Modifies a producer
 *
 * producer Producer 

 * returns String
 **/
module.exports.putProducer = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = 'UPDATE producer SET ? WHERE id = ' + req.undefined.originalValue.id

    // Create data
    var data = {
        name: req.undefined.originalValue.name,
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
