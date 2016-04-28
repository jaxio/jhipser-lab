$output.webapp("app/entities/${entity.model.var}/${entity.model.var}.service.js")##
(function() {
    'use strict';
    angular
        .module('mainApp')
        .factory('${entity.model.type}', ${entity.model.type});

    ${entity.model.type}.${dollar}inject = ['${dollar}resource'];

    function ${entity.model.type} (${dollar}resource) {
        var resourceUrl =  'api/${entity.model.vars}/:id';

        return ${dollar}resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
