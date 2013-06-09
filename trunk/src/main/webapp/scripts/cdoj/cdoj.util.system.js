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
 * Javascript for system information.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

var Sys = {};

!function ($) {

    $(function () {
        var ua = navigator.userAgent.toLowerCase();
        if (ua.indexOf('msie') > 0)
            Sys.ie = ua.match(/msie ([\d.]+)/)[1];
        else if (ua.indexOf('firefox') > 0)
            Sys.firefox = ua.match(/firefox\/([\d.]+)/)[1];
        else if (ua.indexOf('chrome') > 0)
            Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1];
        else if (ua.indexOf('opera') > 0)
            Sys.opera = ua.match(/opera.([\d.]+)/)[1];
        else if (ua.indexOf('version') > 0)
            Sys.safari = ua.match(/version\/([\d.]+)/)[1];

        Sys.windows = (ua.indexOf("windows",0) != -1)?1:0;
        Sys.mac = (ua.indexOf("mac",0) != -1)?1:0;
        Sys.linux = (ua.indexOf("Linux",0) != -1)?1:0;
        Sys.unix = (ua.indexOf("x11",0) != -1)?1:0;

        Sys.ie678 = parseFloat(Sys.ie) < 9 ? 1 : 0;
    })
}(window.jQuery);

