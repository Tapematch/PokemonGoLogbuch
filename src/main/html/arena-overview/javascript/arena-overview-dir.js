'use strict';

function arenaOverviewController($scope, dateService){
    var that = this;
    that.close = close;
    that.save = save;
    that.delete = deleteArena;

    initialize();

    function initialize(){
        if ($scope.isnew){
            $scope.isInEditMode = true;
            $scope.newLocationName = '';
            $scope.newTime = '';
            $scope.newCoordinates = '';
            $scope.newTeam = '';
            $scope.newWin = false;
            $scope.newLevel = '';
            $scope.newNumber = 1;
        } else{
            var data = $scope.data;
            $scope.isInEditMode = data.isInEditMode;
            $scope.newLocationName = data.locationName;
            $scope.newTime = dateService.getDate(data.time, true);
            $scope.newCoordinates = data.coordinates;
            $scope.newTeam = data.team;
            $scope.newLevel = data.level;
            $scope.newWin = data.win;
            $scope.newNumber = data.number;
        }
    }

    function close(){
        $scope.mainController.hideArena(true);
    }

    function save(){
        $scope.data.time = dateService.getTimestamp($scope.newTime);
        $scope.data.locationName = $scope.newLocationName;
        $scope.data.coordinates = $scope.newCoordinates;
        $scope.data.level = $scope.newLevel;
        $scope.data.team = $scope.newTeam;
        $scope.data.win = $scope.newWin;
        $scope.data.number = $scope.newNumber;

        $scope.mainController.hideArena(false);
    }

    function deleteArena(){
        $scope.mainController.deleteArena($scope.data);
    }
}

function arenaOverviewDirective(){
    return{
        restrict: 'E',
        require: '^main',
        scope: {
            data: '=',
            isnew: '=',
            isInEditMode: '=?',
            newLocationName: '=?',
            newNumber: '=?',
            newCoordinates: '=?'
        },
        controller: ['$scope', 'dateService', arenaOverviewController],
        controllerAs: 'arnOvrvwCtrl',
        templateUrl: 'arena-overview/template/arena-overview-template.html',
        link:{
            pre: function (scope, elem, attr, controller){
                scope.mainController = controller;
            }
        }
    }
}

angular.module('PokemonGo').directive('arenaOverview', arenaOverviewDirective);