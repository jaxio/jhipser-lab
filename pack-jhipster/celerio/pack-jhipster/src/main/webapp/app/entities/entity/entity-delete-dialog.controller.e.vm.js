$output.webapp("app/entities/${entity.model.var}/${entity.model.var}-delete-dialog.controller.js")##
(function() {
    'use strict';

    angular
        .module('mainApp')
        .controller('${entity.deleteController.type}',${entity.deleteController.type});

    ${entity.deleteController.type}.${dollar}inject = ['${dollar}uibModalInstance', 'entity', '${entity.model.type}'];

    function ${entity.deleteController.type}(${dollar}uibModalInstance, entity, ${entity.model.type}) {
        var vm = this;
        vm.${entity.model.var} = entity;
        vm.clear = function() {
            ${dollar}uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ${entity.model.type}.delete({id: id},
                function () {
                    ${dollar}uibModalInstance.close(true);
                });
        };
    }
})();
