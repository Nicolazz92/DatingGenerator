create sequence hibernate_sequence start with 1000 increment by 1;

CREATE TABLE MATCH
(
    id                 LONG PRIMARY KEY NOT NULL,
    creation_date_time TIMESTAMP
);

CREATE TABLE USER
(
    id               LONG PRIMARY KEY NOT NULL,
    name             VARCHAR(255),
    gender           VARCHAR(10),
    image            BLOB,
    age              INTEGER,
    body_type        VARCHAR(10),
    description      VARCHAR(255),
    height           DOUBLE,
    search_filter_id LONG             NOT NULL
);

CREATE TABLE PERSON_JOIN_TABLE
(
    PERSON_ID LONG,
    MATCH_ID  LONG,
    primary key (PERSON_ID, MATCH_ID)
);

CREATE TABLE SEARCH_FILTER
(
    id         LONG PRIMARY KEY NOT NULL,
    gender     VARCHAR(10),
    age_min    INTEGER,
    age_max    INTEGER,
    height_min DOUBLE,
    height_max DOUBLE,
    body_types VARCHAR(255)
);

CREATE TABLE BODY_TYPES
(
    id         LONG NOT NULL,
    body_types VARCHAR(10),
    PRIMARY KEY (id, body_types)
)