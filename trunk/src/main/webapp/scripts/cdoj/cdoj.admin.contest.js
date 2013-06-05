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
 * All function used in contest admin list page.
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

var visibleClass = 'icon-eye-open';
var unVisibleClass = 'icon-eye-close';

function getQueryString(field, id, value) {
    var queryString = '/admin/problem/operator?method=edit';
    queryString += '&id=' + id;
    queryString += '&field=' + field;
    queryString += '&value=' + value;
    return queryString;
}

function editVisible(id, value) {
    var queryString = getQueryString('isVisible', id, value);
    $.post(queryString, function (data) {
        if (data.result == "ok") {
            var icon = $('#visibleState[problemId="' + id + '"]');
            if (value == false) {
                icon.removeClass(visibleClass);
                icon.addClass(unVisibleClass);
            }
            else {
                icon.removeClass(unVisibleClass);
                icon.addClass(visibleClass);
            }
            icon.live('click', function () {
                editVisible(id, !value)
            });
        }
    });
}

function getTitle(problemId, title, source, isSpj, isVisible, tags) {
    var html = $('<td></td>');
    var titleInfo = '';
    titleInfo += '<i id="visibleState" isVisible="' + isVisible + '"problemId="' + problemId + '" class="';
    if (isVisible == true)
        titleInfo += visibleClass;
    else
        titleInfo += unVisibleClass;
    titleInfo += ' pull-left tags"/>';

    if (isSpj == true)
        titleInfo += '<span class="label label-important tags pull-right">SPJ</span>';

    titleInfo += '<a class="pull-left" href="/admin/problem/editor/' + problemId + '" title="Edit problem">'
        + title + '</a></span>';

    titleInfo += getTags(tags);

    html.append(titleInfo);

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
        html += '<i class="difficulty-level icon-star pull-left" style="margin: 0px;" value="' + i + '"></i>';
    for (var i = difficulty + 1; i <= 5; i++)
        html += '<i class="difficulty-level icon-star-empty pull-left" style="margin: 0px;" value="' + i + '"></i>';
    return html;
}

function blindVisibleEdit() {
    $('#visibleState').live('click', function () {
        var id = $(this).attr('problemId');
        var visible = ($(this).attr('isVisible') == 'true') ? true : false;
        editVisible(id, !visible);
    });
}

function blindDifficultSpan() {
    $('i.difficulty-level').live('click', function () {
        var target = $(this);
        var problemId = target.parent().attr('problemId');
        var value = target.attr('value');
        var queryString = getQueryString('difficulty', problemId, value);
        $.post(queryString, function (data) {
            if (data.result == "ok") {
                var parentNode = target.parent();
                parentNode.empty();
                parentNode.append(getDifficulty(value));
                blindDifficultSpan();
            }
        });
    });
}

function refreshProblemList(condition) {
    $.post('/admin/problem/search', condition, function (data) {

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
            var html = $('<tr></tr>');

            if (value.title == '') {
                value.title = 'Empty problem, please complete this problem first!';
                html.addClass('alert alert-error');
            }

            html.append('<td>' + value.problemId + '</td>');
            html.append(getTitle(value.problemId, value.title, value.source, value.isSpj, value.isVisible, value.tags));
            html.append('<td class="difficult-span" problemId="' + value.problemId + '">' + getDifficulty(value.difficulty) + '</td>');
            html.append('<td><a href="/admin/problem/data/' + value.problemId + '" title="Edit data"><i class="icon-cog"></i></a></td>');
            tbody.append(html);
        });

        blindVisibleEdit();
        blindDifficultSpan();
    });
}

$(document).ready(function () {
    currentCondition = {
        "currentPage": null,
        "problemCondition.startId": undefined,
        "problemCondition.endId": undefined,
        "problemCondition.title": undefined,
        "problemCondition.source": undefined,
        "problemCondition.isVisible": undefined,
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