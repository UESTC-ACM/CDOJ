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

$(document).ready(function () {
    currentUserPageUser = $('#currentUserPageUser').attr('value');

    $.post('/user/problemStatus/' + currentUserPageUser, function (data) {
        console.log(data);
        var problemCount = Math.max(100, data.problemCount);
        var problemStatus = data.problemStatus;
        var status = [];
        for (var i = 1; i <= problemCount; i++)
            if (problemStatus[i] == 'PASS')
                status.push({
                    problemId: i - 1,
                    status: 1
                });
            else if (problemStatus[i] == 'FAIL')
                status.push({
                    problemId: i - 1,
                    status: 2
                });
            else
                status.push({
                    problemId: i - 1,
                    status: 0
                });

        var colors = ['#f2eada', '#45b97c', '#aa2116'];
        var margin = { top: 20, right: 0, bottom: 20, left: 0 };
        var width = 1033 - margin.left - margin.right;
        var gridSize = Math.floor(width / 15);
        var gridHeight = 30;
        var height = gridHeight * Math.ceil(problemCount / 15);

        console.log(height, width, gridSize, gridHeight, margin, problemCount);
        var chart = d3.select("#chart").append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

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
            .attr("width", gridSize)
            .attr("height", gridHeight)
            .style("fill", colors[0])
            .transition().duration(2000)
            .style('fill', function (d) {
                return colors[d.status];
            });

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
            .attr('class', 'problemLabel');

        $('.problemLabel').click(function(){
            alert(this);
        });
    })
});