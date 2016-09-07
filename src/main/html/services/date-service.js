function dateService(){
    return{
        getTimestamp: getTimestamp,
        getDate: getDate
    };

    function getDate(timestamp, fullTime){
        var date = new Date(timestamp);
        var dateString = '';

        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();

        dateString += day + '.' + month + '.' + year;

        if (fullTime){
            var hour = date.getHours();
            var minute = date.getMinutes();
            var second = date.getSeconds();

            dateString += ' ' + hour + ':' + minute + ':' + second;
        }

        return dateString;
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
