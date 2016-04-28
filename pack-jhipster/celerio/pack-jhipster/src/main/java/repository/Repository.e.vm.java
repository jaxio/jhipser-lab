$output.java($entity.repository)

$output.require($entity.model)##
$output.require($entity.root.primaryKey)##
$output.require("org.springframework.data.jpa.repository.*")##
$output.require("java.util.List")##

/**
 * Spring Data JPA repository for the $entity.model.type entity.
 */
public interface $output.currentClass extends JpaRepository<${entity.model.type}, ${entity.root.primaryKey.type}>{

}
