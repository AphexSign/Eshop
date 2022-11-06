INSERT INTO products (id, title, weight, price, date_manufactured, date_expire, active, category_id) VALUES (1, 'Молоко', 1, 75, '2022-10-25', '2022-10-31', true, 2);
INSERT INTO products (id, title, weight, price, date_manufactured, date_expire, active, category_id) VALUES (2, 'Кефир', 1, 73, '2022-10-25', '2022-10-25', true, 2);
INSERT INTO products (id, title, weight, price, date_manufactured, date_expire, active, category_id) VALUES (3, 'Хлеб "Пастуший"', 0.5, 37, '2022-10-25', '2022-10-28', true, 1);
INSERT INTO products (id, title, weight, price, date_manufactured, date_expire, active, category_id) VALUES (4, 'Батон "Городской"', 0.3, 35, '2022-10-25', '2022-10-28', true, 1);
INSERT INTO products (id, title, weight, price, date_manufactured, date_expire, active, category_id) VALUES (5, 'Говядина, охлажд.', 1, 700, '2022-10-25', '2022-10-30', true, 3);
INSERT INTO products (id, title, weight, price, date_manufactured, date_expire, active, category_id) VALUES (6, 'Свинина, охлажд.', 1, 500, '2022-10-25', '2022-10-30', true, 3);
INSERT INTO products (id, title, weight, price, date_manufactured, date_expire, active, category_id) VALUES (7, 'Яйцо, кур, 1 ст., 10 шт.', 0.5, 80, '2022-10-25', '2022-11-25', true, 4);
INSERT INTO products (id, title, weight, price, date_manufactured, date_expire, active, category_id) VALUES (8, 'Яйцо перепелиное, 20 шт', 0.3, 160, '2022-10-25', '2022-11-25', true, 4);
INSERT INTO products (id, title, weight, price, date_manufactured, date_expire, active, category_id) VALUES (9, 'Филе трески, морож.', 1, 400, '2022-10-25', '2023-04-25', true, 5);
INSERT INTO products (id, title, weight, price, date_manufactured, date_expire, active, category_id) VALUES (10, 'Консервы рыбные, "Шпроты"', 0.3, 120, '2022-10-25', '2023-10-25', true, 5);

ALTER SEQUENCE product_seq RESTART WITH 11;