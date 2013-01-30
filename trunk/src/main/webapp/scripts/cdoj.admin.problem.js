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
 * Date: 13-1-30
 * Time: 下午9:13
 * To change this template use File | Settings | File Templates.
 */

var currentCondition;

function refreshProblemList(condition) {
    $.post('/admin/problem/search', condition, function(data) {
        console.log(condition);
        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }

        //pagination
        $('#pageInfo').empty();
        $('#pageInfo').append(data.pageInfo);
        $('#pageInfo').find('a').click(function(e) {
            currentCondition.currentPage = $(this).attr("href");
            refreshUserList(currentCondition);
            return false;
        });

        console.log(data);
    });
}

$(document).ready(function(){
    currentCondition = {
        "currentPage":1,
        "problemCondition.startId":1,
        "problemCondition.endId":null,
        "problemCondition.title":null,
        "problemCondition.description":null,
        "problemCondition.input":null,
        "problemCondition.output":null,
        "problemCondition.sampleInput":null,
        "problemCondition.sampleOutput":null,
        "problemCondition.hint":null,
        "problemCondition.source":null,
        "problemCondition.isSpj":null,
        "problemCondition.startDifficulty":null,
        "problemCondition.endDifficulty":null,
        "userCondition.startId":1,
        "userCondition.endId":null,
        "userCondition.userName":null,
        "userCondition.type":null,
        "userCondition.school":null,
        "userCondition.departmentId":null
    }

    refreshProblemList(currentCondition);
});