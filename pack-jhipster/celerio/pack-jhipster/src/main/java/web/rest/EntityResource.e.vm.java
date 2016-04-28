$output.java($entity.rest)

$output.require("com.codahale.metrics.annotation.Timed")##
$output.require($entity.model)##
$output.require("$entity.primaryKey.fullType")##
$output.require($entity.repository)##
$output.require("${Root.packageName}.web.rest.util.HeaderUtil")##
$output.require("org.slf4j.Logger")##
$output.require("org.slf4j.LoggerFactory")##
$output.require("org.springframework.http.HttpHeaders")##
$output.require("org.springframework.http.HttpStatus")##
$output.require("org.springframework.http.MediaType")##
$output.require("org.springframework.http.ResponseEntity")##
$output.require("org.springframework.web.bind.annotation.*")##

$output.require("javax.inject.Inject")##
$output.require("javax.validation.Valid")##
$output.require("java.net.URI")##
$output.require("java.net.URISyntaxException")##
$output.require("java.util.List")##
$output.require("java.util.Optional")##

/**
 * REST controller for managing ${entity.model.type}.
 */
@RestController
@RequestMapping("/api")
public class $output.currentClass {

    private final Logger log = LoggerFactory.getLogger(${output.currentClass}.class);
        
    @Inject
    private $entity.repository.type $entity.repository.var;
    
    /**
     * POST  /$entity.model.vars : Create a new ${entity.model.var}.
     *
     * @param $entity.model.var the $entity.model.var to create
     * @return the ResponseEntity with status 201 (Created) and with body the new $entity.model.var, or with status 400 (Bad Request) if the $entity.model.var has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/$entity.model.vars",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<${entity.model.type}> create${entity.model.Type}(@Valid @RequestBody $entity.model.type $entity.model.var) throws URISyntaxException {
        log.debug("REST request to save $entity.model.type : {}", $entity.model.var);
        if (${entity.model.var}.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("$entity.model.var", "idexists", "A new $entity.model.var cannot already have an ID")).body(null);
        }
        $entity.model.type result = ${entity.repository.var}.save($entity.model.var);
        return ResponseEntity.created(new URI("/api/$entity.model.vars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("$entity.model.var", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /${entity.model.vars} : Updates an existing ${entity.model.var}.
     *
     * @param ${entity.model.var} the ${entity.model.var} to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ${entity.model.var},
     * or with status 400 (Bad Request) if the ${entity.model.var} is not valid,
     * or with status 500 (Internal Server Error) if the ${entity.model.var} couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/${entity.model.vars}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<${entity.model.type}> update${entity.model.type}(@Valid @RequestBody ${entity.model.type} ${entity.model.var}) throws URISyntaxException {
        log.debug("REST request to update ${entity.model.type} : {}", ${entity.model.var});
        if (${entity.model.var}.getId() == null) {
            return create${entity.model.type}(${entity.model.var});
        }
        ${entity.model.type} result = ${entity.repository.var}.save(${entity.model.var});
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("${entity.model.var}", ${entity.model.var}.getId().toString()))
            .body(result);
    }

    /**
     * GET  /${entity.model.vars} : get all the ${entity.model.vars}.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ${entity.model.vars} in body
     */
    @RequestMapping(value = "/${entity.model.vars}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<${entity.model.type}> getAll${entity.model.varsUp}() {
        log.debug("REST request to get all ${entity.model.varsUp}");
        List<${entity.model.type}> ${entity.model.vars} = ${entity.repository.var}.findAll();
        return ${entity.model.vars};
    }

    /**
     * GET  /${entity.model.vars}/:id : get the "id" ${entity.model.var}.
     *
     * @param id the id of the ${entity.model.var} to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ${entity.model.var}, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/${entity.model.vars}/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<${entity.model.type}> ${entity.model.getter}(@PathVariable $entity.primaryKey.type id) {
        log.debug("REST request to get ${entity.model.type} : {}", id);
        ${entity.model.type} ${entity.model.var} = ${entity.repository.var}.findOne(id);
        return Optional.ofNullable(${entity.model.var})
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /${entity.model.vars}/:id : delete the "id" ${entity.model.var}.
     *
     * @param id the id of the ${entity.model.var} to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/${entity.model.vars}/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete${entity.model.type}(@PathVariable $entity.primaryKey.type id) {
        log.debug("REST request to delete ${entity.model.type} : {}", id);
        ${entity.repository.var}.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("${entity.model.var}", id.toString())).build();
    }

}
