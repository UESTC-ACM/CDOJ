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
 * current user list
 */
var userList;

/**
 * refresh the user list
 * @param condition
 */
function refreshUserList(condition) {
    $.post('/admin/user/search', condition, function (data) {
        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }

        //pagination
        $('#pageInfo').empty();
        $('#pageInfo').append(data.pageInfo);
        $('#pageInfo').find('a').click(function (e) {
            currentCondition.currentPage = $(this).attr("href");
            refreshUserList(currentCondition);
            return false;
        });

        userList = data.userList;
        var tbody = $('#userList');
        // remove old user list
        tbody.find('tr').remove();
        // put user list
        $.each(userList, function (index, value) {
            var html = '<tr>' +
                '<td><input type="checkbox" id="deleteSelector' + value.userId + '" value="' + value.userId + '"></td>' +
                '<td>' + value.userId + '</td>' +
                '<td style="text-align: left;"><img id="usersAvatar" email="' + value.email + '"/>' + value.userName + '</td>' +
                '<td>' + value.nickName + '</td>' +
                '<td>' + value.email + '</td>' +
                '<td>' + value.typeName + '</td>' +
                '<td>' + value.lastLogin + '</td>' +
                '<td><a href="#" onclick="return editUserDialog(' + index + ')"><i class="icon-pencil"/></a></td>' +
                '</tr>';
            tbody.append(html);
        });

        // get userList avatars
        $('img#usersAvatar').setAvatar({
            size: 37
        });

    });
}

/**
 * show dialog to edit user defined in userList[index]
 *
 * @param index
 * @return always return false to stop url jump event.
 */
function editUserDialog(index) {
    user = userList[index];
    userDTO = {
        "userDTO.userId": user.userId,
        "userDTO.nickName": user.nickName,
        "userDTO.email": user.email,
        "userDTO.school": user.school,
        "userDTO.departmentId": user.departmentId,
        "userDTO.studentId": user.studentId,
        "userDTO.type": user.type
    };
    $('#userEditModal').setFormData(userDTO);
    dialog = $('#userEditModal');
    dialog.find('#userEditModalLabel').empty();
    dialog.find('#userEditModalLabel').append(user.userName);
    dialog.modal();
    return false;
}

$(document).ready(function () {

    $('#userCondition_departmentId').prepend('<option value="-1">All</option>');
    $('#userCondition_departmentId').attr("value", -1);
    $('#userCondition_type').prepend('<option value="-1">All</option>');
    $('#userCondition_type').attr("value", -1);

    currentCondition = {
        "currentPage": null,
        "userCondition.startId": undefined,
        "userCondition.endId": undefined,
        "userCondition.userName": undefined,
        "userCondition.type": undefined,
        "userCondition.school": undefined,
        "userCondition.departmentId": undefined
    };

    $('input#search').setButton({
        callback: function () {
            currentCondition = $('#userCondition').getFormData();
            currentCondition.currentPage = 1;
            refreshUserList(currentCondition);
            $('#TabMenu a:first').tab('show');
        }
    });

    $('input#reset').setButton({
        callback: function () {
            $('#userCondition').resetFormData();
        }
    });

    $('a#selectAllUser').setButton({
        callback: function () {
            selectorList = $('#userList').find(':checkbox');
            $.each(selectorList, function () {
                $(this).attr("checked", true);
            });
        }
    });

    $('a#clearSelectedUser').setButton({
        callback: function () {
            selectorList = $('#userList').find(':checkbox');
            $.each(selectorList, function () {
                $(this).attr("checked", false);
            });
        }
    });

    $('a#deleteSelectedUser').setButton({
        callback: function () {
            selectorList = $('#userList').find(':checkbox');
            var deleteList = new Array();
            $.each(selectorList, function () {
                if ($(this).attr("checked") == "checked") {
                    deleteList.push($(this).attr("value"));
                }
            });
            if (confirm('Are you sure to delete ' + deleteList.join() + '?') == true) {
                queryString = "method=delete&id=" + deleteList.join();
                $.post('/admin/user/operator?' + queryString, function (data) {
                    alert(data.result);
                    refreshUserList(currentCondition);
                });
            }
        }
    });

    $('#userEditModal').setDialog({
        callback: function () {
            info = $('#userEditModal').getFormData();
            $.post('/admin/user/edit', info, function (data) {
                $("#userEditModal .form-horizontal").checkValidate({
                    result: data,
                    onSuccess: function () {
                        $("#userEditModal").modal('hide');
                        refreshUserList(currentCondition);
                    }
                });
            });
        }
    });

    refreshUserList(currentCondition);
});