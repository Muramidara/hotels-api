DROP TABLE IF EXISTS address CASCADE;
DROP TABLE IF EXISTS contacts CASCADE;
DROP TABLE IF EXISTS arrival_time CASCADE;
DROP TABLE IF EXISTS amenity CASCADE;
DROP TABLE IF EXISTS hotel CASCADE;
DROP TABLE IF EXISTS hotel_amenity CASCADE;

CREATE TABLE address
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    house_number INT          NOT NULL,
    street       VARCHAR(128) NOT NULL,
    city         VARCHAR(64)  NOT NULL,
    country      VARCHAR(64)  NOT NULL,
    post_code    VARCHAR(64)  NOT NULL
);
CREATE TABLE contacts
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(128) NOT NULL,
    phone VARCHAR(64)  NOT NULL
);
CREATE TABLE arrival_time
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    check_in  VARCHAR(16) NOT NULL,
    check_out VARCHAR(16) NOT NULL
);
CREATE TABLE amenity
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(64) NOT NULL
);

CREATE TABLE hotel
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(128) NOT NULL,
    description     TEXT         NOT NULL,
    brand           VARCHAR(64)  NOT NULL,
    address_id      BIGINT REFERENCES address (id),
    contacts_id     BIGINT REFERENCES contacts (id),
    arrival_time_id BIGINT REFERENCES arrival_time (id)
);
CREATE TABLE hotel_amenity
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    hotel_id   BIGINT REFERENCES hotel (id),
    amenity_id BIGINT REFERENCES amenity (id)
);
