ALTER TABLE product MODIFY COLUMN category int;
ALTER TABLE product MODIFY COLUMN spended_by int;

ALTER TABLE product CHANGE COLUMN `category` `category_id` int;
ALTER TABLE product CHANGE COLUMN `spended_by` `user_id` int;
