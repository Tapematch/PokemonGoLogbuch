'use strict';

function logbookEntryController($scope, dateService, principalService, httpRequestService, listService){
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
    that.delete = deleteEntry;

    initialize();

    function initialize(){
        if ($scope.item){
            $scope.isInEditMode = false;
            var data = $scope.item;

            $scope.id = data.id;
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
            $scope.id = 0;
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
        $scope.mainController.hideError();
        $scope.mainController.hideInfo();

        var entry = {
            id: $scope.id,
            userId: principalService.getIdentity().id,
            date: dateService.getTimestamp($scope.newDate),
            starttime: dateService.getTimestamp(dateService.buildDate($scope.newDate, $scope.newStartTime)),
            endtime: dateService.getTimestamp(dateService.buildDate($scope.newDate, $scope.newEndTime)),
            startlevel: $scope.newStartLevel,
            levelUp: $scope.newLevelUp,
            startEp: $scope.newStartEp,
            endEp: $scope.newEndEp,
            waypoints: listService.resetDates($scope.newWaypointsList, $scope.newDate),
            gyms: listService.resetDates($scope.newArenasList, $scope.newDate),
            pokemon: listService.resetDates($scope.newPokemonsList, $scope.newDate)
        };

        if ($scope.item){
            httpRequestService.updateLogbookEntry(entry)
                .then(function () {
                    $scope.mainController.showInfo('Eintrag erfolgreich gespeichert.');
                    $scope.mainController.loadEntries();
                }, function (error) {
                    $scope.mainController.showError('Eintrag konnte nicht gespeichert werden: ' + error);
                });
        } else {
            httpRequestService.addLogbookEntry(entry)
                .then(function () {
                    $scope.mainController.showInfo('Eintrag erfolgreich gespeichert.');
                    $scope.mainController.loadEntries();
                }, function (error) {
                    $scope.mainController.showError('Eintrag konnte nicht gespeichert werden: ' + error);
                });
        }
    }

    function edit(){
        $scope.isInEditMode = true;
    }

    function cancel(){
        if ($scope.item){
            $scope.mainController.loadEntries();
        }
        else{
            resetnew();
            $scope.mainController.hideNewEntry();
        }
    }

    function deleteEntry() {
        httpRequestService.deleteLogbookEntry($scope.id)
            .then(function () {
                $scope.mainController.showInfo('Eintrag erfolgreich gelöscht.');
                $scope.mainController.loadEntries();
            }, function (error) {
                $scope.mainController.showError('Eintrag konnte nicht gelöscht werden: ' + error);
            });
    }

    function resetnew() {
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

    function showWaypoint(waypoint){
        waypoint.isInEditMode = $scope.isInEditMode;

        if ($scope.isInEditMode){
            $scope.mainController.showWaypoint(waypoint, false, $scope.newWaypointsList);
        } else{
            $scope.mainController.showWaypoint(waypoint, false, $scope.waypointsList);
        }
    }

    function showNewPokemon(){
        var pokemon = { userId: principalService.getIdentity().id };
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
        var arena = { userId: principalService.getIdentity().id };
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
        var waypoint = { userId: principalService.getIdentity().id };
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
        controller: ['$scope', 'dateService', 'principalService', 'httpRequestService', 'listService', logbookEntryController],
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
