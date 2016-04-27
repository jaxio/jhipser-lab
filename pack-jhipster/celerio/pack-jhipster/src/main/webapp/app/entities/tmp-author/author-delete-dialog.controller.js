(function() {
    'use strict';

    angular
        .module('jhipavrilApp')
        .controller('AuthorDeleteController',AuthorDeleteController);

    AuthorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Author'];

    function AuthorDeleteController($uibModalInstance, entity, Author) {
        var vm = this;
        vm.author = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Author.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
