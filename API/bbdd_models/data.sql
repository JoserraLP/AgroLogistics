INSERT INTO product (name) VALUES 
    ("Potato"), ("Carrot"), 
    ("Strawberry"), ("Apple");

INSERT INTO logistic_center (name, capacity_kg, cooled_capacity_kg, email, password) VALUES 
    ("Caceres Logistic Center", 10000, 2000, "cc_logistic_center@gmail.com", '[2, 89, 89, 224, 26, 18, 61, 96, 201, 10, 115, 111, 146, 72, 157, 107, 120, 14, 12, 109, 73, 252, 73, 191, 187, 183, 199, 70, 10, 38, 89, 128, 131, 232, 178, 129, 173, 200, 94, 23, 215, 132, 171, 36, 44, 163, 117, 153, 168, 189, 215, 130, 58, 146, 68, 230, 180, 135, 128, 109, 57, 16, 192, 26]'), 
    ("Badajoz Logistic Center", 20000, 5000, "ba_logistic_center@gmail.com", '[2, 89, 89, 224, 26, 18, 61, 96, 201, 10, 115, 111, 146, 72, 157, 107, 120, 14, 12, 109, 73, 252, 73, 191, 187, 183, 199, 70, 10, 38, 89, 128, 131, 232, 178, 129, 173, 200, 94, 23, 215, 132, 171, 36, 44, 163, 117, 153, 168, 189, 215, 130, 58, 146, 68, 230, 180, 135, 128, 109, 57, 16, 192, 26]');

INSERT INTO producer (name, email, password) VALUES 
    ("Farmer Lopez", "p_lopez@gmail.com", '[2, 89, 89, 224, 26, 18, 61, 96, 201, 10, 115, 111, 146, 72, 157, 107, 120, 14, 12, 109, 73, 252, 73, 191, 187, 183, 199, 70, 10, 38, 89, 128, 131, 232, 178, 129, 173, 200, 94, 23, 215, 132, 171, 36, 44, 163, 117, 153, 168, 189, 215, 130, 58, 146, 68, 230, 180, 135, 128, 109, 57, 16, 192, 26]'), 
    ("Farmer Antonio", "p_antonio@gmail.com", '[2, 89, 89, 224, 26, 18, 61, 96, 201, 10, 115, 111, 146, 72, 157, 107, 120, 14, 12, 109, 73, 252, 73, 191, 187, 183, 199, 70, 10, 38, 89, 128, 131, 232, 178, 129, 173, 200, 94, 23, 215, 132, 171, 36, 44, 163, 117, 153, 168, 189, 215, 130, 58, 146, 68, 230, 180, 135, 128, 109, 57, 16, 192, 26]'),
    ("Farmer Jose", "p_jose@gmail.com", '[2, 89, 89, 224, 26, 18, 61, 96, 201, 10, 115, 111, 146, 72, 157, 107, 120, 14, 12, 109, 73, 252, 73, 191, 187, 183, 199, 70, 10, 38, 89, 128, 131, 232, 178, 129, 173, 200, 94, 23, 215, 132, 171, 36, 44, 163, 117, 153, 168, 189, 215, 130, 58, 146, 68, 230, 180, 135, 128, 109, 57, 16, 192, 26]');

INSERT INTO consumer (name, email, password) VALUES 
    ("Caceres Greengrocer", "c_caceres@gmail.com", '[2, 89, 89, 224, 26, 18, 61, 96, 201, 10, 115, 111, 146, 72, 157, 107, 120, 14, 12, 109, 73, 252, 73, 191, 187, 183, 199, 70, 10, 38, 89, 128, 131, 232, 178, 129, 173, 200, 94, 23, 215, 132, 171, 36, 44, 163, 117, 153, 168, 189, 215, 130, 58, 146, 68, 230, 180, 135, 128, 109, 57, 16, 192, 26]'), 
    ("Lola's Greengrocer", "c_lola@gmail.com", '[2, 89, 89, 224, 26, 18, 61, 96, 201, 10, 115, 111, 146, 72, 157, 107, 120, 14, 12, 109, 73, 252, 73, 191, 187, 183, 199, 70, 10, 38, 89, 128, 131, 232, 178, 129, 173, 200, 94, 23, 215, 132, 171, 36, 44, 163, 117, 153, 168, 189, 215, 130, 58, 146, 68, 230, 180, 135, 128, 109, 57, 16, 192, 26]'),
    ("UltraGreengrocer", "c_ultra@gmail.com", '[2, 89, 89, 224, 26, 18, 61, 96, 201, 10, 115, 111, 146, 72, 157, 107, 120, 14, 12, 109, 73, 252, 73, 191, 187, 183, 199, 70, 10, 38, 89, 128, 131, 232, 178, 129, 173, 200, 94, 23, 215, 132, 171, 36, 44, 163, 117, 153, 168, 189, 215, 130, 58, 146, 68, 230, 180, 135, 128, 109, 57, 16, 192, 26]');

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