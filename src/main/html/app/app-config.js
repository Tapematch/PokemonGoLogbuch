'use strict';

function appConfig($stateProvider, $urlRouterProvider, $locationProvider) {

    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('login', {
            url: '/login',
            views: {
                'content@': {
                    templateUrl: 'login/view/login.html'
                }
            }
        })
        .state('main', {
            url: '/main',
            views: {
                'content@': {
                    templateUrl: 'main/view/main.html'
                }
            }
        });
}

angular.module('PokemonGo').config(
    [
        '$stateProvider',
        '$urlRouterProvider',
        '$locationProvider',
        appConfig
    ]
);