/**
 * Javascript for system information.
 */

var Sys = {};

$(document).ready(function () {

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
    });
});

