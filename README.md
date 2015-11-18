## JHipster Lab

Simple (proof of concept) Celerio code generation template to generate JHipster json entity files. 

Template is in : `src/main/celerio`

Run

    mvn -Pdb,metadata,gen generate-sources

It will create an H2 database on disk, reverse it and execute the template above.

Then check: `.jhipster` folder