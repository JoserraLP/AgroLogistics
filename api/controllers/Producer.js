




'use strict';

var Producer = require('../service/ProducerService');

module.exports.deleteProducer = function deleteProducer (req, res, next) {

    Producer.deleteProducer(req.swagger.params, res, next);

};

module.exports.getProducer = function getProducer (req, res, next) {

    Producer.getProducer(req.swagger.params, res, next);

};

module.exports.getProducers = function getProducers (req, res, next) {

    Producer.getProducers(req.swagger.params, res, next);

};

module.exports.postProducer = function postProducer (req, res, next) {

    Producer.postProducer(req.swagger.params, res, next);

};

module.exports.putProducer = function putProducer (req, res, next) {

    Producer.putProducer(req.swagger.params, res, next);

};
