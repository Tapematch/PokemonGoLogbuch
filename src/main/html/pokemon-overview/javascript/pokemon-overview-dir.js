'use strict';

function pokemonOverviewController($scope, dateService){
    var that = this;
    that.close = close;
    that.save = save;
    that.delete = deletePokemon;

    initialize();

    function initialize(){
        if ($scope.isnew){
            $scope.isInEditMode = true;
            $scope.newLocationName = '';
            $scope.newTime = '';
            $scope.newCoordinates = '';
            $scope.newName = '';
            $scope.newWp = '';
            $scope.newNumber = 1;
        } else{
            var data = $scope.data;
            $scope.isInEditMode = data.isInEditMode;
            $scope.newLocationName = data.locationName;
            $scope.newTime = dateService.getDate(data.time, true);
            $scope.newCoordinates = data.coordinates;
            $scope.newName = data.name;
            $scope.newWp = data.wp;
            $scope.newNumber = data.number;
        }
    }

    function close(){
        $scope.mainController.hidePokemon(true);
    }

    function save(){
        $scope.data.time = dateService.getTimestamp($scope.newTime);
        $scope.data.locationName = $scope.newLocationName;
        $scope.data.coordinates = $scope.newCoordinates;
        $scope.data.name = $scope.newName;
        $scope.data.wp = $scope.newWp;
        $scope.data.number = $scope.newNumber;

        $scope.mainController.hidePokemon(false);
    }

    function deletePokemon(){
        $scope.mainController.deletePokemon($scope.data);
    }
}

function pokemonOverviewDirective(){
    return{
        restrict: 'E',
        require: '^main',
        scope: {
            data: '=',
            isnew: '=',
            isInEditMode: '=?'
        },
        controller: ['$scope', 'dateService', pokemonOverviewController],
        controllerAs: 'pkmnOvrvwCtrl',
        templateUrl: 'pokemon-overview/template/pokemon-overview-template.html',
        link:{
            pre: function (scope, elem, attr, controller){
                scope.mainController = controller;
            }
        }
    }
}

angular.module('PokemonGo').directive('pokemonOverview', pokemonOverviewDirective);
