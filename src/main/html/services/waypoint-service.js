'use strict';

function waypointService(){
    return {
        indexOf: indexOf,
        reorder: reorder
    };

    function indexOf(waypoint, list){
        for (var i = 0; i < list.length; i++){
            if (list[i].number === waypoint.number && list[i].locationName === waypoint.locationName
                && list[i].coordinates === list[i].coordinates){
                return i;
            }
        }

        return -1;
    }

    function reorder(list){
        var orderedList = [];

        for (var i = 0; i < list.length; i++){
            var indexOfPoint = -1;
            var j = i;

            while (indexOfPoint === -1 || j < list.length){
                j = j + 1;
                indexOfPoint = indexOfNumber(list, j);

                if (indexOfPoint > -1){
                    list[indexOfPoint].number = i + 1;
                    orderedList.push()
                }
            }
        }
    }

    function indexOfNumber(list, number){
        for (var i = 0; i < list.length; i++){
            if (list[i].number === number){
                return i;
            }
        }

        return -1;
    }
}

angular.module('PokemonGo').factory('waypointService', waypointService);
