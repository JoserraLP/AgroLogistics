




'use strict';

var ActualStock = require('../service/ActualStockService');

module.exports.deleteActualStock = function deleteActualStock (req, res, next) {

    ActualStock.deleteActualStock(req.swagger.params, res, next);

};

module.exports.getActualStock = function getActualStock (req, res, next) {

    ActualStock.getActualStock(req.swagger.params, res, next);

};

module.exports.postActualStock = function postActualStock (req, res, next) {

    ActualStock.postActualStock(req.swagger.params, res, next);

};

module.exports.putActualStock = function putActualStock (req, res, next) {

    ActualStock.putActualStock(req.swagger.params, res, next);

};
