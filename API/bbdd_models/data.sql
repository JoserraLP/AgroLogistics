INSERT INTO product (name) VALUES 
    ("Potato"), ("Carrot"), 
    ("Strawberry"), ("Apple");

INSERT INTO logistic_center (name, capacity_kg, cooled_capacity_kg) VALUES 
    ("Caceres Logistic Center", 10000, 2000), 
    ("Badajoz Logistic Center", 20000, 5000);

INSERT INTO producer (name) VALUES 
    ("Farmer Lopez"), 
    ("Farmer Antonio"),
    ("Farmer Jose");

INSERT INTO consumer (name) VALUES 
    ("Caceres Greengrocer"), 
    ("Lola's Greengrocer"),
    ("UltraGreengrocer");

INSERT INTO producer_event (product_id, logistic_center_id, producer_id, product_category, 
                            amount_kg, date, price, storage_type) VALUES 
    (1, 1, 1, "high_quality", 1000, "2022-03-10 10:00:00", 10, "automatic"), 
    (2, 1, 2, "medium_quality", 2000, "2022-03-14 10:00:00", 5, "manual"),
    (1, 2, 1, "low_quality", 100, "2022-04-10 10:00:00", 20, "automatic"),
    (2, 2, 2, "high_quality", 1050, "2023-03-10 10:00:00", 50, "manual");

INSERT INTO producer_transaction (id, product_id, logistic_center_id, producer_id, product_category, 
                                  amount_kg, date, price, storage_type) VALUES 
    (1, 1, 1, 1, "high_quality", 1000, "2022-03-10 10:00:00", 10, "automatic"), 
    (2, 2, 1, 2, "medium_quality", 2000, "2022-03-14 10:00:00", 5, "manual"),
    (3, 1, 2, 1, "low_quality", 100, "2022-04-10 10:00:00", 20, "automatic"),
    (4, 2, 2, 2, "high_quality", 1050, "2023-03-10 10:00:00", 50, "manual");

INSERT INTO consumer_event (product_id, logistic_center_id, consumer_id, product_category, 
                            amount_kg, date, price, storage_type) VALUES 
    (1, 1, 1, "high_quality", 1000, "2022-03-10 10:00:00", 10, "automatic"), 
    (2, 1, 2, "medium_quality", 2000, "2022-03-14 10:00:00", 5, "manual"),
    (1, 2, 1, "low_quality", 100, "2022-04-10 10:00:00", 20, "automatic"),
    (2, 2, 2, "high_quality", 1050, "2023-03-10 10:00:00", 50, "manual");

INSERT INTO consumer_transaction (id, product_id, logistic_center_id, consumer_id, product_category, 
                                  amount_kg, date, price, storage_type) VALUES 
    (1, 1, 1, 1, "high_quality", 1000, "2022-03-10 10:00:00", 10, "automatic"), 
    (2, 2, 1, 2, "medium_quality", 2000, "2022-03-14 10:00:00", 5, "manual"),
    (3, 1, 2, 1, "low_quality", 100, "2022-04-10 10:00:00", 20, "automatic"),
    (4, 2, 2, 2, "high_quality", 1050, "2023-03-10 10:00:00", 50, "manual");

INSERT INTO actual_stock (product_id, logistic_center_id, product_category, 
                          amount_kg, date) VALUES 
    (1, 1, "high_quality", 1000, "2022-03-10 10:00:00"), 
    (2, 1, "medium_quality", 2000, "2022-03-14 10:00:00"),
    (1, 2, "low_quality", 100, "2022-04-10 10:00:00"),
    (2, 2, "high_quality", 1050, "2023-03-10 10:00:00");

INSERT INTO estimated_stock (product_id, logistic_center_id, product_category, 
                             amount_kg, date) VALUES 
    (1, 1, "high_quality", 1000, "2022-03-10 10:00:00"), 
    (2, 1, "medium_quality", 2000, "2022-03-14 10:00:00"),
    (1, 2, "low_quality", 100, "2022-04-10 10:00:00"),
    (2, 2, "high_quality", 1050, "2023-03-10 10:00:00");