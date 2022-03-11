




'use strict';

var Consumer = require('../service/ConsumerService');

module.exports.deleteConsumer = function deleteConsumer (req, res, next) {

    Consumer.deleteConsumer(req.swagger.params, res, next);

};

module.exports.getConsumer = function getConsumer (req, res, next) {

    Consumer.getConsumer(req.swagger.params, res, next);

};

module.exports.getConsumers = function getConsumers (req, res, next) {

    Consumer.getConsumers(req.swagger.params, res, next);

};

module.exports.postConsumer = function postConsumer (req, res, next) {

    Consumer.postConsumer(req.swagger.params, res, next);

};

module.exports.putConsumer = function putConsumer (req, res, next) {

    Consumer.putConsumer(req.swagger.params, res, next);

};
