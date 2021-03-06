create sequence hibernate_sequence start with 1000 increment by 1;

CREATE TABLE MATCH
(
    id                 LONG PRIMARY KEY NOT NULL,
    creation_date_time TIMESTAMP
);

CREATE TABLE USER
(
    id            LONG PRIMARY KEY NOT NULL,
    name          VARCHAR(255),
    gender        VARCHAR(10),
    photo         BLOB,
    age           INTEGER,
    body_type     VARCHAR(10),
    description   VARCHAR(255),
    height        INTEGER,

    filter_gender VARCHAR(10),
    age_min       INTEGER,
    age_max       INTEGER,
    height_min    INTEGER,
    height_max    INTEGER,

    username      varchar(50),
    password      varchar(255),
    enabled       boolean          not null default true
);

CREATE TABLE AUTHORITIES
(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL
);

CREATE TABLE PERSON_JOIN_TABLE
(
    PERSON_ID LONG,
    MATCH_ID  LONG,
    primary key (PERSON_ID, MATCH_ID)
);

CREATE TABLE BODY_TYPES
(
    user_id   LONG        NOT NULL,
    body_type VARCHAR(10) NOT NULL,
    PRIMARY KEY (user_id, body_type)
)