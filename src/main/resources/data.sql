INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

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
VALUES (1, 1, CURRENT_TIMESTAMP(0)),
       (2, 2, CURRENT_TIMESTAMP(0));
