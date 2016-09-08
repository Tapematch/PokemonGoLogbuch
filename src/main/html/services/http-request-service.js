'use strict';

function httpRequestService($http, $q, principalService){
    return{
        login: login,
        logout: logout,
        getLogbookEntriesByUserId: getLogbookEntriesByUserId,
        addLogbookEntry: addLogbookEntry,
        updateLogbookEntry: updateLogbookEntry,
        deleteLogbookEntry: deleteLogbookEntry
    };

    function deleteLogbookEntry(id){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        var config = {
            url: 'http://localhost:8080/rest/logbookentry/delete/' + id,
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            }
        };

        request(config, deferred);

        return deferred.promise;
    }

    function updateLogbookEntry(entry){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        var config = {
            url: 'http://localhost:8080/rest/logbookentry/update',
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: entry
        };

        request(config, deferred);

        return deferred.promise;
    }

    function addLogbookEntry(entry){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        var config = {
            url: 'http://localhost:8080/rest/logbookentry/add',
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: entry
        };

        request(config, deferred);

        return deferred.promise;
    }

    function getLogbookEntriesByUserId(id){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        var config = {
            url: 'http://localhost:8080/rest/logbookentry/user/' + id,
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            }
        };

        request(config, deferred);

        return deferred.promise;
    }

    function logout(id){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        var config = {
            url: 'http://localhost:8080/rest/logout/' + id,
            method: 'POST',
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
            if (response.status === 401){
                deferred.reject("Ungültige SessionId!");
            } else{
                deferred.reject(response.data);
            }
        });
    }
}

angular.module('PokemonGo').factory('httpRequestService', ['$http', '$q', 'principalService', httpRequestService]);
