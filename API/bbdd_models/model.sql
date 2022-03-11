USE agrologistics;

CREATE TABLE product (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40),  
	PRIMARY KEY (id)
);

CREATE TABLE logistic_center (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40),  
    capacity_kg DECIMAL(10, 2),    
    cooled_capacity_kg DECIMAL(10, 2),
	PRIMARY KEY (id)
);

CREATE TABLE producer (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40),  
	PRIMARY KEY (id)
);

CREATE TABLE consumer (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40),  
	PRIMARY KEY (id)
);

CREATE TABLE producter_event (
	id INT NOT NULL AUTO_INCREMENT,
    product_id INT,
    logistic_center_id INT,
    productor_id INT,
    product_category VARCHAR(20),
    amount_kg DECIMAL(10, 2),
    date TIMESTAMP,
    price DECIMAL(6, 2),
    storage_type VARCHAR(20),
	PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (logistic_center_id) REFERENCES logistic_center(id),
    FOREIGN KEY (productor_id) REFERENCES producer(id)
);

CREATE TABLE producer_transaction (
	id INT NOT NULL AUTO_INCREMENT,
    product_id INT,
    logistic_center_id INT,
    productor_id INT,
    product_category VARCHAR(20),
    amount_kg DECIMAL(10, 2),
    date TIMESTAMP,
    price DECIMAL(6, 2),
    storage_type VARCHAR(20),
	PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (logistic_center_id) REFERENCES logistic_center(id),
    FOREIGN KEY (productor_id) REFERENCES producer(id)
);

CREATE TABLE consumer_event (
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

CREATE TABLE consumer_transaction (
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

CREATE TABLE actual_stock (
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

CREATE TABLE estimated_stock (
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