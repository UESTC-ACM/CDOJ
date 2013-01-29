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
 * Created with IntelliJ IDEA.
 * User: mzry1992
 * Date: 13-1-27
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */

/**
 * current search condition
 */
var currentCondition;

/**
 * refresh the user list
 * @param condition
 */
function refreshUserList(condition) {
    $.post('/admin/user/search', condition, function(data) {
        if (data.result == "error") {
            //TODO alert?
            return;
        }

        //pagination
        $('#pageInfo').empty();
        $('#pageInfo').append(data.pageInfo);
        $('#pageInfo').find('a').click(function(e) {
            console.log(this);
            console.log($(this).attr("href"));
            currentCondition.currentPage = $(this).attr("href");
            refreshUserList(currentCondition);
            return false;
        });

        userList = data.userList;
        var tbody = $('#userList');
        // remove old user list
        tbody.find('tr').remove();
        // put user list
        $.each(userList,function(index,value){
            var html = '<tr>'+
                '<td><input type="checkbox" id="deleteSelector'+value.userId+'" value="'+value.userId+'"></td>'+
                '<td>'+value.userId+'</td>'+
                '<td>'+value.userName+'</td>'+
                '<td>'+value.nickName+'</td>'+
                '<td>'+value.email+'</td>'+
                '<td>'+value.type+'</td>'+
                '<td>'+value.lastLogin+'</td>'+
                '<td><a href="#" onclick="editUserDialog('+index+')"><i class="icon-pencil"/></a></td>'+
                '</tr>';
            tbody.append(html);
        });
    });
}

$(document).ready(function(){

    $('#userCondition_departmentId').prepend('<option value="0">All</option>');
    $('#userCondition_departmentId').attr("value",0);

    currentCondition = {
        "currentPage":null,
        "userCondition.startId":null,
        "userCondition.endId":null,
        "userCondition.userName":null,
        "userCondition.type":null,
        "userCondition.school":null,
        "userCondition.departmentId":null
    };

    $('input#search').click(function(e) {
        $.each(currentCondition, function(index,value) {
            if (index.indexOf('.') != -1)
                currentCondition[index] = $('#'+index.replace('.','_')).val();
        });
        if (currentCondition["userCondition.departmentId"] == 0)
            currentCondition["userCondition.departmentId"] = null;
        currentCondition.currentPage = 1;
        refreshUserList(currentCondition);
        $('#TabMenu a:first').tab('show');
        return false;
    });

    $('a#selectAllUser').click(function(e) {
        selectorList = $('#userList').find(':checkbox');
        $.each(selectorList,function() {
            $(this).attr("checked", true);
        });
        return false;
    });

    $('a#clearSelectedUser').click(function(e) {
        selectorList = $('#userList').find(':checkbox');
        $.each(selectorList,function() {
            $(this).attr("checked", false);
        });
        return false;
    });

    $('a#deleteSelectedUser').click(function(e) {
        selectorList = $('#userList').find(':checkbox');
        var deleteList = new Array();
        $.each(selectorList,function() {
            if ($(this).attr("checked") == "checked") {
                deleteList.push($(this).attr("value"));
            }
        });
        console.log(deleteList.join());
        return false;
    });

    refreshUserList(currentCondition);
});