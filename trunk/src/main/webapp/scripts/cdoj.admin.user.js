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
 * refresh the user list
 * @param condition
 */
function refreshUserList(condition) {
    console.log(condition);
    $.post('/admin/user/search', condition, function(data) {

        var tbody = $('#userList');
        // remove old user list
        tbody.find('tr').remove();

    });
}

$(document).ready(function(){
    refreshUserList({
        "userCondition.startId":null
    });
});