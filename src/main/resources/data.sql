INSERT INTO USERS (name, email, password, calories_per_day)
VALUES ('User', 'user@yandex.ru', '{noop}password', 2005),
       ('Admin', 'admin@gmail.com', '{noop}admin', 1900),
       ('Guest', 'guest@gmail.com', '{noop}guest', 2000);

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);