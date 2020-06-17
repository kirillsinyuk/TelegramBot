ALTER TABLE product MODIFY COLUMN category_id int unsigned;

ALTER TABLE product MODIFY COLUMN user_id int unsigned;


ALTER TABLE product
  ADD CONSTRAINT FK_ProductCategory
    FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE product
  ADD CONSTRAINT FK_ProductUser
    FOREIGN KEY (user_id) REFERENCES bot_user (id);
