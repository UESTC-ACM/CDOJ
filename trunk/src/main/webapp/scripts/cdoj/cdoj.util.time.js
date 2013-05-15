/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

/**
 * Blind formatTimeStyle function to nodes.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */

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

    $.fn.formatTimeStyle = function() {

        $.each(this, function() {
            var self = $(this);
            var date = new Date(parseInt(self[0].innerHTML));
            if (self.attr('type') != 'milliseconds')
                date = new Date(self[0].innerHTML);
            self.empty();
            self.append(date.Format('yyyy-MM-dd hh:mm:ss'));
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
    }
}

function getSeconds(data, id) {
    var time = getTimeInfo(data, id);
    return ((parseInt(time.days) * 24 + parseInt(time.hours)) * 60 + parseInt(time.minutes)) * 60 + parseInt(time.seconds);
}

function getTime(data, id) {
    var time = getTimeInfo(data, id);
    var timeString = time.days + ' ' + time.hours + ':' + time.minutes + ':' + time.seconds;
    var result = new Date(timeString).getTime();
    console.log(timeString, result);
    return result;
}