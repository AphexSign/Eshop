-- USERS
create sequence user_seq start 1 increment 1;

create table users (
                       id int8 not null,
                       archive boolean not null,
                       email varchar(255),
                       name varchar(255),
                       fio varchar(255),
                       password varchar(255),
                       role varchar(255),
                       address varchar(255),
                       telephone varchar(255),
                       primary key (id)
);

-- PRODUCTS
create sequence product_seq start 1 increment 1;

create table products (
                          id int8 not null,
                          title varchar(255),
                          weight float8,
                          price float8,
                          date_manufactured date,
                          date_expire date,
                          active boolean not null,
                          primary key (id)
);

-- CART
create sequence cart_seq start 1 increment 1;

create table carts (
                       id int8 not null,
                       user_id int8,
                       primary key (id)
);

-- LINK BETWEEN CART AND USER
alter table if exists carts
    add constraint carts_fk_user
        foreign key (user_id) references users;


-- PRODUCTS IN CART
create table cart_products (
                               cart_id int8 not null,
                               product_id int8 not null
);

alter table if exists cart_products
    add constraint bucket_products_fk_product
        foreign key (product_id) references products;

alter table if exists cart_products
    add constraint bucket_products_fk_bucket
        foreign key (cart_id) references carts;


-- ORDERS
create sequence order_seq start 1 increment 1;

create table orders (
                        id int8 not null,
                        address varchar(255),
                        changed timestamp,
                        created timestamp,
                        status varchar(255),
                        paymethod varchar(100),
                        sum numeric(19, 2),
                        user_id int8,
                        primary key (id)
);

alter table if exists orders
    add constraint orders_fk_user
        foreign key (user_id) references users;

-- DETAILS OF ORDER
create sequence order_details_seq start 1 increment 1;

create table orders_details (
                                id int8 not null,
                                order_id int8 not null,
                                product_id int8 not null,
                                amount numeric(19, 2),
                                price numeric(19, 2),
                                primary key (id)
);

alter table if exists orders_details
    add constraint orders_details_fk_order
        foreign key (order_id) references orders;

alter table if exists orders_details
    add constraint orders_details_fk_product
        foreign key (product_id) references products;