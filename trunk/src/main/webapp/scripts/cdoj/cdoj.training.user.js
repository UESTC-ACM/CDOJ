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

var trainingUserId;

$(document).ready(function () {
    trainingUserId = $('#name').attr('value');
    console.log(trainingUserId);
    $.post('/training/user/history/' + trainingUserId, function(data) {
        console.log(data);
        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }
        var tbody = $('#teamHistoryList');
        tbody.find('tr').remove();
        var teamHistory = data.teamHistory;
        $.each(teamHistory, function(index, value) {
            var html = $('<tr></tr>');
            html.append($('<td>' + value.contestId + '</td>'));
            if (value.contestId > 0)
                html.append($('<td><a href="/training/contest/show/' + value.contestId + '">' + value.contestName + '</a></td>'));
            else
                html.append($('<td>' + value.contestName + '</td>'));
            html.append(getRating(value.rating, value.ratingVary));
            html.append(getVolatility(value.volatility, value.volatilityVary));
            tbody.prepend(html);
        });
        drawRatingChart(teamHistory);
    });
});