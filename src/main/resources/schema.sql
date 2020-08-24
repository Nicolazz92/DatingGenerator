create sequence hibernate_sequence start with 1000 increment by 1;

CREATE TABLE MATCH
(
    id                 LONG PRIMARY KEY NOT NULL,
--     person1id          LONG             NOT NULL,
--     person2id          LONG             NOT NULL,
    creation_date_time TIMESTAMP
--     UNIQUE (person1id, person2id)
);

CREATE TABLE USER
(
    id          LONG PRIMARY KEY NOT NULL,
    name        VARCHAR(255),
    sex         VARCHAR(10),
    image       BLOB,
    body_type   VARCHAR(10),
    description VARCHAR(255),
    height      DOUBLE
);

CREATE TABLE PERSON_JOIN_TABLE
(
    PERSON_ONE_ID LONG,
    PERSON_TWO_ID LONG,
    MATCH_ID      LONG
)