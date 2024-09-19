INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurant (name)
VALUES ('KFC'),
       ('McDonalds'),
       ('Burger King');

INSERT INTO dish (name, price, restaurant_id)
VALUES ('Hamburger', 100, 1),
       ('Cheeseburger', 150, 1),
       ('Big Mac', 200, 2),
       ('McChicken', 100, 2),
       ('Whopper', 150, 3),
       ('Bacon King', 200, 3);

INSERT INTO vote (user_id, restaurant_id, date_time)
VALUES (1, 1, '2022-01-01 12:00:00'),
       (2, 2, '2022-01-02 12:00:00'),
       (3, 2, '2022-01-03 12:00:00');
