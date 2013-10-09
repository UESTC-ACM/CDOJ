/**
 * Blind formatTimeStyle function to nodes.
 */

/**
 * Date() function for ie 6 7 8.
 * @param dateString like YYYY-MM-DDThh:mm:ss
 */
var ieDate = function(dateString) {
    var exp;
    exp = /^s*(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})s*$/;
    var date = new Date(NaN);
    var parts = exp.exec(dateString);
    if (parts) {
        date = new Date(parts[1], parseInt(parts[2], 10) - 1, parts[3], parts[4], parts[5], parts[6]);
        if (Sys.ie678)
            date = new Date(date.getTime() - date.getTimezoneOffset() * 60 * 1000);
    }
    return date;
};

(function($) {
    'use strict';

    /**
     * Format time style.
     *
     */
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };

    /**
     * Format time style
     *
     * @returns {*}
     */
    $.fn.formatTimeStyle = function() {

        $.each(this, function() {
            var self = $(this);
            var date = new Date(parseInt(self[0].innerHTML, 10));
            var time = parseInt(self[0].innerHTML, 10);
            if (self.attr('type') != 'milliseconds') {
                date = new Date(self[0].innerHTML);
                if (Sys.ie678 || Sys.firefox)
                    date = ieDate(self[0].innerHTML);
            }
            if (!Sys.firefox && self.attr('isutc') != 'true') {
                date.setTime(date.getTime() + date.getTimezoneOffset() * 60 * 1000);
            }
            self.empty();
            if (self.attr('timeStyle') == 'length') {
                var seconds = time % 60;
                time = parseInt(time / 60, 10);
                var minutes = time % 60;
                time = parseInt(time / 60, 10);
                var hours = time % 60;
                time = parseInt(time / 24, 10);
                var days = time;
                var result = '';
                if (days > 0)
                    result = days + ' days ';
                if (minutes < 10)
                    minutes = '0' + minutes;
                if (seconds < 10)
                    seconds = '0' + seconds;
                result = result + hours + ':' + minutes + ':' + seconds;
                self.append(result);
            }
            else
                self.append(date.Format('yyyy-MM-dd hh:mm:ss'));
        });

        return this;
    };

    /**
     * Set time selector with <div class="controls time-selector" value="1368453116000"></div>
     * Convert value into standard time style and put it into each <input> tags.
     *
     * if type is timePassed then convert it into dd hh:mm:ss style.
     *
     * @returns {*}
     */
    $.fn.setTimeSelector = function() {

        $.each(this, function() {
            var self = $(this);
            var days, hours, minutes;
            if (self.attr('type') != 'timePassed') {
                var date = new Date(parseInt(self.attr('value'), 10));
                days = date.Format('yyyy-MM-dd');
                hours = date.Format('hh');
                minutes = date.Format('mm');
            } else {
                var time = parseInt(self.attr('value'), 10);
                time = parseInt(time / 60, 10);
                minutes = time % 60;
                time = parseInt(time / 60, 10);
                hours = time % 24;
                time = parseInt(time / 24, 10);
                days = time;
            }
            self.find('.time_days').attr('value', days);
            self.find('.time_hours').attr('value', hours);
            self.find('.time_minutes').attr('value', minutes);
            //self.find('.time_seconds').attr('value', seconds);
        });
        return this;
    };

}(jQuery));

function getTimeInfo(data, id) {
    return {
        days: data[id + '.days'],
        hours: data[id + '.hours'],
        minutes: data[id + '.minutes'],
        seconds: data[id + '.seconds']
    };
}

function getSeconds(data, id) {
    var time = getTimeInfo(data, id);
    return ((parseInt(time.days, 10) * 24 + parseInt(time.hours, 10)) * 60 + parseInt(time.minutes, 10)) * 60 + parseInt(time.seconds, 10);
}

function getTime(data, id) {
    var time = getTimeInfo(data, id);
    var timeString = time.days + ' ' + time.hours + ':' + time.minutes + ':' + time.seconds;
    var result = new Date(timeString).getTime();
    if (Sys.ie678 || Sys.firefox || Sys.safari) {
        timeString = time.days + 'T' + time.hours + ':' + time.minutes + ':' + time.seconds;
        result = ieDate(timeString).getTime();
    }
    return result;
}