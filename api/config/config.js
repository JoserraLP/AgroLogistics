const mysql = require('mysql');

const db = mysql.createConnection({
	host:"172.30.0.2",
	port: "3306",
	user:"root",
	password:"root",
	database:"agrologistics"
});

module.exports = db;