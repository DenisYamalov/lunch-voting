DELETE FROM user_role;
DELETE FROM dish;
DELETE FROM restaurant;
DELETE FROM vote;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('User', 100000),
       ('Admin', 100001),
       ('User2', 100002);

INSERT INTO restaurant (name)
VALUES ('Mcdonalds'),
       ('KFC'),
       ('Burger King');

INSERT INTO dish (name, price, restaurant_id)
VALUES ('Big Mac', 1000, 100003),
       ('Cheeseburger', 500, 100003),
       ('Fries', 300, 100003),
       ('Cola', 300, 100003),
       ('Famous Bowl Meal', 1000, 100004),
       ('Mac & Cheese', 100, 100004),
       ('Whopper', 200, 100005),
       ('Double Cheeseburger', 300, 100005);

INSERT INTO vote (date_time, user_id, restaurant_id)
VALUES (NOW(), 100000, 100003),
       (NOW(), 100001, 100005),
       (NOW(), 100002, 100005);
