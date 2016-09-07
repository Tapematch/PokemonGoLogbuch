function dateService(){
    return{
        getTimestamp: getTimestamp,
        getDate: getDate,
        buildDate: buildDate
    };

    function buildDate(baseDate, time){
        return baseDate + ' ' + time + ':00';
    }

    function getDate(timestamp, timeOnly){
        var date = new Date(timestamp);

        if (!date || isNaN(date)){
            return timestamp;
        }

        if (timeOnly){
            var hour = date.getHours();
            if (hour.toString().length === 1){
                hour = "0" + hour;
            }

            var minute = date.getMinutes();
            if (minute.toString().length === 1){
                minute = "0" + minute;
            }

            return hour + ':' + minute;
        } else {
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            if (month.toString().length === 1){
                month = "0" + month;
            }

            var day = date.getDate();
            if (day.toString().length === 1){
                day = "0" + day;
            }

            return day + '.' + month + '.' + year;
        }
    }

    function getTimestamp(date){
        var splittedDate = date.split(' ');
        var year = null;
        var month = null;
        var day = null;

        if (splittedDate[0]){
            var splittedYear = splittedDate[0].split('.');
            year = splittedYear[2];
            month = splittedYear[1];
            day = splittedYear[0];
        }

        if (splittedDate[1]){
            var splittedTime = splittedDate[1].split(':');
            var hour = splittedTime[0];
            var minute = splittedTime[1];
            var second = splittedTime[2];

            return new Date(year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second).getTime();
        } else{
            return new Date(year + '-' + month + '-' + day).getTime();
        }
    }
}

angular.module('PokemonGo').factory('dateService', dateService);
