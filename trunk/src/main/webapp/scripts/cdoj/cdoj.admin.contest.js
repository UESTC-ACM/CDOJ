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
 * current contest list
 */
var contestList;

var visibleClass = 'icon-eye-open';
var unVisibleClass = 'icon-eye-close';

function getQueryString(field, id, value) {
    var queryString = '/admin/contest/operator?method=edit';
    queryString += '&id=' + id;
    queryString += '&field=' + field;
    queryString += '&value=' + value;
    return queryString;
}

function editVisible(id, value) {
    var queryString = getQueryString('isVisible', id, value);
    $.post(queryString, function (data) {
        if (data.result == "ok") {
            var icon = $('#visibleState[contestId="' + id + '"]');
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

function blindVisibleEdit() {
    $('#visibleState').live('click', function () {
        var id = $(this).attr('contestId');
        var visible = ($(this).attr('isVisible') == 'true') ? true : false;
        editVisible(id, !visible);
    });
}

function getTitle(contestId, contestTitle, isVisible) {
    var html = $('<td></td>');
    var titleInfo = '';
    titleInfo += '<i id="visibleState" isVisible="' + isVisible + '"contestId="' + contestId + '" class="';
    if (isVisible == true)
        titleInfo += visibleClass;
    else
        titleInfo += unVisibleClass;
    titleInfo += ' pull-left tags"/>';

    titleInfo += '<a class="pull-left" href="/admin/contest/editor/' + contestId + '" title="Edit problem">'
        + contestTitle + '</a></span>';

    html.append(titleInfo);

    return html;
}

function getLength(length, status) {
    var html = $('<td class="cdoj-time" type="milliseconds" timeStyle="length">' + length + '</td>');

    if (status == 'Running')
        html.addClass('contest-state-running');
    else if (status == 'Ended')
        html.addClass('contest-state-ended');
    else
        html.addClass('contest-state-pending');
    return html;
}

/**
 * refresh the contest list
 * @param condition
 */
function refreshContestList(condition) {
    $.post('/admin/contest/search', condition, function (data) {
        console.log(data);
        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }

        //pagination
        $('#pageInfo').empty();
        $('#pageInfo').append(data.pageInfo);
        $('#pageInfo').find('a').click(function (e) {
            currentCondition.currentPage = $(this).attr("href");
            refreshContestList(currentCondition);
            return false;
        });

        contestList = data.contestList;
        var tbody = $('#contestList');
        // remove old contest list
        tbody.find('tr').remove();
        // put user list
        $.each(contestList, function (index, value) {
            var html = $('<tr></tr>');

            html.append('<td>' + value.contestId + '</td>');
            html.append(getTitle(value.contestId, value.title, value.isVisible));
            html.append('<td>' + value.typeName + '</td>');
            html.append('<td class="cdoj-time">' + value.time + '</td>');
            html.append(getLength(value.length, value.status));
            tbody.append(html);
        });

        blindVisibleEdit();

        // format time style
        $('.cdoj-time').formatTimeStyle();

    });
}

function changeOrder(field) {
    if (currentCondition["contestCondition.orderFields"] == field)
        currentCondition["contestCondition.orderAsc"] = (currentCondition["contestCondition.orderAsc"] == "true" ? "false" : "true");
    else {
        currentCondition["contestCondition.orderFields"] = field;
        currentCondition["contestCondition.orderAsc"] = "false";
    }
    refreshContestList(currentCondition);
}

$(document).ready(function () {
    $('#contestCondition_type').prepend('<option value="-1">All</option>');
    $('#contestCondition_type').attr("value", -1);

    currentCondition = {
        "currentPage": null,
        "contestCondition.startId": undefined,
        "contestCondition.endId": undefined,
        "contestCondition.title": undefined,
        "contestCondition.keyword": undefined,
        "contestCondition.isVisible": undefined,
        "contestCondition.type": undefined,
        "contestCondition.orderFields": undefined,
        "contestCondition.orderAsc": undefined
    };

    $('input#search').setButton({
        callback: function () {
            currentCondition = $('#contestCondition').getFormData();
            currentCondition.currentPage = 1;
            refreshContestList(currentCondition);
            $('#TabMenu a:first').tab('show');
        }
    });

    $('input#reset').setButton({
        callback: function () {
            $('#contestCondition').resetFormData();
        }
    });

    $.each($('.orderButton'), function(){
        var field = $(this).attr('field');
        $(this).setButton({
            callback: function(){
                changeOrder(field);
            }
        });
    });

    refreshContestList(currentCondition);
});