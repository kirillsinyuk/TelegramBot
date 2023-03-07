CREATE TABLE `Band`
(
  `id`        int unsigned NOT NULL AUTO_INCREMENT,
  `is_single` BIT(1),
  `admin_id`  int unsigned,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `Bot_user`
(
  id           int unsigned NOT NULL AUTO_INCREMENT,
  band_id     int unsigned NOT NULL,
  chat_id      int,
  user_id      int,
  first_name   varchar(25),
  last_name    varchar(25),
  username     varchar(25),
  laguage_code varchar(5),
  is_bot        BIT(1),
  PRIMARY KEY (`id`),
  CONSTRAINT `user_band_1` FOREIGN KEY (`band_id`) REFERENCES `Band` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `Category`
(
  id       int unsigned NOT NULL AUTO_INCREMENT,
  band_id int unsigned NOT NULL,
  name     varchar(25),
  PRIMARY KEY (`id`),
  CONSTRAINT `category_band_1` FOREIGN KEY (`band_id`) REFERENCES `Band` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;
