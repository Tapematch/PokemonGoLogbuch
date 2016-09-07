'use strict';

function waypointOverviewController($scope){
    var that = this;
    that.close = close;
    that.save = save;
    that.delete = deleteWaypoint;

    initialize();

    function initialize(){
        if ($scope.isnew){
            $scope.isInEditMode = true;
            $scope.newLocationName = '';
            $scope.newNumber = 1;
            $scope.newCoordinates = '';
        } else{
            var data = $scope.data;
            $scope.isInEditMode = data.isInEditMode;
            $scope.newLocationName = data.locationName;
            $scope.newNumber = data.number;
            $scope.newCoordinates = data.coordinates;
        }
    }

    function close(){
        $scope.mainController.hideWaypoint(true);
    }

    function save(){
        $scope.data.number = $scope.newNumber;
        $scope.data.locationName = $scope.newLocationName;
        $scope.data.coordinates = $scope.newCoordinates;

        $scope.mainController.hideWaypoint(false);
    }

    function deleteWaypoint(){
        $scope.mainController.deleteWaypoint($scope.data);
    }
}

function waypointOverviewDirective(){
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
        controller: ['$scope', waypointOverviewController],
        controllerAs: 'wpntOvrvwCtrl',
        templateUrl: 'waypoint-overview/template/waypoint-overview-template.html',
        link:{
            pre: function (scope, elem, attr, controller){
                scope.mainController = controller;
            }
        }
    }
}

angular.module('PokemonGo').directive('waypointOverview', waypointOverviewDirective);