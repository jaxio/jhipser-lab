$output.webapp("app/entities/${entity.model.var}/${entity.model.var}.state.js")##
(function() {
    'use strict';

    angular
        .module('mainApp')
        .config(stateConfig);

    stateConfig.${dollar}inject = ['${dollar}stateProvider'];

    function stateConfig(${dollar}stateProvider) {
        ${dollar}stateProvider
            .state('${entity.model.var}', {
                parent: 'entity',
                url: '/${entity.model.var}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'mainApp.${entity.model.var}.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/${entity.model.var}/${entity.model.vars}.html',
                        controller: '${entity.model.type}Controller',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['${dollar}translate', '${dollar}translatePartialLoader', function (${dollar}translate, ${dollar}translatePartialLoader) {
                        ${dollar}translatePartialLoader.addPart('${entity.model.var}');
                        ${dollar}translatePartialLoader.addPart('global');
                        return ${dollar}translate.refresh();
                    }]
                }
            })
            .state('${entity.model.var}-detail', {
                parent: 'entity',
                url: '/${entity.model.var}/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'mainApp.${entity.model.var}.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/${entity.model.var}/${entity.model.var}-detail.html',
                        controller: '${entity.model.type}DetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['${dollar}translate', '${dollar}translatePartialLoader', function (${dollar}translate, ${dollar}translatePartialLoader) {
                        ${dollar}translatePartialLoader.addPart('${entity.model.var}');
                        return ${dollar}translate.refresh();
                    }],
                    entity: ['${dollar}stateParams', '${entity.model.type}', function(${dollar}stateParams, ${entity.model.type}) {
                        return ${entity.model.type}.get({id : ${dollar}stateParams.id});
                    }]
                }
            })
            .state('${entity.model.var}.new', {
                parent: '${entity.model.var}',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['${dollar}stateParams', '${dollar}state', '${dollar}uibModal', function(${dollar}stateParams, ${dollar}state, ${dollar}uibModal) {
                    ${dollar}uibModal.open({
                        templateUrl: 'app/entities/${entity.model.var}/${entity.model.var}-dialog.html',
                        controller: '${entity.model.type}DialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
#foreach($attribute in $entity.simpleAttributes.list)
                                    ${attribute.var}: null,
#end
#if($entity.hasSimplePk())
                                    ${entity.primaryKey.attribute.var}: null
#end
## TODO: composite PK
                                };
                            }
                        }
                    }).result.then(function() {
                        ${dollar}state.go('${entity.model.var}', null, { reload: true });
                    }, function() {
                        ${dollar}state.go('${entity.model.var}');
                    });
                }]
            })
            .state('${entity.model.var}.edit', {
                parent: '${entity.model.var}',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['${dollar}stateParams', '${dollar}state', '${dollar}uibModal', function(${dollar}stateParams, ${dollar}state, ${dollar}uibModal) {
                    ${dollar}uibModal.open({
                        templateUrl: 'app/entities/${entity.model.var}/${entity.model.var}-dialog.html',
                        controller: '${entity.model.type}DialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['${entity.model.type}', function(${entity.model.type}) {
                                return ${entity.model.type}.get({id : ${dollar}stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        ${dollar}state.go('${entity.model.var}', null, { reload: true });
                    }, function() {
                        ${dollar}state.go('^');
                    });
                }]
            })
            .state('${entity.model.var}.delete', {
                parent: '${entity.model.var}',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['${dollar}stateParams', '${dollar}state', '${dollar}uibModal', function(${dollar}stateParams, ${dollar}state, ${dollar}uibModal) {
                    ${dollar}uibModal.open({
                        templateUrl: 'app/entities/${entity.model.var}/${entity.model.var}-delete-dialog.html',
                        controller: '${entity.model.type}DeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['${entity.model.type}', function(${entity.model.type}) {
                                return ${entity.model.type}.get({id : ${dollar}stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        ${dollar}state.go('${entity.model.var}', null, { reload: true });
                    }, function() {
                        ${dollar}state.go('^');
                    });
                }]
            });
    }

})();
