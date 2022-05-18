CREATE DATABASE IF NOT EXISTS agrologistics;

USE agrologistics;

CREATE TABLE IF NOT EXISTS product (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40),  
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS logistic_center (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40),  
    capacity_kg DECIMAL(10, 2),    
    cooled_capacity_kg DECIMAL(10, 2),
    email VARCHAR(50),
    password BLOB,
	PRIMARY KEY (id, email)
);

CREATE TABLE IF NOT EXISTS producer (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40), 
    email VARCHAR(50),
    password VARCHAR(20), 
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS consumer (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40),  
    email VARCHAR(50),
    password VARCHAR(20),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS producer_event (
	id INT NOT NULL AUTO_INCREMENT,
    product_id INT,
    logistic_center_id INT,
    producer_id INT,
    product_category VARCHAR(20),
    amount_kg DECIMAL(10, 2),
    date TIMESTAMP,
    price DECIMAL(6, 2),
    storage_type VARCHAR(20),
	PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (logistic_center_id) REFERENCES logistic_center(id),
    FOREIGN KEY (producer_id) REFERENCES producer(id)
);

CREATE TABLE IF NOT EXISTS producer_transaction (
	id INT NOT NULL,
    product_id INT,
    logistic_center_id INT,
    producer_id INT,
    product_category VARCHAR(20),
    amount_kg DECIMAL(10, 2),
    date TIMESTAMP,
    price DECIMAL(6, 2),
    storage_type VARCHAR(20),
	PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (logistic_center_id) REFERENCES logistic_center(id),
    FOREIGN KEY (producer_id) REFERENCES producer(id)
);

CREATE TABLE IF NOT EXISTS consumer_event (
	id INT NOT NULL AUTO_INCREMENT,
    product_id INT,
    logistic_center_id INT,
    consumer_id INT,
    product_category VARCHAR(20),
    amount_kg DECIMAL(10, 2),
    date TIMESTAMP,
    price DECIMAL(6, 2),
    storage_type VARCHAR(20),
	PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (logistic_center_id) REFERENCES logistic_center(id),
    FOREIGN KEY (consumer_id) REFERENCES consumer(id)
);

CREATE TABLE IF NOT EXISTS consumer_transaction (
	id INT NOT NULL,
    product_id INT,
    logistic_center_id INT,
    consumer_id INT,
    product_category VARCHAR(20),
    amount_kg DECIMAL(10, 2),
    date TIMESTAMP,
    price DECIMAL(6, 2),
    storage_type VARCHAR(20),
	PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (logistic_center_id) REFERENCES logistic_center(id),
    FOREIGN KEY (consumer_id) REFERENCES consumer(id)
);

CREATE TABLE IF NOT EXISTS actual_stock (
	id INT NOT NULL AUTO_INCREMENT,
    product_id INT,
    logistic_center_id INT,
    product_category VARCHAR(20),
    amount_kg DECIMAL(10, 2),
    date TIMESTAMP,
	PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (logistic_center_id) REFERENCES logistic_center(id)
);

CREATE TABLE IF NOT EXISTS estimated_stock (
	id INT NOT NULL AUTO_INCREMENT,
    product_id INT,
    logistic_center_id INT,
    product_category VARCHAR(20),
    amount_kg DECIMAL(10, 2),
    date TIMESTAMP,
	PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (logistic_center_id) REFERENCES logistic_center(id)
);