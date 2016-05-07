---
-- Example Schema
--

DROP ALL OBJECTS;

CREATE SEQUENCE hibernate_sequence START WITH 1000;

-- JHIPSTER TABLES... we don't rely on liquibase stuff.

CREATE TABLE jhi_user (
    id                  int not null IDENTITY,
    login                varchar(50),
    password_hash                varchar(60),
    first_name                varchar(50),
    last_name                varchar(50) not null,
    email                varchar(100),
    activated           bool not null,
    lang_key            varchar(5),
    activation_key            varchar(20),
    reset_key            varchar(20),
    created_by            varchar(50) not null,
    created_date            timestamp not null default now(),
    reset_date            timestamp,
    last_modified_by    varchar(50),
    last_modified_date         timestamp,
    constraint idx_user_login unique (login),
    constraint idx_user_email unique (email),
    primary key (id)
);

CREATE TABLE jhi_authority (
    name                  varchar(50) not null,
    primary key (name)
);

CREATE TABLE jhi_user_authority (
    user_id                  int not null,
    authority_name                  varchar(50) not null,

    constraint fk_user_id foreign key (user_id) references jhi_user,
    constraint fk_authority_name foreign key (authority_name) references jhi_authority,

    primary key (user_id, authority_name)
);

CREATE TABLE jhi_persistent_token (
    series                  varchar(255) not null,
    user_id                  int not null,
    token_value                  varchar(255) not null,
    token_date                  date,
    ip_address                  varchar(39),
    user_agent                  varchar(255),
    constraint fk_user_persistent_token foreign key (user_id) references jhi_user,

    primary key (series)
);

CREATE TABLE jhi_persistent_audit_event (
    event_id                  int not null IDENTITY,
    principal                  varchar(255) not null,
  event_date        TIMESTAMP ,
  event_type    varchar(255),
-- TODO : indexes
    primary key (event_id)
);

CREATE TABLE jhi_persistent_audit_evt_data (
    event_id                  int not null,
    name                  varchar(255) not null,
    value                  varchar(255),
  event_type    varchar(255),
-- TODO : indexes
    constraint fk_evt_pers_audit_evt_data foreign key (event_id) references jhi_persistent_audit_event,
    primary key (event_id, name)
);

-- POPULATE JHIPSTER TABLES

INSERT INTO JHI_AUTHORITY(name) VALUES('ROLE_ADMIN');
INSERT INTO JHI_AUTHORITY(name) VALUES('ROLE_USER');

INSERT INTO JHI_USER(id,login,password_hash,first_name,last_name,email,activated,lang_key,created_by) VALUES (1,'system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','System','System','system@localhost',true,'en','system');
INSERT INTO JHI_USER(id,login,password_hash,first_name,last_name,email,activated,lang_key,created_by) VALUES (2,'anonymousUser','$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO','Anonymous','User','anonymous@localhost',true,'en','system');
INSERT INTO JHI_USER(id,login,password_hash,first_name,last_name,email,activated,lang_key,created_by) VALUES (3,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost',true,'en','system');
INSERT INTO JHI_USER(id,login,password_hash,first_name,last_name,email,activated,lang_key,created_by) VALUES (4,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost',true,'en','system');

INSERT INTO jhi_user_authority(user_id, authority_name)  VALUES(1, 'ROLE_ADMIN');
INSERT INTO jhi_user_authority(user_id, authority_name) VALUES (1, 'ROLE_USER');
INSERT INTO jhi_user_authority(user_id, authority_name)  VALUES(3, 'ROLE_ADMIN');
INSERT INTO jhi_user_authority(user_id, authority_name) VALUES (3, 'ROLE_USER');
INSERT INTO jhi_user_authority(user_id, authority_name) VALUES (4, 'ROLE_USER');

-- OUR TABLES...

CREATE TABLE AUTHOR (
    id                  int not null IDENTITY,
    name                varchar(100),
    birthday            date,
    description         varchar(100),
    primary key (id)
);

CREATE TABLE BOOK (
    id                  int not null IDENTITY,
    title               varchar(100) not null,
    price               decimal(20, 2) not null,
    author_id           int not null,

    constraint book_fk_1 foreign key (author_id) references AUTHOR,
    primary key (id)
);


CREATE TABLE SHELVE (
    id                  int not null IDENTITY,
    name                varchar(100),
    user_id         int not null,
    constraint shelve_user_id_fk foreign key (user_id) references jhi_user,
    primary key (id)
);


CREATE TABLE VARIOUS (
    id                  int not null IDENTITY,
    zdt                 TIMESTAMP default now(),
    primary key (id)
);