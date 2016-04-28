## Copyright 2015 JAXIO http://www.jaxio.com
##
## Licensed under the Apache License, Version 2.0 (the "License");
## you may not use this file except in compliance with the License.
## You may obtain a copy of the License at
##
##    http://www.apache.org/licenses/LICENSE-2.0
##
## Unless required by applicable law or agreed to in writing, software
## distributed under the License is distributed on an "AS IS" BASIS,
## WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
## See the License for the specific language governing permissions and
## limitations under the License.
##
$output.java($entity.model)##

$output.require("com.google.common.base.MoreObjects")##
$output.require("java.util.Objects")##

#if($entity.hasComment())
/**
 * $entity.comment
 */
#end
#if ($output.isAbstract())
$output.dynamicAnnotation("javax.persistence.MappedSuperclass")
#end
#foreach($annotation in $entity.jpa.annotations)$annotation#end
#foreach($annotation in $entity.custom.annotations)$annotation#end

#if($entity.isRoot())
$output.require("java.io.Serializable")##
public#if ($output.isAbstract()) abstract#{end} class ${output.currentClass}${entity.spaceAndExtendsStatement} implements Serializable {
#else
$output.require($entity.parent.model)##
public#if ($output.isAbstract()) abstract#{end} class $output.currentClass extends $entity.parent.model.type {
#end
    private static final long serialVersionUID = 1L;
#if ($entity.isRoot() && $entity.primaryKey.isComposite())

    // Composite primary key
    private $entity.primaryKey.type $entity.primaryKey.var = new ${entity.primaryKey.type}();
#end
## --------------- Raw attributes (exception the one involved in XtoOneRelation)
#foreach ($attribute in $entity.nonCpkAttributes.list)
#if ($velocityCount == 1)

    // Raw attributes
#end
#if(!$attribute.isInFk() || $attribute.isSimplePk())
$output.require($attribute)##
    private $attribute.type $attribute.var;
#end
#end
## --------------- Many to One
#foreach ($manyToOne in $entity.manyToOne.list)
#if ($velocityCount == 1)

    // Many to one
#end
$output.require($manyToOne.to)##
    private $manyToOne.to.type $manyToOne.to.var;
#end
## --------------- One to One
#foreach ($oneToOne in $entity.oneToOne.list)
#if ($velocityCount == 1)

    // One to one
#end
$output.require($oneToOne.to)##
    private $oneToOne.to.type $oneToOne.to.var;
#end
## --------------- One to many
#foreach ($oneToMany in $entity.oneToMany.list)
#if ($velocityCount == 1)

    // One to many
#end
$output.require($entity.collectionType.fullType)##
$output.require($entity.collectionType.implementationFullType)##
$output.require($oneToMany.to)##
    $output.dynamicAnnotation("com.fasterxml.jackson.annotation.JsonIgnore")
    private ${entity.collectionType.type}<$oneToMany.to.type> $oneToMany.to.vars = new ${entity.collectionType.implementationType}<$oneToMany.to.type>();
#end
## --------------- Many to many
#foreach ($manyToMany in $entity.manyToMany.list)
#if ($velocityCount == 1)

    // Many to many
#end
$output.require($entity.collectionType.fullType)##
$output.require($entity.collectionType.implementationFullType)##
$output.require($manyToMany.to)##
    $output.dynamicAnnotation("com.fasterxml.jackson.annotation.JsonIgnore")
    private ${entity.collectionType.type}<$manyToMany.to.type> $manyToMany.to.vars = new ${entity.collectionType.implementationType}<$manyToMany.to.type>();
#end
#if ($entity.isRoot() && $entity.primaryKey.isComposite())

    // -----------------------
    // Composite Primary Key
    // -----------------------

    /**
     * Returns the composite primary key.
     */
    $output.dynamicAnnotation("javax.persistence.EmbeddedId")
#if($entity.entityConfig.hasTrueIndexed())
        // TODO search ?
#end
    public $entity.primaryKey.type ${entity.primaryKey.getter}() {
        return $entity.primaryKey.var;
    }

    /**
     * Set the composite primary key.
     * @param $entity.root.primaryKey.var the composite primary key.
     */
    public void ${entity.primaryKey.setter}($entity.primaryKey.type $entity.primaryKey.var) {
        this.$entity.primaryKey.var = $entity.primaryKey.var;
    }

    public ${output.currentRootClass} ${entity.primaryKey.with}($entity.primaryKey.type $entity.primaryKey.var) {
        ${entity.primaryKey.setter}($entity.primaryKey.var);
        return ${output.currentRootCast}this;
    }

    /**
     * Tells whether or not this instance has a non empty composite primary key set.
     * @return true if a non empty primary key is set, false otherwise
     */
    $output.dynamicAnnotation("javax.persistence.Transient")
    public boolean ${entity.primaryKey.has}() {
        return ${entity.primaryKey.getter}() != null && ${entity.primaryKey.getter}().areFieldsSet();
    }
#end

#foreach ($attribute in $entity.nonCpkAttributes.list)
#if(!$attribute.isInFk() || $attribute.isSimplePk())
    // -- [${attribute.var}] ------------------------

#if($attribute.hasComment())$attribute.javadoc#end
#foreach ($annotation in $attribute.custom.annotations)
    $annotation
#end
#foreach ($annotation in $attribute.validation.annotations)
    $annotation
#end
#foreach ($annotation in $attribute.jpa.annotations)
    $annotation
#end
#foreach ($annotation in $attribute.formatter.annotations)
    $annotation
#end
    public $attribute.type ${attribute.getter}() {
        return $attribute.var;
    }
#if ($attribute.isSetterAccessibilityPublic())

    public void ${attribute.setter}($attribute.type $attribute.var) {
        this.$attribute.var = $attribute.var;
    }

    public ${output.currentRootClass} ${attribute.with}($attribute.type $attribute.var) {
        ${attribute.setter}($attribute.var);
        return ${output.currentRootCast}this;
    }
#else

    private void ${attribute.setter}($attribute.type $attribute.var) {
        this.$attribute.var = $attribute.var;
    }
#end
#if($attribute.isSimplePk())

    $output.dynamicAnnotation("javax.persistence.Transient")
    public boolean ${attribute.has}() {
        return $attribute.var != null#if ($attribute.isString() && !$attribute.isEnum()) && !${attribute.var}.isEmpty()#end;
    }
#end
#end
#end
## --------------- Many to one
#foreach ($relation in $entity.manyToOne.list)
#if ($velocityCount == 1)

    // -----------------------------------------------------------------
    // Many to One support
    // -----------------------------------------------------------------
#end

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: $relation.toString()
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

#foreach ($annotation in $relation.validation.annotations)
    $annotation
#end
#foreach ($annotation in $relation.jpa.annotations)
    $annotation
#end
    public $relation.to.type ${relation.to.getter}() {
        return $relation.to.var;
    }

    /**
     * Set the {@link #$relation.to.var} without adding this $relation.from.type instance on the passed {@link #${relation.to.var}}
#if($relation.hasInverse())
     * If you want to preserve referential integrity we recommend to use
     * instead the corresponding adder method provided by {@link $relation.to.type}
#end
     */
    public void ${relation.to.setter}($relation.to.type $relation.to.var) {
        this.$relation.to.var = $relation.to.var;
#if (!$relation.isIntermediate())        
#foreach ($attributePair in $relation.attributePairs)
#if ($attributePair.fromAttribute.isInCpk() && $attributePair.toAttribute.isInCpk())
         ${identifiableProperty.getter}().${attributePair.fromAttribute.setter}($relation.to.var != null ? ${relation.to.var}.${identifiableProperty.getter}().${attributePair.toAttribute.getter}() : null);
#end
#end
#end
    }

    public ${output.currentRootClass} ${relation.to.with}($relation.to.type $relation.to.var) {
        ${relation.to.setter}($relation.to.var);
        return ${output.currentRootCast}this;
    }
#end
##---------------- One to one
#foreach ($relation in $entity.oneToOne.list)
#if ($velocityCount == 1)

    // -----------------------------------------------------------------
    // One to one
    // -----------------------------------------------------------------
#end

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // #{if}($relation.isInverse())Inverse#{else}Owner#{end} side of one-to-one relation: $relation.toString()
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
## TODO : add @NotNull annotation
#foreach ($annotation in $relation.validation.annotations)
    $annotation
#end
#foreach ($annotation in $relation.jpa.annotations)
    $annotation
#end
    public $relation.to.type ${relation.to.getter}() {
        return $relation.to.var;
    }

#if($relation.isInverse())
## Inverse ONE TO ONE SETTER (analog to a one to many)
    public void ${relation.to.setter}($relation.to.type $relation.to.var) {
        this.$relation.to.var = $relation.to.var;

        if (this.$relation.to.var != null) {
            this.${relation.to.var}.${relation.inverse.to.setter}(${output.currentRootCast}this);
        }
    }
#else
## FORWARD ONE TO ONE SETTER
    public void ${relation.to.setter}($relation.to.type $relation.to.var) {
        this.$relation.to.var = $relation.to.var;
#if (!$relation.isIntermediate())        
#foreach ($attributePair in $relation.attributePairs)
#if ($attributePair.fromAttribute.isInCpk() && $attributePair.toAttribute.isInCpk())
        ${identifiableProperty.getter}().${attributePair.fromAttribute.setter}($relation.to.var != null ? ${relation.to.var}.${identifiableProperty.getter}().${attributePair.toAttribute.getter}() : null);
#end
#end
#end
    }
#end

    public ${output.currentRootClass} ${relation.to.with}($relation.to.type $relation.to.var) {
        ${relation.to.setter}($relation.to.var);
        return ${output.currentRootCast}this;
    }

#end

## --------------- One to many
#foreach ($relation in $entity.oneToMany.list)
#if ($velocityCount == 1)

    // -----------------------------------------------------------------
    // One to Many support
    // -----------------------------------------------------------------
#end

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // one to many: $relation.fromEntity.model.var ==> $relation.to.vars
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

#foreach ($annotation in $relation.jpa.annotations)
    $annotation
#end
    public ${entity.collectionType.type}<$relation.to.type> ${relation.to.getters}() {
        return $relation.to.vars;
    }

    /**
     * Set the {@link $relation.to.type} list.
     * It is recommended to use the helper method {@link ${pound}${relation.to.adder}($relation.to.type)} / {@link ${pound}${relation.to.remover}($relation.to.type)}
     * if you want to preserve referential integrity at the object level.
     *
     * @param $relation.to.vars the list to set
     */
    public void ${relation.to.setters}(${entity.collectionType.type}<$relation.to.type> $relation.to.vars) {
        this.$relation.to.vars = $relation.to.vars;
    }
    

    /**
     * Helper method to add the passed {@link $relation.to.type} to the {@link #$relation.to.vars} list
     * and set this $relation.from.var on the passed $relation.to.var to preserve referential
     * integrity at the object level.
     *
     * @param $relation.to.var the to add
     * @return true if the $relation.to.var could be added to the $relation.to.vars list, false otherwise
     */
    public boolean ${relation.to.adder}($relation.to.type $relation.to.var) {
        if (${relation.to.getters}().add($relation.to.var)) {
            ${relation.to.var}.${relation.from.setter}(${output.currentRootCast}this);
            return true;
        }
        return false;
    }

    /**
     * Helper method to remove the passed {@link $relation.to.type} from the {@link #$relation.to.vars} list and unset
     * this $relation.from.var from the passed $relation.to.var to preserve referential integrity at the object level.
     *
     * @param $relation.to.var the instance to remove
     * @return true if the $relation.to.var could be removed from the $relation.to.vars list, false otherwise
     */
    public boolean ${relation.to.remover}($relation.to.type $relation.to.var) {
        if (${relation.to.getters}().remove($relation.to.var)) {
            ${relation.to.var}.${relation.from.setter}(null);
            return true;
        }
        return false;
    }

#end
#foreach ($relation in $entity.manyToMany.list)
#if ($velocityCount == 1)

    // -----------------------------------------------------------------
    // Many to Many
    // -----------------------------------------------------------------
#end

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    // many-to-many: $relation.fromEntity.model.var ==> $relation.to.vars
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

    /**
     * Returns the {@link #$relation.to.vars} list.
     */
#foreach ($annotation in $relation.jpa.annotations)
    $annotation
#end
    public ${entity.collectionType.type}<$relation.to.type> ${relation.to.getters}() {
        return $relation.to.vars;
    }

    /**
     * Set the {@link #$relation.to.vars} list.
#if ($relation.hasInverse())
     * <p>
     * It is recommended to use the helper method {@link ${pound}${relation.to.adder}($relation.to.type)} / {@link ${pound}${relation.to.remover}($relation.to.type)}
     * if you want to preserve referential integrity at the object level.
#end
     *
     * @param $relation.to.vars the list of $relation.to.type
     */
    public void ${relation.to.setters}(${entity.collectionType.type}<$relation.to.type> $relation.to.vars) {
        this.$relation.to.vars = $relation.to.vars;
    }

#if ($relation.hasInverse())
    /**
     * Helper method to add the passed {@link $relation.to.type} to the {@link #$relation.to.vars} list
     * and add this $relation.from.var to the passed ${relation.to.var}'s {@link #$relation.from.vars} list
     * to preserve referential integrity at the object level.
     */
    public boolean ${relation.to.adder}($relation.to.type $relation.to.var) {
        if (${relation.to.getters}().add(${relation.to.var})) {
            return ${relation.to.var}.${relation.from.getters}().add(${output.currentRootCast}this);
        }
        return false;
    }

    /**
     * Helper method to remove the passed {@link $relation.to.type} from the {@link #$relation.to.vars} list
     * and remove this $relation.from.var from the passed ${relation.to.var}'s {@link #$relation.from.vars} list.
     * to preserve referential integrity at the object level.
     */
    public boolean ${relation.to.remover}($relation.to.type $relation.to.var) {
        if (${relation.to.getters}().remove($relation.to.var)) {
            return ${relation.to.var}.${relation.from.getters}().remove(${output.currentRootCast}this);
        }
        return false;
    }

#else
    /**
     * Helper method to add the passed {@link $relation.to.type} to the {@link #$relation.to.vars} list.
     */
    public boolean ${relation.to.adder}($relation.to.type $relation.to.var) {
        return ${relation.to.getters}().add($relation.to.var);
    }

    /**
     * Helper method to remove the passed {@link $relation.to.type} from the {@link #$relation.to.vars} list.
     */
    public boolean ${relation.to.remover}($relation.to.type $relation.to.var) {
        return ${relation.to.getters}().remove($relation.to.var);
    }
#end

    /**
     * Helper method to determine if the passed {@link $relation.to.type} is present in the {@link #$relation.to.vars} list.
     */
    public boolean ${relation.to.contains}($relation.to.type $relation.to.var) {
        return ${relation.to.getters}() != null && ${relation.to.getters}().contains($relation.to.var);
    }
#end

    /**
     * Apply the default values.
     */
    public ${output.currentRootClass} withDefaults() {
#if ($entity.hasParent())
        super.withDefaults();
#end
#foreach ($attribute in $entity.pertinentDefaultValueAttributes.list)
        ${attribute.setter}($attribute.javaDefaultValue);
#end
        return ${output.currentRootCast}this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        $output.currentClass other = ($output.currentClass) o;

        if(other.${identifiableProperty.getter}() == null || ${identifiableProperty.getter}() == null) {
            return false;
        }

        return Objects.equals(${identifiableProperty.getter}(), other.${identifiableProperty.getter}());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(${identifiableProperty.getter}());
    }

    /**
     * Construct a readable string representation for this ${output.currentClass} instance.
     * @see java.lang.Object${pound}toString()
     */
    @Override
    public String toString() {
        return #if ($entity.hasParent())super.toString() + #{end}MoreObjects.toStringHelper(this) //
#foreach ($attribute in $entity.nonCpkAttributes.list)
#if(!$attribute.isInFk() || $attribute.isSimplePk())
            .add("${attribute.var}", #if($attribute.isPassword())"XXXX"#else${attribute.getter}()#end) //
#end
#end
            .toString();
    }
}