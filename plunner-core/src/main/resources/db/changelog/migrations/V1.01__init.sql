create table if not exists person
(
    id serial primary key,
    group_id integer not null,
    first_name varchar(30),
    last_name varchar(30)
);

create table if not exists user_group
(
    id serial primary key,
    admin_id integer
);

create table if not exists category
(
    id serial primary key,
    "name" varchar(50) not null,
    group_id integer not null
);

create table if not exists product
(
    id serial primary key,
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

alter table person
    add constraint fk_user_group
        foreign key (group_id)
            references user_group (id);

alter table user_group
    add constraint fk_group_admin
        foreign key (admin_id)
            references person (id);

alter table "category"
    add constraint fk_category_group
        foreign key (group_id)
            references user_group (id);