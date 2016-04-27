---
-- Example Schema
--

DROP ALL OBJECTS;

CREATE SEQUENCE hibernate_sequence START WITH 1000;

CREATE TABLE AUTHOR (
    id                  int not null IDENTITY,
    name                varchar(100),
    birthday            timestamp,
    description         varchar(100),
    primary key (id)
);

CREATE TABLE BOOK (
    id                          char(36) not null,
    title                       varchar(100) not null,
    price                       decimal(20, 2) not null,
    author_id                   int not null,

    constraint book_fk_1 foreign key (author_id) references AUTHOR,
    primary key (id)
);
