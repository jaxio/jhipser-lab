## JHipster Lab

Basic idea: try to generate a `jhipster like` application using Celerio instead of JHipster.

Many people ask for DB reverse engineering in JHipster and apparently 
this is not so easy to do... whereas this is definitely a task Celerio can handle.

`pack-jhipster`: Celerio Templates...

Note: only templates present in the folder `pack-jhipster/celerio/..../webapp/app/entities/` are really dynamic, all the
other ones were simply copy pasted from a jhipster app.
In this app, 2 entities were added using jhipster generators, so there are still some hard coded references to these entities,
in particular in liquibase files.

The dynamic templates offer basic support (not as good as jhipster for the moment), this is in progress, we need to support
calendar, files, etc..


`src/main/config`: contains celerio conf

`src/main/sql`: contains SQL script for additional tables (Book, Author) that get reversed...

IMPORTANT: jhipster uses liquibase (Celerio does not).

## Install JS dep

    bower install

## Install the Celerio Angular SPI

    cd celerio-spi-angular
    mvn clean install

## Generate the application

    mvn -Pdb,metadata,gen generate-sources

It will create an H2 database on disk, reverse it and execute the template above.

## Run the application
    
    mvn spring-boot:run

## Access the application

    http://localhost:8080/

## Clean the generated stuff:

    mvn -PcleanGen clean

# TODOS:

Urgent:
* address issue with liquibase vs SQL Script

In webapp/app/entities templates:

* support calendar
* support file upload/download (blob)
* support more validation
* support more relations
* support x-to-one display field
* support elastic search
* etc...

Other templates:
* support JPA entity
* support resources
* etc...
