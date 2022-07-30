DROP TABLE IF EXISTS users;

CREATE SEQUENCE IF NOT EXISTS users_id_sec START 0;

CREATE TABLE users (
    id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);