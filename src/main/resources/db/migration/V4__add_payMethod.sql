INSERT INTO pay_method (id, title) VALUES (1, 'Наличная оплата');
INSERT INTO pay_method (id, title) VALUES (2, 'Банковская оплата');

ALTER SEQUENCE pay_method_seq RESTART WITH 3;
