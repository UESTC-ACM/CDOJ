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

function getRating(rating, color, isFirst, ratingVary) {
    var html = $('<td></td>');
    var ratingSpan = $('<span class="rating-' + color + ' label-rating">' + rating + '</span>');
    var varySpan = $('<span class="label pull-right label-diff"></span>');
    if (isFirst != undefined) {
        varySpan.addClass('label-info');
        varySpan.append('INIT');
    } else {
        if (ratingVary >= 0) {
            varySpan.addClass('label-success');
            varySpan.append('+' + ratingVary);
        } else {
            varySpan.addClass('label-important');
            varySpan.append(ratingVary);
        }
    }
    html.append(ratingSpan);
    html.append(varySpan);
    return html;
}

function getHtml(value) {
    var html = $('<tr></tr>');
    html.append('<td>' + value.contestId + '</td>');
    html.append('<td>' + value.contestName + '</td>');
    html.append(getRating(value.rating, value.ratingColor, value.isFirst, value.ratingVary));
    html.append('<td>' + value.volatility +
        '(' + (value.volatilityVary >= 0 ? '+' : '') + value.volatilityVary +
        ')</td>');
    return html;
}

$(document).ready(function () {
    var currentTeam = '1';
    $.post('/training/teamSummary/' + currentTeam, function(data){
        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }

        var tbody = $('#teamHistoryList');
        // remove old list
        tbody.find('tr').remove();
        // put list
        var teamSummary = data.teamSummary;
        tbody.prepend(getHtml({
            contestId: '0',
            contestName: 'Initialization',
            rating: 1200,
            volatility: 550,
            volatilityVary: 0,
            isFirst: true
        }));
        $.each(teamSummary, function(index, value){
            tbody.prepend(getHtml(value));
        });

        var minRating = data.minRating;
        var maxRating = data.maxRating;
        var ratingBetween = maxRating - minRating;
        minRating = Math.max(0, minRating - ratingBetween / 6);
        maxRating = maxRating + ratingBetween / 6;
        var colors = {
            gray: '#999999',
            green: '#00A900',
            blue: '#6666FF',
            yellow: '#DDCC00',
            red: '#EE0000'
        };
        var margin = { top: 20, right: 20, bottom: 20, left: 20 };
        var width = 1033 - margin.left - margin.right;
        var height = 400 - margin.top - margin.bottom;

        var chart = d3.select("#ratingChart").append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
        // Render our axis.
        var yAxisWidth = 50;
        var xAxisHeight = 50;

        var xScale = d3.scale.linear()
            .domain([0, teamSummary.length + 1])
            .range([yAxisWidth, width]);
        var xAxis = d3.svg.axis().scale(xScale);
        chart.append('g')
            .attr({
                'class': 'x axis',
                'transform': 'translate(0,' + (height - xAxisHeight) + ')'
            })
            .call(xAxis);
        var yScale = d3.scale.linear()
            .domain([minRating, maxRating])
            .range([height - xAxisHeight, 0]);
        var yAxis = d3.svg.axis().scale(yScale).orient('left');
        chart.append('g')
            .attr({
                'class': 'y axis',
                'transform': 'translate(' + yAxisWidth + ',0)'
            })
            .call(yAxis);
        var line = d3.svg.line()
            .x(function(d) { return xScale(d.contestId); })
            .y(function(d) { return yScale(d.rating); });
        chart.append('g').attr('class', 'ratingLine').append("path")
            .datum(teamSummary)
            .attr("class", "ratingLine")
            .attr("d", line);

        chart.append('g').attr('class', 'ratingHrefLabel').selectAll('path')
            .data(teamSummary)
            .enter().append('path')
            .attr('transform', function(d) { return "translate(" + xScale(d.contestId) + "," + yScale(d.rating) + ")"; })
            .attr('fill', function(d) {return colors[d.ratingColor];})
            .attr('d', d3.svg.symbol().type('square'));
    });
});