DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id         BIGINT AUTO_INCREMENT,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);