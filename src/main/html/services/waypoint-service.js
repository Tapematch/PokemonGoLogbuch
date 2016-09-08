'use strict';

function waypointService(){
    return {
        indexOf: indexOf
    };

    function indexOf(waypoint, list){
        for (var i = 0; i < list.length; i++){
            if (list[i].number === waypoint.number && list[i].locationName === waypoint.locationName
                && list[i].coordinates === waypoint.coordinates && list[i].time === waypoint.time){
                return i;
            }
        }

        return -1;
    }
}

angular.module('PokemonGo').factory('waypointService', waypointService);
