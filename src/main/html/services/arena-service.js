'use strict';

function arenaService(){
    return {
        indexOf: indexOf
    };

    function indexOf(arena, list){
        for (var i = 0; i < list.length; i++){
            if (list[i].number === arena.number && list[i].locationName === arena.locationName
                && list[i].coordinates === arena.coordinates && list[i].team === arena.team
                && list[i].win === arena.win && list[i].time === arena.time){
                return i;
            }
        }

        return -1;
    }
}

angular.module('PokemonGo').factory('arenaService', arenaService);

