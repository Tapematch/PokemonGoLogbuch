'use strict';

function mainController($scope, httpRequestService, principalService){
    var that = this;

    that.hideError = hideError;
    that.showNewEntry = showNewEntry;

    initialize();

    function initialize(){
        $scope.username = principalService.getIdentity().username;

        loadEntries();
    }

    function loadEntries(){
        $scope.loading = true;

        var userId = principalService.getIdentity().userId;

        httpRequestService.getLogbookEntriesByUserId(userId)
            .then(function(entries){
                $scope.entries = entries;
                $scope.loading = false;
            }, function(error){
                showError(error);
                $scope.loading = false;
            });
    }

    function showError(error){
        $scope.error = error;
        $scope.showError = true;
    }

    function hideError(){
        $scope.error = '';
        $scope.showError = false;
    }

    function showNewEntry(){
        $scope.showNewEntry = true;
    }
}

function mainDirective(){
    return{
        restrict: 'E',
        scope:{
            entries: '=?',
            showNewEntry: '=?',
            showError: '=?',
            error: '=?',
            username: '=?',
            loading: '=?'
        },
        controller: ['$scope', 'httpRequestService', 'principalService', mainController],
        controllerAs: 'mainCtrl',
        templateUrl: 'main/template/main-template.html'
    }
}

angular.module('PokemonGo').directive('main', mainDirective);
