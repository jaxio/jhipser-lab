$output.webapp("app/entities/${entity.model.var}/${entity.model.var}-detail.controller.js")##
(function() {
    'use strict';

    angular
        .module('${configuration.applicationName}')
        .controller('${entity.detailController.type}', ${entity.detailController.type});

    ${entity.detailController.type}.${dollar}inject = ['${dollar}scope', '${dollar}rootScope', '${dollar}stateParams', 'entity'#foreach($relatedEntity in $entity.meAndRelatedEntities.list), '${relatedEntity.model.type}'#{end}];

    function ${entity.detailController.type}(${dollar}scope, ${dollar}rootScope, ${dollar}stateParams, entity#foreach($relatedEntity in $entity.meAndRelatedEntities.list), ${relatedEntity.model.type}#{end}) {
        var vm = this;
        vm.${entity.model.var} = entity;

        var unsubscribe = ${dollar}rootScope.${dollar}on('${configuration.applicationName}:${entity.model.var}Update', function(event, result) {
            vm.${entity.model.var} = result;
        });
        ${dollar}scope.${dollar}on('${dollar}destroy', unsubscribe);

    }
})();
## TODO
## blob file
