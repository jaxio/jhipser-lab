$output.webapp("app/entities/${entity.model.var}/${entity.model.var}.service.js")##
(function() {
    'use strict';
    angular
        .module('mainApp')
        .factory('${entity.model.type}', ${entity.model.type});

    ${entity.model.type}.${dollar}inject = ['${dollar}resource'#if($entity.hasDateAttribute()), 'DateUtils'#end];

    function ${entity.model.type} (${dollar}resource#if($entity.hasDateAttribute()), DateUtils#end) {
        var resourceUrl =  'api/${entity.model.vars}/:id';

        return ${dollar}resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
#foreach($attribute in $entity.simpleAttributes.list)
#if($attribute.isLocalDate())
                    data.$attribute.var = DateUtils.convertLocalDateFromServer(data.$attribute.var);
#elseif($attribute.isDate())
                    data.$attribute.var = DateUtils.convertDateTimeFromServer(data.$attribute.var);
#end
#end
                    return data;
                }
            },
#if($entity.ajs.hasLocalDateAttribute())
            'update': {
                method: 'PUT',
                    transformRequest: function (data) {
#foreach($attribute in $entity.simpleAttributes.list)
#if($attribute.isLocalDate())
                    data.$attribute.var = DateUtils.convertLocalDateToServer(data.$attribute.var);
#end
#end
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                    transformRequest: function (data) {
#foreach($attribute in $entity.simpleAttributes.list)
#if($attribute.isLocalDate())
                    data.$attribute.var = DateUtils.convertLocalDateToServer(data.$attribute.var);
#end
#end
                    return angular.toJson(data);
                }
            }
#else
            'update': { method:'PUT' }
#end
        });
    }
})();
