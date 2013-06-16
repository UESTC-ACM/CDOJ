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
 * Javascript for user center page.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

var currentUserPageUser;

/**
 * show dialog to edit user defined in userList[index]
 *
 * @param index
 * @return always return false to stop url jump event.
 */
function editUserDialog(index) {

    var currentCondition = {
        "currentPage": null,
        "userCondition.startId": index,
        "userCondition.endId": index,
        "userCondition.userName": undefined,
        "userCondition.type": undefined,
        "userCondition.school": undefined,
        "userCondition.departmentId": undefined,
        "userCondition.orderFields": "solved",
        "userCondition.orderAsc": false
    };

    $.post('/user/search', currentCondition, function(data){
        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }

        user = data.userList[0];
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
        var dialog = $('#userEditModal');
        dialog.find('#userEditModalLabel').empty();
        dialog.find('#userEditModalLabel').append(user.userName);

        var mult = 0.95;
        if (Sys.windows)
            mult = 0.65;

        dialog.find('.modal-body').css('max-height', Math.min(600, $(window).height() * mult));

        dialog.modal();
    });
    return false;
}

$(document).ready(function () {
    $('img#userAvatar-large').setAvatar({
        image: 'http://www.acm.uestc.edu.cn/images/akari.jpg'
    });
    currentUserPageUser = $('#currentUserPageUser').attr('value');
    if (currentUser == currentUserPageUser) {
        $('#userAvatarWrap').tooltip({
            placement: 'bottom'
        });
        $('#userAvatarWrap').attr('href', 'http://gravatar.com/emails/');

        $('#userEditModal').setDialog({
            callback: function () {
                info = $('#userEditModal').getFormData();
                if (info["userDTO.password"] == '')
                    info["userDTO.password"] = undefined;
                $.post('/user/edit', info, function (data) {
                    $("#userEditModal .form-horizontal").checkValidate({
                        result: data,
                        onSuccess: function () {
                            alert('Edit profile successful!');
                            window.location.reload();
                        }
                    });
                });
            }
        });
    }

    $.post('/user/problemStatus/' + currentUserPageUser, function (data) {
        var problemStatus = data.problemStatus;
        var status = [];
        $.each(problemStatus, function(index, data) {
            if (data == 'PASS')
                status.push({
                    problemId: index - 1,
                    status: 1
                });
            else if (data == 'FAIL')
                status.push({
                    problemId: index - 1,
                    status: 2
                });
            else
                status.push({
                    problemId: index - 1,
                    status: 0
                });
        });
        var problemCount = status.length;
        console.log(status, problemCount);

        var colors = ['#f2eada', '#45b97c', '#aa2116'];
        var margin = { top: 20, right: 0, bottom: 20, left: 0 };
        var width = 769 - margin.left - margin.right;
        var gridSize = Math.floor(width / 15);
        var gridHeight = 30;
        var height = gridHeight * Math.ceil(problemCount / 15);

        var chart = d3.select("#chart").append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        var problemLabel = chart.selectAll('.problemLabel')
            .data(status)
            .enter().append("text")
            .text(function(d) { return d.problemId + 1; })
            .attr("x", function (d) {
                return gridSize * (d.problemId % 15 + 0.5);
            })
            .attr("y", function (d) {
                return gridHeight * (Math.floor(d.problemId / 15) + 0.7);
            })
            .style('fill', colors[0])
            .style("text-anchor", "middle")
            .attr('class', 'problemLabel')
            .attr("value", function(d) {
                return d.problemId + 1;
            })
            .transition().duration(2000)
            .style('fill', '#000000');

        var heatMap = chart.selectAll(".problemStatus")
            .data(status)
            .enter().append("rect")
            .attr("x", function (d) {
                return gridSize * (d.problemId % 15);
            })
            .attr("y", function (d) {
                return gridHeight * Math.floor(d.problemId / 15);
            })
            .attr("rx", 4)
            .attr("ry", 4)
            .attr("class", "problemStatus bordered")
            .attr("value", function(d) {
                return d.problemId + 1;
            })
            .attr("width", gridSize)
            .attr("height", gridHeight)
            .style("fill", colors[0])
            .transition().duration(2000)
            .style('fill', function (d) {
                return colors[d.status];
            });

        $('.problemStatus').click(function(){
            var problemId = $(this).attr('value');
            window.location.href = '/problem/show/' + problemId;
        });
    })
});