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
 * Javascript for user admin page.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

/**
 * current search condition
 */
var currentCondition;

/**
 * current training contest list
 */
var trainingContestList;

/**
 * refresh the contest list
 * @param condition
 */
function refreshTrainingContestList(condition) {
    $.post('/training/admin/contest/search', condition, function (data) {
        console.log(data);
        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }

        var pagination = $('#pageInfo');
        //pagination
        pagination.empty();
        pagination.append(data.pageInfo);
        pagination.find('a').click(function (e) {
            if ($(this).attr('href') == null)
                return false;
            currentCondition.currentPage = $(this).attr("href");
            refreshTrainingContestList(currentCondition);
            return false;
        });

        trainingContestList = data.trainingContestList;
        var tbody = $('#trainingContestList');
        // remove old user list
        tbody.find('tr').remove();
        // put user list
        $.each(trainingContestList, function (index, value) {
            var html = $('<tr></tr>');
            html.append($('<td>' + value.trainingContestId + '</td>'));
            html.append($('<td><a href="/training/admin/contest/editor/' + value.trainingContestId + '">' + value.title + '</a></td>'));
            if (value.isPersonal)
                html.append($('<td>Personal</td>'));
            else
                html.append($('<td>Team</td>'));
            tbody.append(html);
        });

    });
}

function changeOrder(field) {
    if (currentCondition["trainingContestCondition.orderFields"] == field)
        currentCondition["trainingContestCondition.orderAsc"] = (currentCondition["trainingContestCondition.orderAsc"] == "true" ? "false" : "true");
    else {
        currentCondition["trainingContestCondition.orderFields"] = field;
        currentCondition["trainingContestCondition.orderAsc"] = "false";
    }
    refreshTrainingContestList(currentCondition);
}

$(document).ready(function () {

    currentCondition = {
        "currentPage": null,
        "trainingContestCondition.startId": undefined,
        "trainingContestCondition.endId": undefined,
        "trainingContestCondition.title": undefined,
        "trainingContestCondition.isPersonal": undefined,
        "trainingContestCondition.orderFields": "id",
        "trainingContestCondition.orderAsc": false
    };

    $.each($('.orderButton'), function(){
        var field = $(this).attr('field');
        $(this).setButton({
            callback: function(){
                changeOrder(field);
            }
        });
    });

    refreshTrainingContestList(currentCondition);
});