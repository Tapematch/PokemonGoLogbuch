'use strict';

function appRun($rootScope, authorizationService) {

    $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams, fromState, fromParams) {

        if (toState.name !== 'login' && toState.name !== 'logout'){
            authorizationService.authorize(event, toState, toStateParams);
        }
    });
}

angular.module('PokemonGo').run(
    [
        '$rootScope',
        'authorizationService',
        appRun
    ]
);