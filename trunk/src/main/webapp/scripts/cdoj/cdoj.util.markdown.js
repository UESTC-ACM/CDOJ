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
 * Blind marked to jQuery node element.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

(function($) {
    'use strict';

    $.fn.markdown = function() {
        console.log(this.innerHTML.toString());
    }

}(jQuery));

function markdown() {
    $.each($('[type="markdown"]'),function() {
        //IE7 and IE8's html() method will delete all line breakers EXCEPT in <textarea>
        var md = $(this).html()
            .replace('<textarea>','')
            .replace('</textarea>','')
            .replace('<TEXTAREA>','')
            .replace('</TEXTAREA>','');

        //IE7 and IE8 not support .trim() method.
        md = js.lang.String.trim(md);

        var markedHTML = $(marked(md));

        $(this).empty().append(markedHTML);

        $.each($(this).find('pre'), function(){
            //unescape all characters in <pre></pre>
            var str = $(this)[0].innerHTML;
            str = '<pre>' + str + '</pre>'
            str = js.lang.String.decodeHtml(str);
            $(this).replaceWith($('<div class="markdown-temp"></div>'));
            var nowDiv = $('.markdown-temp');
            $(nowDiv).append(str);
            $(nowDiv).removeClass('markdown-temp');
        });
    })
}