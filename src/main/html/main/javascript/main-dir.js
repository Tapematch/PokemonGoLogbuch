'use strict';

function mainController($scope, $state, httpRequestService, principalService, waypointService, arenaService, pokemonService){
    var that = this;
    that.showWaypoint = showWaypoint;
    that.hideWaypoint = hideWaypoint;
    that.deleteWaypoint = deleteWaypoint;
    that.showArena = showArena;
    that.hideArena = hideArena;
    that.deleteArena = deleteArena;
    that.showPokemon = showPokemon;
    that.hidePokemon = hidePokemon;
    that.deletePokemon = deletePokemon;
    that.showError = showError;
    that.hideError = hideError;
    that.showInfo = showInfo;
    that.hideInfo = hideInfo;
    that.showNewEntry = showNewEntry;
    that.addEntry = addEntry;
    that.hideNewEntry = hideNewEntry;
    that.logout = logout;
    that.loadEntries = loadEntries;

    initialize();

    function initialize(){
        $scope.username = principalService.getIdentity().username;
        $scope.team = principalService.getIdentity().team;
        $scope.startdate = principalService.getIdentity().startdate;
        $scope.activeWaypoint = [];
        $scope.isNewWaypoint = false;

        loadEntries();
    }

    function loadEntries(){
        $scope.showNewEntry = false;
        $scope.entries = [];

        var userId = principalService.getIdentity().id;

        httpRequestService.getLogbookEntriesByUserId(userId)
            .then(function(entries){
                $scope.entries = entries;
            }, function(error){
                showError(error);
            });
    }

    function addEntry(entry){
        $scope.entries.push(entry)
    }

    function hideNewEntry(){
        $scope.showNewEntry = false;
    }

    function showPokemon(pokemon, isNew, list){
        $scope.activePokemon = [];
        $scope.isNewPokemon = isNew;
        $scope.activePokemon.push(pokemon);
        $scope.pokemonsList = list;

        hideWaypoint(false);
        hideArena(false);
    }

    function hidePokemon(cancel){
        if (cancel && $scope.isNewPokemon){
            $scope.pokemonsList.splice($scope.pokemonsList.length - 1, 1);
        }

        $scope.activePokemon = [];
        $scope.isNewPokemon = false;
        $scope.pokemonsList = [];
    }

    function deletePokemon(pokemon){
        var index = pokemonService.indexOf(pokemon, $scope.pokemonsList);
        $scope.pokemonsList.splice(index, 1);

        $scope.activePokemon = [];
        $scope.isNewPokemon = false;
        $scope.pokemonsList = [];
    }

    function showArena(arena, isNew, list){
        $scope.activeArena = [];
        $scope.isNewArena = isNew;
        $scope.activeArena.push(arena);
        $scope.arenasList = list;

        hideWaypoint(false);
        hidePokemon(false);
    }

    function hideArena(cancel){
        if (cancel && $scope.isNewArena){
            $scope.arenasList.splice($scope.arenasList.length - 1, 1);
        }

        $scope.activeArena = [];
        $scope.isNewArena = false;
        $scope.arenasList = [];
    }

    function deleteArena(arena){
        var index = arenaService.indexOf(arena, $scope.arenasList);
        $scope.arenasList.splice(index, 1);

        $scope.activeArena = [];
        $scope.isNewArena = false;
        $scope.arenasList = [];
    }

    function showWaypoint(waypoint, isNew, list){
        $scope.activeWaypoint = [];
        $scope.isNewWaypoint = isNew;
        $scope.activeWaypoint.push(waypoint);
        $scope.waypointsList = list;

        hidePokemon(false);
        hideArena(false);
    }

    function hideWaypoint(cancel){
        if (cancel && $scope.isNewWaypoint){
            $scope.waypointsList.splice($scope.waypointsList.length - 1, 1);
        }

        $scope.activeWaypoint = [];
        $scope.isNewWaypoint = false;
        $scope.waypointsList = [];
    }

    function deleteWaypoint(point){
        var index = waypointService.indexOf(point, $scope.waypointsList);
        $scope.waypointsList.splice(index, 1);

        $scope.activeWaypoint = [];
        $scope.isNewWaypoint = false;
        $scope.waypointsList = [];
    }

    function showInfo(info){
        $scope.info = info;
        $scope.showInfo = true;
    }

    function hideInfo(){
        $scope.info = '';
        $scope.showInfo = false;
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

    function logout(){
        httpRequestService.logout(principalService.getIdentity().id);
        principalService.setIdentity(null);
        $state.go('login');
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
            loading: '=?',
            activeWaypoint: '=?',
            isNewWaypoint: '=?'
        },
        controller: ['$scope', '$state', 'httpRequestService', 'principalService', 'waypointService', 'arenaService', 'pokemonService', mainController],
        controllerAs: 'mainCtrl',
        templateUrl: 'main/template/main-template.html'
    }
}

angular.module('PokemonGo').directive('main', mainDirective);
