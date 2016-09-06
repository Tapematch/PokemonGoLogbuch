'use strict';

function principalService($localStorage){

    initialize();

    return{
        setIdentity: setIdentity,
        getIdentity: getIdentity,
        isAuthorized: isAuthorized
    };

    function initialize(){
        $localStorage.identity = null;
    }

    function setIdentity(identitiy){
        $localStorage.identity = identitiy;
    }

    function getIdentity(){
        return $localStorage.identity;
    }

    function isAuthorized(){
        return $localStorage.identity != null;
    }
}

angular.module('PokemonGo').factory('principalService', ['$localStorage', principalService]);