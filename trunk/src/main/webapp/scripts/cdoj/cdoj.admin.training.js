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
 * current training user list
 */
var trainingUserList;

function getAllowButton(trainingUserId, allow) {
    var html = $('<td></td>');
    var switcher = $('<div class="switch" data-on="primary" data-off="danger" data-on-label="YES" data-off-label="NO"></div>');
    var checkbox = $('<input type="checkbox" trainingUserId="' + trainingUserId + '" allow="' + allow + '"/>');
    if (allow == true)
        checkbox.attr('checked', 'true');
    switcher.append(checkbox);
    html.append(switcher);
    return html;
}

function getQueryString(field, id, value) {
    var queryString = '/training/admin/user/operator?method=edit';
    queryString += '&id=' + id;
    queryString += '&field=' + field;
    queryString += '&value=' + value;
    return queryString;
}

/**
 * refresh the user list
 * @param condition
 */
function refreshTrainingUserList(condition) {
    $.post('/training/admin/user/search', condition, function (data) {
        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }

        var userListContent = $('#tab-training-user');
        var pagination = userListContent.find('#pageInfo');
        //pagination
        pagination.empty();
        pagination.append(data.pageInfo);
        pagination.find('a').click(function (e) {
            if ($(this).attr('href') == null)
                return false;
            currentCondition.currentPage = $(this).attr("href");
            refreshTrainingUserList(currentCondition);
            return false;
        });

        trainingUserList = data.trainingUserList;
        var tbody = $('#trainingUserList');
        // remove old user list
        tbody.find('tr').remove();
        // put user list
        $.each(trainingUserList, function (index, value) {
            var html = $('<tr></tr>');
            html.append($('<td>' + value.trainingUserId + '</td>'));
            html.append($('<td>' + value.name + '</td>'));
            html.append($('<td style="text-align: left;"><img id="usersAvatar" email="' + value.userEmail + '"/>' + value.userName + '</td>'));

            html.append(getRating(value.rating, value.ratingVary));
            if (value.volatilityVary == null)
                value.volatilityVary = 0;
            html.append('<td>' + value.volatility +
                '(' + (value.volatilityVary >= 0 ? '+' : '') + value.volatilityVary +
                ')</td>');

            html.append('<td>' + value.competitions + '</td>')
            html.append($('<td>' + value.typeName + '</td>'));
            html.append(getAllowButton(value.trainingUserId, value.allow));
            tbody.append(html);
        });

        // get userList avatars
        $('img#usersAvatar').setAvatar({
            size: 37,
            image: 'http://www.acm.uestc.edu.cn/images/akari_small.jpg'
        });

        $('.switch').bootstrapSwitch();

        $('.switch').on('switch-change', function(e, data) {
            var $el = $(data.el);
            var queryString = getQueryString('allow', $el.attr('trainingUserId'), data.value);
            $.post(queryString, function(data) {
                if (data.result != 'ok')
                    $el.parent().bootstrapSwitch('toggleState');
            });
        })
    });
}

function changeOrder(field) {
    if (currentCondition["trainingUserCondition.orderFields"] == field)
        currentCondition["trainingUserCondition.orderAsc"] = (currentCondition["trainingUserCondition.orderAsc"] == "true" ? "false" : "true");
    else {
        currentCondition["trainingUserCondition.orderFields"] = field;
        currentCondition["trainingUserCondition.orderAsc"] = "false";
    }
    refreshTrainingUserList(currentCondition);
}

$(document).ready(function () {

    currentCondition = {
        "currentPage": null,
        "trainingUserCondition.startId": undefined,
        "trainingUserCondition.endId": undefined,
        "trainingUserCondition.name": undefined,
        "trainingUserCondition.type": undefined,
        "trainingUserCondition.allow": undefined,
        "trainingUserCondition.orderFields": "id",
        "trainingUserCondition.orderAsc": false
    };

    $.each($('.orderButton'), function(){
        var field = $(this).attr('field');
        $(this).setButton({
            callback: function(){
                changeOrder(field);
            }
        });
    });

    refreshTrainingUserList(currentCondition);
});