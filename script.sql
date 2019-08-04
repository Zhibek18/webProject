create table menu
(
    dish_name      varchar(50)    not null
        primary key,
    price          decimal(10, 2) not null,
    dish_name_en   varchar(50)    not null,
    dish_name_ru   varchar(50)    not null,
    description_en varchar(255)   not null,
    description_ru varchar(255)   not null
);

create table users
(
    login      varchar(20)          not null
        primary key,
    password   varchar(20)          not null,
    street     varchar(50)          null,
    house      varchar(10)          null,
    apartment  int                  null,
    phone      varchar(30)          null,
    first_name varchar(50)          null,
    is_admin   tinyint(1) default 0 not null
);

create table orders
(
    order_id   int auto_increment
        primary key,
    login      varchar(20)                         not null,
    timestamp  timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    total_cost float     default 0                 null,
    status     int(2)    default 0                 null,
    constraint orders_ibfk_1
        foreign key (login) references users (login)
            on update cascade on delete cascade
);

create table order_list
(
    order_id  int               not null,
    dish_name varchar(20)       not null,
    quantity  tinyint default 1 not null,
    primary key (order_id, dish_name),
    constraint order_list_ibfk_2
        foreign key (dish_name) references menu (dish_name)
            on update cascade on delete cascade,
    constraint order_list_ibfk_3
        foreign key (order_id) references orders (order_id)
            on update cascade on delete cascade
);

create index fk_user
    on orders (login);


