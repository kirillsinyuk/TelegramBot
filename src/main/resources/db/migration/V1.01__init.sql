CREATE TABLE IF NOT EXISTS product(
    id int primary key AUTO_INCREMENT,
    category varchar(255),
    price int,
    add_data timestamp,
    description varchar(255),
    spended_by varchar(255)
) CHARSET=UTF8;