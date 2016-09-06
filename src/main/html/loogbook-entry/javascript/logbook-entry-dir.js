'use strict';

function logbookEntryController($scope){
    var that = this;
    that.showWayPoints = showWayPoints;
    that.showArenas = showArenas;
    that.showPokemons = showPokemons;

    initialize();

    function initialize(){
        if ($scope.data){
            var data = $scope.data;

            $scope.date = new Date(data.date);
            $scope.startTime = new Date(data.startTime);
            $scope.endTime = new Date(data.endTime);
            $scope.startLevel = data.startLevel;
            $scope.startEp = data.startEp;
            $scope.endEp = data.endEp;

            if (data.levelUp){
                $scope.levelUp = 'Ja';
            } else{
                $scope.levelUp = 'Nein';
            }

            setWayPoints(data.waypoints);
            setArenas(data.arenas);
            setPokemons(data.pokemons);

            $scope.isInEditMode = false;
        }
    }

    function setWayPoints(waypoints){
        var preview = '';
        var list = waypoints;

        for (var i = 0; i < 2; i++){
            preview += waypoints[i].locationName;
        }

        $scope.waypointsPreview = '';
        $scope.waypointsList = list;
    }

    function setArenas(arenas){
        var preview = '';
        var list = arenas;

        for (var i = 0; i < 2; i++){
            preview += arenas[i].locationName;
        }

        $scope.arenasPreview = preview;
        $scope.arenasList = list;
    }

    function setPokemons(pokemons){
        var preview = '';
        var list = pokemons;

        for (var i = 0; i < 2; i++){
            preview += pokemons[i].name;
        }

        $scope.pokemonsPreview = preview;
        $scope.pokemonsList = list;
    }

    function showWayPoints(){
        $scope.showWayPoints = true;
    }

    function showArenas(){
        $scope.showArenas = true;
    }

    function showPokemons(){
        $scope.showPokemons = true;
    }
}

function logbookEntryDirective(){
    return{
        restrict: 'E',
        scope:{
            data: '=',
            new: '=',
            showWayPoints: '=?',
            showArenas: '=?',
            showPokemons: '=?',
            waypointsPreview: '=?',
            waypointsList: '=?',
            arenasPreview: '=?',
            arenasList: '=?',
            pokemonsPreview: '=?',
            pokemonsList: '=?',
            isInEditMode: '=?'
        },
        controller: logbookEntryController,
        controllerAs: 'lgbEntryCtrl',
        template: 'loogbook-entry/template/loogbook-entry-template.html'
    }
}

angular.module('PokemonGo').directive('logbookEntry', logbookEntryDirective);
