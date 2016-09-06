'use strict';

function httpRequestService($http, $q){
    return{
        login: login
    };

    function login(credentials){
        var deferred = $q.defer();

        var config = {
            url: 'localhost:8080/rest/login',
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
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

angular.module('PokemonGo').factory('httpRequestService', httpRequestService);
