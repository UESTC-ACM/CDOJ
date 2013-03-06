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
 * All function used in problem list page.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */


/**
 * current search condition
 */
var currentCondition;

/**
 * current problem list
 */
var problemList;

function getTitle(problemId, title, source, isSpj, isVisible) {
    var html = '';

    if (isSpj == true)
        html += '<span class="label label-important tags pull-left">SPJ</span>';

    html += '<a class="pull-left" href="/problem/show/' + problemId + '" title="'+source+'">'
        + title + '</a></span>';
    return html;
}

function getTags(tags) {
    var html = '';
    $.each(tags, function (index, value) {
        html += '<span class="label label-info pull-right tags">' + value + '</span>';
    });
    return html;
}

function getDifficulty(difficulty) {
    difficulty = Math.max(1, Math.min(difficulty, 5));
    var html = '';
    for (var i = 1; i <= difficulty; i++)
        html += '<i class="difficulty-level icon-star" value="' + i + '"></i>';
    for (var i = difficulty + 1; i <= 5; i++)
        html += '<i class="difficulty-level icon-star-empty" value="' + i + '"></i>';
    return html;
}

function refreshProblemList(condition) {
    $.post('/problem/search', condition, function (data) {

        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }

        //pagination
        $('#pageInfo').empty();
        $('#pageInfo').append(data.pageInfo);
        $('#pageInfo').find('a').click(function (e) {
            currentCondition.currentPage = $(this).attr("href");
            refreshProblemList(currentCondition);
            return false;
        });

        problemList = data.problemList;
        var tbody = $('#problemList');
        // remove old user list
        tbody.find('tr').remove();
        // put user list
        $.each(problemList, function (index, value) {
            var html = '<tr>' +
                '<td>' + value.problemId + '</td>' +
                '<td>' + getTitle(value.problemId, value.title, value.source, value.isSpj, value.isVisible) + getTags(value.tags) + '</td>' +
                '<td>' + getDifficulty(value.difficulty) + '</td>' +
                '<td><i class="icon-user"/>' + value.solved + '</td>'
                '</tr>';
            tbody.append(html);
        });

    });
}

$(document).ready(function () {
    currentCondition = {
        "currentPage": null,
        "problemCondition.startId": undefined,
        "problemCondition.endId": undefined,
        "problemCondition.title": undefined,
        "problemCondition.source": undefined,
        "problemCondition.isSpj": undefined,
        "problemCondition.startDifficulty": undefined,
        "problemCondition.endDifficulty": undefined,
        "problemCondition.keyword": undefined
    }

    $('input#search').setButton({
        callback: function () {
            currentCondition = $('#problemCondition').getFormData();
            currentCondition.currentPage = 1;
            refreshProblemList(currentCondition);
            $('#TabMenu a:first').tab('show');
        }
    });

    $('input#reset').setButton({
        callback: function () {
            $('#problemCondition').resetFormData();
        }
    });

    refreshProblemList(currentCondition);
});
