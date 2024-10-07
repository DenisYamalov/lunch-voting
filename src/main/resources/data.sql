INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('SecondUser', 'seconduser@yandex.ru', '{noop}password');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3);

INSERT INTO restaurant (name)
VALUES ('KFC'),
       ('McDonalds'),
       ('Burger King');

INSERT INTO dish (name, price, menu_date, restaurant_id)
VALUES ('Hamburger', 100, CURRENT_DATE(), 1),
       ('Cheeseburger', 150, CURRENT_DATE(), 1),
       ('Big Mac', 200, CURRENT_DATE(), 2),
       ('McChicken', 100, CURRENT_DATE(), 2),
       ('Whopper', 150, CURRENT_DATE(), 3),
       ('Bacon King', 200, CURRENT_DATE(), 3),
       ('Hamburger', 100, '2024-10-01', 1),
       ('Cheeseburger', 150, '2024-10-01', 1),
       ('Hamburger', 100, '2024-10-02', 1),
       ('Cheeseburger', 150, '2024-10-02', 1);

INSERT INTO vote (user_id, restaurant_id)
VALUES (1, 1),
       (2, 2),
       (3, 2);
