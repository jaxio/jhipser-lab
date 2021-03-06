$output.webapp("app/entities/${entity.model.var}/${entity.model.var}-dialog.controller.js")##
(function() {
    'use strict';

    angular
        .module('mainApp')
        .controller('${entity.dialogController.type}', ${entity.dialogController.type});

    ${entity.dialogController.type}.${dollar}inject = ['${dollar}timeout', '${dollar}scope', '${dollar}stateParams', '${dollar}uibModalInstance', 'entity'#foreach($relatedEntity in $entity.meAndRelatedEntities.list), '${relatedEntity.model.type}'#{end}];

    function ${entity.dialogController.type} (${dollar}timeout, ${dollar}scope, ${dollar}stateParams, ${dollar}uibModalInstance, entity#foreach($relatedEntity in $entity.meAndRelatedEntities.list), ${relatedEntity.model.type}#{end}) {
        var vm = this;
        vm.${entity.model.var} = entity;
#foreach ($manyToOne in $entity.manyToOne.list)
        vm.$manyToOne.to.vars = ${manyToOne.toEntity.model.type}.query();
#end
        ${dollar}timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            ${dollar}scope.${dollar}emit('mainApp:${entity.model.var}Update', result);
            ${dollar}uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.${entity.model.var}.id !== null) {
                ${entity.model.type}.update(vm.${entity.model.var}, onSaveSuccess, onSaveError);
            } else {
                ${entity.model.type}.save(vm.${entity.model.var}, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            ${dollar}uibModalInstance.dismiss('cancel');
        };

#if($entity.hasDateAttribute())
        vm.datePickerOpenStatus = {};
#foreach($attribute in $entity.simpleAttributes.list)
#if($attribute.isLocalDate())## TODO: ZonedDateTime
        vm.datePickerOpenStatus.$attribute.var = false;
#end
#end
#end

## TODO: files etc..

#if($entity.hasDateAttribute())
            vm.openCalendar = function(date) {
                vm.datePickerOpenStatus[date] = true;
            };
#end
    }
})();
