/*
 * cdoj, UESTC ACMICPC Online Judge
 *
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 * mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

/**
 * All function used in article page.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

var currentArticle;

$(document).ready(function () {

    currentArticle = $('#article_title').attr('value');

    markdown();

    $.each($('.sample'), function () {
        var md = $(this).html()
            .replace('<textarea>', '')
            .replace('</textarea>', '')
            .replace('<TEXTAREA>', '')
            .replace('</TEXTAREA>', '')
            .replace('\r', '<br/>');
        $(this).empty().append(md);
    });

    // make code pretty
    prettify();

    MathJax.Hub.Config({
        tex2jax: {inlineMath: [
            ['$', '$'],
            ['\\[', '\\]']
        ]}
    });
    MathJax.Hub.Queue(['Typeset', MathJax.Hub]);
});
