create table if not exists "user"
(
    id integer primary key,
    group_id integer not null,
    first_name varchar(30),
    last_name varchar(30),
    constraint fk_admin
        foreign key (group_id)
            references user_group (id)
);

create table if not exists user_group
(
    id integer primary key,
    "name" varchar(50) not null,
    admin_id integer not null,
    constraint fk_admin
        foreign key (admin_id)
            references category (id)
);

create table if not exists category
(
    id integer primary key,
    "name" varchar(50) not null,
    group_id integer not null ,
    constraint fk_group
        foreign key (group_id)
            references user_group (id)
);

create table if not exists product
(
    id integer primary key,
    category_id integer not null,
    price float,
    description varchar(255),
    deleted boolean default false,
    author_id integer not null,
    created_at timestamp,

    constraint fk_category
        foreign key (category_id)
            references category (id)
);