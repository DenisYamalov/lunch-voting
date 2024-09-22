DROP TABLE user_role IF EXISTS;
DROP TABLE dish IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE restaurant IF EXISTS;
DROP TABLE vote IF EXISTS;

CREATE TABLE users
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL
);

CREATE UNIQUE INDEX users_unique_email_idx
    ON USERS (email);

CREATE TABLE user_role
(
    user_id INTEGER      NOT NULL,
    role    VARCHAR(255) NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE restaurant
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE dish
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    price         BIGINT       NOT NULL,
    menu_date     DATE         NOT NULL DEFAULT CURRENT_DATE,
    restaurant_id INTEGER      NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX dish_unique_idx
    ON dish (name, price, menu_date, restaurant_id);

CREATE TABLE vote
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    date_time     TIMESTAMP NOT NULL,
    user_id       INTEGER   NOT NULL,
    restaurant_id INTEGER   NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX vote_unique_date_user_idx
    ON vote (date_time, user_id);