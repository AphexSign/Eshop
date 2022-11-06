INSERT INTO order_status (id, title) VALUES (1, 'Новый');
INSERT INTO order_status (id, title) VALUES (2, 'Зарезервирован');
INSERT INTO order_status (id, title) VALUES (3, 'Отменен');
INSERT INTO order_status (id, title) VALUES (4, 'Оплачен');
INSERT INTO order_status (id, title) VALUES (5, 'Завершен');

ALTER SEQUENCE order_status_seq RESTART WITH 6;