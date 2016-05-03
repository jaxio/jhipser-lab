## JHipster Lab

[![Build Status](https://travis-ci.org/jaxio/jhipser-lab.svg?branch=master)](https://travis-ci.org/jaxio/jhipser-lab)

Basic idea: try to generate a `jhipster like` application using Celerio instead of JHipster.

Many people ask for DB reverse engineering in JHipster and apparently 
this is not so easy to do... whereas this is definitely a task Celerio can handle.

## Celerio packs

* `pack-jhipster`: Celerio dynamic Templates (interpreted by Celerio Engine)

* `pack-jhipster-static`: Celerio static Templates (copied as is without interpretation)

* `src/main/config`: contains celerio conf

* `src/main/sql`: contains SQL script for jhipster tables + additional tables (Book, Author). You may add other tables...

## IMPORTANT NOTES

The application does not rely on liquibase.
The jhi_* tables are ignored by Celerio engine. So currently the additional tables cannot reference them.

## Install JS dep

    bower install

## Install the Celerio Angular SPI

    cd celerio-spi-angular
    mvn clean install

## Create/reverse the database + generate the application

    mvn -Pdb,metadata,gen generate-sources

## Run the application
    
    mvn spring-boot:run

## Access the application

    http://localhost:8080/

## Clean the generated stuff:

    mvn -PcleanGen clean

# TODOS:

* [done] address issue with liquibase vs SQL Script

In webapp/app/entities templates:

* [done] support calendar
* support file upload/download (blob)
* support more validation
* support more relations
* support x-to-one display field
* support elastic search
* etc...

Other templates:
* [done] support JPA entity
* [done] support resources
* etc...
