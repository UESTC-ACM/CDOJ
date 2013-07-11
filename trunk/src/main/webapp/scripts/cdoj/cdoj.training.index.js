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
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

$(document).ready(function () {

    $('#registerTrainingUserButton').setButton({
        callback: function(){
            var content = $('#trainingUserRegister');
            var form = content.find('.form-horizontal');
            var info = form.getFormData();

            var data = {
                "trainingUserDTO.name": info["trainingUserDTO.name"],
                "trainingUserDTO.type": info["trainingUserDTO.type"]
            };
            $.post('/training/register', data, function(data) {
                content.checkValidate({
                    result: data,
                    onSuccess: function(){
                        alert('Successful!');
                        window.location.reload();
                    }
                });
                console.log(data);
            });
            return false;
        }
    });

    $.post('/training/user/search', function(data) {
        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }

        var trainingUserList = data.trainingUserList;
        var teamList = $('#teamList');
        var personalList = $('#personalList');
        teamList.find('tr').remove();
        personalList.find('tr').remove();
        $.each(trainingUserList, function(index, value) {
            var html = $('<tr></tr>');
            html.append($('<td>' + value.rank + '</td>'));
            html.append($('<td><a href="/training/user/show/' + value.trainingUserId + '">' + value.name + '</a></td>'));

            html.append(getRating(value.rating, value.ratingVary));
            html.append(getVolatility(value.volatility, value.volatilityVary));

            html.append('<td>' + value.competitions + '</td>');

            if (value.type == 0)
                personalList.append(html);
            else
                teamList.append(html);
        });
    });

    $.post('/training/contest/search', function(data) {
        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }
        console.log(data);

        trainingContestList = data.trainingContestList;
        var tbody = $('#trainingContestList');
        // remove old user list
        tbody.find('tr').remove();
        // put user list
        $.each(trainingContestList, function (index, value) {
            var html = $('<tr></tr>');
            html.append($('<td>' + value.trainingContestId + '</td>'));
            html.append($('<td><a href="/training/contest/show/' + value.trainingContestId + '">' + value.title + '</a></td>'));
            if (value.isPersonal)
                html.append($('<td>Personal</td>'));
            else
                html.append($('<td>Team</td>'));
            tbody.append(html);
        });
    });
});