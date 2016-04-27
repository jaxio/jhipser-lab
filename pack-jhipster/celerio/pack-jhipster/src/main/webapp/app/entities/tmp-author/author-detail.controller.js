(function() {
    'use strict';

    angular
        .module('jhipavrilApp')
        .controller('AuthorDetailController', AuthorDetailController);

    AuthorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Author', 'Book'];

    function AuthorDetailController($scope, $rootScope, $stateParams, entity, Author, Book) {
        var vm = this;
        vm.author = entity;
        
        var unsubscribe = $rootScope.$on('jhipavrilApp:authorUpdate', function(event, result) {
            vm.author = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
