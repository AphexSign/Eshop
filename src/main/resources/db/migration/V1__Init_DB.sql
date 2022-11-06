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

--Category
create sequence category_seq start 1 increment 1;

create table category(
                         id int8 not null,
                         title varchar(255),
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
                          category_id int8,
                          primary key (id)
);
alter table if exists products
    add constraint products_fk_category
        foreign key (category_id) references category;



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


-- OrderStatus

create sequence order_status_seq start 1 increment 1;
create table order_status (
                        id int8 not null,
                        title varchar(100),
                        primary key (id)
);

-- PayMethod
create sequence pay_method_seq start 1 increment 1;
create table pay_method (
                              id int8 not null,
                              title varchar(100),
                              primary key (id)
);


-- ORDERS
create sequence order_seq start 1 increment 1;

create table orders (
                        id int8 not null,
                        address varchar(255),
                        changed timestamp,
                        created timestamp,
                        status_id int8 not null,
                        pay_method_id int8 not null,
                        sum numeric(19, 2),
                        user_id int8,
                        primary key (id)
);

alter table if exists orders
    add constraint orders_fk_user
        foreign key (user_id) references users;

alter table if exists orders
    add constraint orders_fk_status
        foreign key (status_id) references order_status;

alter table if exists orders
    add constraint orders_fk_pay
        foreign key (pay_method_id) references pay_method;


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


-- Images
create sequence images_seq start 1 increment 1;

create table images(
                       id int8 not null,
                       name varchar(255),
                       originalFileName varchar(255),
                       size int8,
                       contentType varchar(255),
                       content bytea,
                       primary key (id)
);


-- News
create sequence news_seq start 1 increment 1;
create table news (
                      id int8 not null,
                      title varchar(255),
                      message text,
                      created timestamp,
                      changed timestamp,
                      active boolean not null,
                      user_id int8,
                      image_id int8,
                      primary key (id)
);

alter table if exists news
    add constraint news_fk_users
        foreign key (user_id) references users;

alter table if exists news
    add constraint news_fk_image
        foreign key (image_id) references images;
