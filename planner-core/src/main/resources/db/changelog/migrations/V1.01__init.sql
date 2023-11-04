create table if not exists person
(
    id uuid primary key,
    first_name varchar(30),
    last_name varchar(30)
);

create table if not exists category
(
    id uuid primary key,
    "name" varchar(50) not null,
    user_id uuid not null
);

create table if not exists product
(
    id uuid primary key,
    category_id uuid not null,
    price decimal,
    description varchar(255),
    deleted boolean default false,
    author_id integer not null,
    created_at timestamp,
    constraint fk_category
        foreign key (category_id)
            references category (id)
);

alter table "category"
    add constraint fk_category_user
        foreign key (user_id)
            references person (id);