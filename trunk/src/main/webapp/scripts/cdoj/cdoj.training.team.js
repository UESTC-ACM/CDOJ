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
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

function getRating(rating, color, isFirst, ratingVary) {
    var html = $('<td style="text-align: right;"></td>');
    var ratingSpan = $('<span class="rating-' + color + ' label-rating">' + rating + '</span>');
    var varySpan = $('<span class="label label-diff"></span>');
    if (isFirst != undefined) {
        varySpan.addClass('label-info');
        varySpan.append('INIT');
    } else {
        if (ratingVary >= 0) {
            varySpan.addClass('label-success');
            varySpan.append('+' + ratingVary);
        } else {
            varySpan.addClass('label-important');
            varySpan.append(ratingVary);
        }
    }
    html.append(ratingSpan);
    html.append(varySpan);
    return html;
}

function getHtml(value) {
    var html = $('<tr></tr>');
    html.append('<td>' + value.contestId + '</td>');
    html.append('<td>' + value.contestName + '</td>');
    html.append(getRating(value.rating, value.ratingColor, value.isFirst, value.ratingVary));
    html.append('<td>' + value.volatility +
        '(' + (value.volatilityVary >= 0 ? '+' : '') + value.volatilityVary +
        ')</td>');
    return html;
}

$(document).ready(function () {
    var currentTeam = '1';
    $.post('/training/teamSummary/' + currentTeam, function(data){
        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }

        var tbody = $('#teamHistoryList');
        // remove old list
        tbody.find('tr').remove();
        // put list
        var teamSummary = data.teamSummary;
        tbody.prepend(getHtml({
            contestId: '0',
            contestName: 'Initialization',
            rating: 1200,
            volatility: 550,
            volatilityVary: 0,
            isFirst: true
        }));
        $.each(teamSummary, function(index, value){
            tbody.prepend(getHtml(value));
        });

        drawRatingChart(teamSummary);
    });
});