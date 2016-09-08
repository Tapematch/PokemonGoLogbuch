'use strict';

function pokemonService(){
    return {
        indexOf: indexOf
    };

    function indexOf(arena, list){
        for (var i = 0; i < list.length; i++){
            if (list[i].number === arena.number && list[i].locationName === arena.locationName
                && list[i].coordinates === arena.coordinates && list[i].wp === arena.wp
                && list[i].name === arena.name && list[i].time === arena.time){
                return i;
            }
        }

        return -1;
    }
}

angular.module('PokemonGo').factory('pokemonService', pokemonService);

