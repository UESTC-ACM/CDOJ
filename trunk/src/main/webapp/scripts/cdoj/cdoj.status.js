/*
 * cdoj, UESTC ACMICPC Online Judge
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

/**
 * All function used in status list page.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */


/**
 * current search condition
 */
var currentCondition;

/**
 * current status list
 */
var statusList;

function refreshStatusList(condition) {
    $.post('/status/search', condition, function (data) {
        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }

        //pagination
        $('#pageInfo').empty();
        $('#pageInfo').append(data.pageInfo);
        $('#pageInfo').find('a').click(function (e) {
            currentCondition.currentPage = $(this).attr("href");
            refreshStatusList(currentCondition);
            return false;
        });

        statusList = data.statusList;
        var tbody = $('#statusList');
        // remove old user list
        tbody.find('tr').remove();
        // put user list

        $.each(statusList, function (index, value) {
            var html = '<tr>' +
                '<td>' + value.statusId + '</td>' +
                '<td>' + value.userName + '</td>' +
                '<td><a href="/problem/show/' + value.problemId + '">' + value.problemId + '</a></td>' +
                '<td>' + value.returnType + '</td>' +
//                '<td>Restricted Function on test $case</td>' +
                '<td>' + value.length + '</td>'+
                '<td>' + value.timeCost + '</td>' +
                '<td>' + value.memoryCost + '</td>' +
                '<td>' + value.time + '</td>' +
                '</tr>';
            tbody.append(html);
        });

    });
}

$(document).ready(function () {
    currentCondition = {
        "currentPage": null,
        "statusCondition.startId": undefined,
        "statusCondition.endId": undefined,
        "statusCondition.userId": undefined,
        "statusCondition.problemId": undefined,
        "statusCondition.languageId": undefined,
        "statusCondition.contestId": undefined
    }

    $('input#search').setButton({
        callback: function () {
            currentCondition = $('#statusCondition').getFormData();
            currentCondition.currentPage = 1;
            refreshStatusList(currentCondition);
            $('#TabMenu a:first').tab('show');
        }
    });

    $('input#reset').setButton({
        callback: function () {
            $('#statusCondition').resetFormData();
        }
    });

    refreshStatusList(currentCondition);
});
