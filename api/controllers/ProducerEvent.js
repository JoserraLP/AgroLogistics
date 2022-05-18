




'use strict';

var ProducerEvent = require('../service/ProducerEventService');

module.exports.deleteProducerEvent = function deleteProducerEvent (req, res, next) {

    ProducerEvent.deleteProducerEvent(req.swagger.params, res, next);

};

module.exports.getProducerEvents = function getProducerEvents (req, res, next) {

    ProducerEvent.getProducerEvents(req.swagger.params, res, next);

};

module.exports.postProducerEvent = function postProducerEvent (req, res, next) {

    ProducerEvent.postProducerEvent(req.swagger.params, res, next);

};

module.exports.putProducerEvent = function putProducerEvent (req, res, next) {

    ProducerEvent.putProducerEvent(req.swagger.params, res, next);

};
