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
 * All function used in contest page.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

var timeLeft;
var timeLeftDiv;
var totTime;
var timeLeftProgress;

function refreshCurrentTime() {
    timeLeftDiv.empty();
    timeLeftDiv.append(timeLeft);
    timeLeftDiv.formatTimeStyle();
    timeLeftProgress.css('width', (1 - timeLeft / totTime) * 100 + '%');
    timeLeft--;
}

$(document).ready(function () {

    //Init current time left
    timeLeftDiv = $('#timeLeft');
    if (timeLeftDiv) {
        timeLeftProgress = $('#timeLeftProgress');
        timeLeft = timeLeftDiv.attr('value');
        totTime = parseInt(timeLeftDiv.attr('totTime'));
        setInterval(refreshCurrentTime, 1000);
    }

    //Blind resize function
    $('.problemHref').each(function(){
        var target = $(this).attr('target');
        $(this).click(function () {
            $('#TabMenu').find('a[href="#tab-contest-problem-' + target + '"]').tab('show');
            return false;
        });

        $('#TabMenu').find('a[href="#tab-contest-problem-' + target + '"]').on('shown', function () {
            var problem = $('#tab-contest-problem-' + target);
            // make sample input and output have the same height
            var height = 0;
            $.each(problem.find('.sample'), function () {
                height = Math.max(height, $(this).height());
            });
            $.each(problem.find('.sample'), function () {
                $(this).css('height', height);
            });
        });
    });

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
