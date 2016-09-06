'use strict';

function httpRequestService($http, $q, principalService){
    return{
        login: login,
        getLogbookEntriesByUserId: getLogbookEntriesByUserId
    };

    function getLogbookEntriesByUserId(id){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        var config = {
            url: 'http://localhost/rest/logbookentry/user/' + id,
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            }
        };

        request(config, deferred);

        return deferred.promise;
    }

    function login(credentials){
        var deferred = $q.defer();

        var config = {
            url: 'http://localhost:8080/rest/login',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: credentials
        };

        request(config, deferred);

        return deferred.promise;
    }

    function request(config, deferred){
        $http(config).then(function(response){
            deferred.resolve(response.data);
        }, function(response){
            deferred.reject(response.data);
        });
    }
}

angular.module('PokemonGo').factory('httpRequestService', ['$http', '$q', 'principalService', httpRequestService]);
