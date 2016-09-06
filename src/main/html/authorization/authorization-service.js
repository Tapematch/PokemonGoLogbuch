'use strict';

function authorizationService($state, $rootScope, principalService){
    return{
        authorize: authorize
    };

    function authorize(event, toState, toStateParams){
        if (!principalService.isAuthorized()){
            event.preventDefault();

            $rootScope.returnToState = toState;
            $rootScope.returnToParams = toStateParams;
            $state.go('login');
        }
    }
}

angular.module('PokemonGo').factory('authorizationService', ['$state', '$rootScope', 'principalService', authorizationService]);