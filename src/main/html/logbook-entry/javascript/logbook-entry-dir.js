'use strict';

function logbookEntryController($scope, dateService){
    var that = this;
    that.showWayPoints = showWayPoints;
    that.showArenas = showArenas;
    that.showPokemons = showPokemons;
    that.showNewWaypoint = showNewWaypoint;
    that.showWaypoint = showWaypoint;
    that.showNewArena = showNewArena;
    that.showArena = showArena;
    that.showNewPokemon = showNewPokemon;
    that.showPokemon = showPokemon;
    that.save = save;
    that.cancel = cancel;
    that.edit = edit;

    initialize();

    function initialize(){
        if ($scope.item){
            $scope.isInEditMode = false;
            var data = $scope.item;

            $scope.date = dateService.getDate(data.date, false);
            $scope.startTime = dateService.getDate(data.startTime, true);
            $scope.endTime = dateService.getDate(data.endTime, true);
            $scope.startLevel = data.startLevel;
            $scope.startEp = data.startEp;
            $scope.endEp = data.endEp;
            $scope.levelUp = data.levelUp;

            setWayPoints(data.waypoints);
            setArenas(data.arenas);
            setPokemons(data.pokemons);

            $scope.newDate = dateService.getDate(data.date, false);
            $scope.newStartTime = dateService.getDate(data.startTime, true);
            $scope.newEndTime = dateService.getDate(data.endTime, true);
            $scope.newStartLevel = data.startLevel;
            $scope.newStartEp = data.startEp;
            $scope.newEndEp = data.endEp;
            $scope.newLevelUp = data.levelUp;
            $scope.newPokemonsList = data.pokemons;
            $scope.newWaypointsList = data.waypoints;
            $scope.newArenasList = data.arenas;
        } else {
            $scope.isInEditMode = true;
            $scope.newDate = '';
            $scope.newStartTime = '';
            $scope.newEndTime = '';
            $scope.newStartLevel = '';
            $scope.newStartEp = '';
            $scope.newEndEp = '';
            $scope.newLevelUp = false;
            $scope.newPokemonsList = [];
            $scope.newWaypointsList = [];
            $scope.newArenasList = [];
        }
    }

    function save(){
        if($scope.item){
            $scope.date = dateService.getDate(dateService.getTimestamp($scope.newDate), false);
            $scope.startTime = dateService.getDate(dateService.getTimestamp($scope.newStartTime), true);
            $scope.endTime = dateService.getDate(dateService.getTimestamp($scope.newEndTime), true);
            $scope.startLevel = $scope.newStartLevel;
            $scope.startEp = $scope.newStartEp;
            $scope.endEp = $scope.newEndEp;
            $scope.levelUp = $scope.newLevelUp;
            $scope.pokemonsList = $scope.newPokemonsList;
            $scope.waypointsList = $scope.newWaypointsList;
            $scope.arenasList = $scope.newArenasList;

            setWayPoints($scope.waypointsList);

            $scope.isInEditMode = false;
        } else{
            var entry = {
                date: dateService.getTimestamp($scope.newDate),
                startTime: dateService.getTimestamp($scope.newStartTime),
                endTime: dateService.getTimestamp($scope.newEndTime),
                startLevel: $scope.newStartLevel,
                startEp: $scope.newStartEp,
                endEp: $scope.newEndEp,
                levelUp: $scope.newLevelUp,
                waypoints: $scope.newWaypointsList,
                arenas: $scope.newArenasList,
                pokemons: $scope.newPokemonsList
            };

            $scope.mainController.addEntry(entry);

            $scope.mainController.hideNewEntry();
        }
    }

    function edit(){
        $scope.isInEditMode = true;
    }

    function cancel(){
        if($scope.item){
            $scope.isInEditMode = false;
        } else{
            $scope.mainController.hideNewEntry();
        }

        resetnew();
    }

    function resetnew(){
        if ($scope.item){
            $scope.newDate = dateService.getDate(dateService.getTimestamp($scope.date), false);
            $scope.newStartTime = dateService.getDate(dateService.getTimestamp($scope.startTime), true);
            $scope.newEndTime = dateService.getDate(dateService.getTimestamp($scope.endTime), true);
            $scope.newStartLevel = $scope.startLevel;
            $scope.newStartEp = $scope.startEp;
            $scope.newEndEp = $scope.endEp;
            $scope.newLevelUp = $scope.levelUp;
        } else{
            $scope.newDate = '';
            $scope.newStartTime = '';
            $scope.newEndTime = '';
            $scope.newStartLevel = '';
            $scope.newStartEp = '';
            $scope.newEndEp = '';
            $scope.newLevelUp = false;
            $scope.newPokemonsList = [];
            $scope.newWaypointsList = [];
            $scope.newArenasList = [];
        }
    }

    function test(){
        var pokemons = [];
        pokemons.push({name: 'Glumanda'}, {name: 'Glutexo'},{name: 'Glurak'}, {name: 'Amonitas'}, {name: 'Bisaflor'}, {name: 'Kapador'});
        setPokemons(pokemons);

        var arenas = [];
        arenas.push({locationName: 'Deine Ecke'}, {locationName: 'Meine Ecke'}, {locationName: 'Unsere Ecke'});
        setArenas(arenas);

        var waypoints = [];
        waypoints.push({locationName: 'Deine Ecke'}, {locationName: 'Meine Ecke'}, {locationName: 'Unsere Ecke'});
        setWayPoints(waypoints);
    }

    function showWaypoint(waypoint){
        waypoint.isInEditMode = $scope.isInEditMode;

        if ($scope.isInEditMode){
            $scope.mainController.showWaypoint(waypoint, false, $scope.newWaypointsList);
        } else{
            $scope.mainController.showWaypoint(waypoint, false, $scope.waypointsList);
        }
    }

    function showNewPokemon(){
        var pokemon = {};
        $scope.newPokemonsList.push(pokemon);

        $scope.mainController.showPokemon(pokemon, true, $scope.newPokemonsList);
    }

    function showPokemon(pokemon){
        pokemon.isInEditMode = $scope.isInEditMode;

        if ($scope.isInEditMode){
            $scope.mainController.showPokemon(pokemon, false, $scope.newPokemonsList);
        } else{
            $scope.mainController.showPokemon(pokemon, false, $scope.pokemonsList);
        }
    }

    function showNewArena(){
        var arena = {};
        $scope.newArenasList.push(arena);

        $scope.mainController.showArena(arena, true, $scope.newArenasList);
    }

    function showArena(arena){
        arena.isInEditMode = $scope.isInEditMode;

        if ($scope.isInEditMode){
            $scope.mainController.showArena(arena, false, $scope.newArenasList);
        } else{
            $scope.mainController.showArena(arena, false, $scope.arenasList);
        }
    }

    function showNewWaypoint(){
        var waypoint = {};
        $scope.newWaypointsList.push(waypoint);

        $scope.mainController.showWaypoint(waypoint, true, $scope.newWaypointsList);
    }

    function setWayPoints(waypoints){
        var preview = '';
        var list = waypoints;

        for (var i = 0; i < 2; i++){
            if (i > waypoints.length - 1){
                break;
            }

            preview += waypoints[i].locationName + ', ';
        }

        if (waypoints.length > 3){
            preview += '...  '
        }

        $scope.waypointsPreview = preview.substring(0, preview.length - 2);
        $scope.waypointsList = list;
    }

    function setArenas(arenas){
        var preview = '';
        var list = arenas;

        for (var i = 0; i < 2; i++){
            if (i > arenas.length - 1){
                break;
            }

            preview += arenas[i].locationName;
        }

        $scope.arenasPreview = preview;
        $scope.arenasList = list;
    }

    function setPokemons(pokemons){
        var preview = '';
        var list = pokemons;

        for (var i = 0; i < 2; i++){
            if (i > pokemons.length - 1){
                break;
            }

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
        restrict: 'A',
        require: '^main',
        scope:{
            data: '=',
            item: '=logbookEntry',
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
        controller: ['$scope', 'dateService', logbookEntryController],
        controllerAs: 'lgbEntryCtrl',
        templateUrl: 'logbook-entry/template/logbook-entry-template.html',
        link:{
            pre: function(scope, elem, attr, controller){
                scope.mainController = controller;
            }
        }
    }
}

angular.module('PokemonGo').directive('logbookEntry', logbookEntryDirective);
