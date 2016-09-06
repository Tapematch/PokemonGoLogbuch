'use strict';

function loginController($scope, $rootScope, $state, httpRequestService, principalService){
    var that = this;

    that.showError = showError;
    that.hideError = hideError;
    that.login = login;

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
                if (data === '' || data === null){
                    $scope.loading = false;
                    showError('Could not login.');
                    return;
                }

                $scope.loading = false;
                principalService.setIdentity(data);

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
            'principalService',
            loginController
        ],
        controllerAs: 'lgnCtrl',
        templateUrl: 'login/template/login-template.html'
    }
}

angular.module('PokemonGo').directive('login', loginDirective);
