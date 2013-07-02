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
 * All function used in article admin list page.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

/**
 * current search condition
 */
var currentCondition;

/**
 * current article list
 */
var articleList;

var visibleClass = 'icon-eye-open';
var unVisibleClass = 'icon-eye-close';

function getQueryString(field, id, value) {
    var queryString = '/admin/article/operator?method=edit';
    queryString += '&id=' + id;
    queryString += '&field=' + field;
    queryString += '&value=' + value;
    return queryString;
}

function editVisible(id, value) {
    var queryString = getQueryString('isVisible', id, value);
    $.post(queryString, function (data) {
        console.log(data, queryString);
        if (data.result == "ok") {
            var icon = $('#visibleState[articleId="' + id + '"]');
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

function getTitle(articleId, title, isVisible, clicked, author) {
    var html = $('<td></td>');
    var titleInfo = '';
    titleInfo += '<i id="visibleState" isVisible="' + isVisible + '"articleId="' + articleId + '" class="';
    if (isVisible == true)
        titleInfo += visibleClass;
    else
        titleInfo += unVisibleClass;
    titleInfo += ' pull-left tags"/>';

    titleInfo += '<a class="pull-left" href="/admin/article/editor/' + articleId + '" title="Edit article">'
        + title + '</a></span>';
    titleInfo += '<span class="pull-left muted" style="margin-left: 12px"> By ' + author + '</span>';

    titleInfo += '<span class="pull-right label label-success">' + clicked + '</span>';
    html.append(titleInfo);

    return html;
}

function blindVisibleEdit() {
    $('#visibleState').live('click', function () {
        var id = $(this).attr('articleId');
        var visible = ($(this).attr('isVisible') == 'true') ? true : false;
        editVisible(id, !visible);
    });
}

function refreshArticleList(condition) {
    $.post('/admin/article/search', condition, function (data) {

        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }

        //pagination
        $('#pageInfo').empty();
        $('#pageInfo').append(data.pageInfo);
        $('#pageInfo').find('a').click(function (e) {
            if ($(this).attr('href') == null)
                return false;
            currentCondition.currentPage = $(this).attr("href");
            refreshProblemList(currentCondition);
            return false;
        });

        articleList = data.articleList;
        var tbody = $('#articleList');
        // remove old user list
        tbody.find('tr').remove();
        // put user list
        $.each(articleList, function (index, value) {
            var html = $('<tr></tr>');

            html.append('<td>' + value.articleId + '</td>');
            html.append(getTitle(value.articleId, value.title, value.isVisible, value.clicked, value.author));
            html.append('<td class="cdoj-time">' + value.time + '</td>');
            html.append('<td style="text-align: left;"><img id="usersAvatar" email="' + value.ownerEmail + '"/>' + value.ownerName + '</td>');
            tbody.append(html);
        });

        blindVisibleEdit();

        // get userList avatars
        $('img#usersAvatar').setAvatar({
            size: 37,
            image: 'http://www.acm.uestc.edu.cn/images/akari_small.jpg'
        });

        // format time style
        $('.cdoj-time').formatTimeStyle();
    });
}

function changeOrder(field) {
    if (currentCondition["articleCondition.orderFields"] == field)
        currentCondition["articleCondition.orderAsc"] = (currentCondition["articleCondition.orderAsc"] == "true" ? "false" : "true");
    else {
        currentCondition["articleCondition.orderFields"] = field;
        currentCondition["articleCondition.orderAsc"] = "false";
    }
    refreshArticleList(currentCondition);
}

$(document).ready(function () {
    currentCondition = {
        "currentPage": null,
        "articleCondition.startId": undefined,
        "articleCondition.endId": undefined,
        "articleCondition.title": undefined,
        "articleCondition.isVisible": undefined,
        "articleCondition.keyword": undefined,
        "articleCondition.orderFields": undefined,
        "articleCondition.orderAsc": undefined
    }

    $('input#search').setButton({
        callback: function () {
            currentCondition = $('#articleCondition').getFormData();
            currentCondition.currentPage = 1;
            refreshArticleList(currentCondition);
            $('#TabMenu a:first').tab('show');
        }
    });

    $('input#reset').setButton({
        callback: function () {
            $('#articleCondition').resetFormData();
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

    refreshArticleList(currentCondition);
});