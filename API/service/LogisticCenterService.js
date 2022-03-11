'use strict';

const connection = require("../config/config");

/**
 * Delete product item
 * Delete logistic center
 *
 * logisticCenterID Integer Logistic center ID

 * returns String
 **/
module.exports.deleteLogisticCenter = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = "DELETE FROM logistic_center WHERE id = " + req.logisticCenterID.originalValue

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
 * Get one logistic center's information
 * Get one logistic center
 *
 * id Integer ID of the logistic center to get information

 * returns String
 **/
module.exports.getLogisticCenter = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create query
    var query = "SELECT * FROM logistic_center WHERE id = '" + req.id.originalValue + "'"
    
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
 * Returns all the logistics centers
 * Returns all the logistic centers
 *

 * returns String
 **/
module.exports.getLogisticCenters = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create query
    var query = "SELECT * FROM logistic_center"

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
 * Creates new logistic center
 * Creates new logistic center
 *
 * logisticCenter LogisticCenter 

 * returns String
 **/
module.exports.postLogisticCenter = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create a query
    var query = 'INSERT INTO logistic_center SET ?'

    // Create data
    var data = {
        name: req.undefined.originalValue.name,
        capacity_kg: req.undefined.originalValue.capacity_kg,
        cooled_capacity_kg: req.undefined.originalValue.cooled_capacity_kg
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
 * Modifies a logistic center
 * Modifies a logistic center
 *
 * logisticCenter LogisticCenter 

 * returns String
 **/
module.exports.putLogisticCenter = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create a query
    var query = 'UPDATE logistic_center SET ? WHERE id = ' + req.undefined.originalValue.id

    // Create data
    var data = {
        name: req.undefined.originalValue.name,
        capacity_kg: req.undefined.originalValue.capacity_kg,
        cooled_capacity_kg: req.undefined.originalValue.cooled_capacity_kg
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




