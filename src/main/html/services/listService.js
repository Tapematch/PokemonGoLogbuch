'use strict';

function listService(dateService){
    return{
        resetDates: resetDates
    };

    function resetDates(list, baseDate){
        for (var i = 0; i < list.length; i++){
            list[i].time = dateService.getTimestamp(dateService.buildDate(baseDate, dateService.getDate(list[i].time, true)));
        }

        return list;
    }
}

angular.module('PokemonGo').factory('listService', ['dateService', listService]);
