$output.webapp("app/entities/${entity.model.var}/${entity.model.var}.controller.js")##
(function() {
    'use strict';

    angular
        .module('mainApp')
        .controller('$entity.controller.type', ${entity.controller.type});

    ${entity.controller.type}.${dollar}inject = ['${dollar}scope', '${dollar}state', '$entity.model.type'];

    function $entity.controller.type (${dollar}scope, ${dollar}state, ${entity.model.type}) {
        var vm = this;
        vm.$entity.model.vars = [];
        vm.loadAll = function() {
            ${entity.model.type}.query(function(result) {
                vm.$entity.model.vars = result;
            });
        };

        vm.loadAll();
    }
})();
## TODOS:
## - pagination
## - elastic search
## - blob
