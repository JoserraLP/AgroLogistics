




'use strict';

var EstimatedStock = require('../service/EstimatedStockService');

module.exports.deleteEstimatedStock = function deleteEstimatedStock (req, res, next) {

    EstimatedStock.deleteEstimatedStock(req.swagger.params, res, next);

};

module.exports.getEstimatedStock = function getEstimatedStock (req, res, next) {

    EstimatedStock.getEstimatedStock(req.swagger.params, res, next);

};

module.exports.postEstimatedStock = function postEstimatedStock (req, res, next) {

    EstimatedStock.postEstimatedStock(req.swagger.params, res, next);

};

module.exports.putEstimatedStock = function putEstimatedStock (req, res, next) {

    EstimatedStock.putEstimatedStock(req.swagger.params, res, next);

};
