INSERT INTO users (id, address, archive, email, fio, name, password, role, telephone)
VALUES (1, 'г. Инза, ул. Мира, д.1',false, 'admin@eshop.ru', 'Иванов Иван Иванович', 'admin', '$2a$10$lMTU6ygUu4UZk2FEcGeLrO4CnKroHcWfI6Op6wnXvcbD1S0YVjp5m', 'ADMIN', '79991234567');

ALTER SEQUENCE user_seq RESTART WITH 2;