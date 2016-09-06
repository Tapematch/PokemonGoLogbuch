'use strict';

function loginController($scope, $rootScope, $state, httpRequestService, authorizationService){
    var that = this;

    that.showError = showError;
    that.hideError = hideError;

    initialize();

    function initialize(){
        $scope.username = '';
        $scope.password = '';
    }

    function showError(error){
        $scope.error = error;
        $scope.showError = true;
    }

    function hideError(){
        $scope.showError = false;
    }

    function login(){
        $scope.loading = true;

        var credentials = {
            username: $scope.username,
            password: $scope.password
        };

        httpRequestService.login(credentials)
            .then(function(data) {
                $scope.loading = false;
                authorizationService.setIdentity(data);

                if ($rootScope.returnToState){
                    $state.go($rootScope.returnToState, $rootScope.returnToParams);
                }
                else{
                    $state.go('main');
                }
            }, function() {
                $scope.loading = false;
                showError('Could not login.');
            });
    }
}

function loginDirective(){
    return{
        restrict: 'E',
        scope:{
            username: '=?',
            password: '=?',
            error: '=?',
            showError: '=?',
            loading: '=?'
        },
        controller: [
            '$scope',
            '$rootScope',
            '$state',
            'httpRequestService',
            'authorizationService',
            loginController
        ],
        controllerAs: 'lgnCtrl',
        templateUrl: 'login/template/login-template.html'
    }
}

angular.module('PokemonGo').directive('login', loginDirective);
