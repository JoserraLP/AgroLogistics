const mysql = require('mysql');

const db = mysql.createConnection({
	host:"172.30.0.2",
	port: "3306",
	user:"root",
	password:"root",
	database:"agrologistics"
});

db.connect(function(err){
	if (err) {
		process.exit(-1);
		//setTimeout(() => {process.exit(-1);}, 15000); // 15 seconds	
	}
})

module.exports = db;