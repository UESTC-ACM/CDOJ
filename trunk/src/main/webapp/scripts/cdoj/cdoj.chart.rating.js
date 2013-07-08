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
 * Created with IntelliJ IDEA.
 * User: mzry1992
 * Date: 13-7-6
 * Time: 下午7:26
 * To change this template use File | Settings | File Templates.
 */

function drawRatingChart(teamSummary) {
    var minRating = d3.min(teamSummary, function(data) {
        return data.rating;
    });
    var maxRating = d3.max(teamSummary, function(data) {
        return data.rating;
    });
    var maxContestId = d3.max(teamSummary, function(data) {
        return data.contestId;
    });
    var ratingBetween = Math.max(500.0, maxRating - minRating);

    minRating = Math.max(0, minRating - ratingBetween / 6);
    maxRating = maxRating + ratingBetween / 6;
    var colors = {
        gray: '#999999',
        green: '#00A900',
        blue: '#6666FF',
        yellow: '#DDCC00',
        red: '#EE0000'
    };
    var margin = { top: 30, right: 20, bottom: 20, left: 20 };
    var width = 1033 - margin.left - margin.right;
    var height = 400 - margin.top - margin.bottom;

    var chart = d3.select("#ratingChart").append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    //Title
    chart.append('text')
        .attr('x', (width / 2))
        .attr('y', 0 - (margin.top / 2))
        .attr('text-anchor', 'middle')
        .style('font-size', '16px')
        .style('text-decoration', 'underline')
        .text('Team');
    //Warning under IE 7 8
    if (Sys.ie678) {
        chart.append('text')
            .attr('x', (width / 2))
            .attr('y', 20 - (margin.top / 2))
            .attr('text-anchor', 'middle')
            .style('font-size', '16px')
            .style('fill', 'red')
            .style('text-decoration', 'underline')
            .text('This chart is not fully supported under IE6 7 8');
    }

    // Render our axis.
    var yAxisWidth = 50;
    var xAxisHeight = 50;

    var xScale = d3.scale.ordinal()
        .domain(d3.range(maxContestId + 1))
        .rangePoints([yAxisWidth, width]);
    /*
    var xScale = d3.scale.linear()
        .domain([0, maxContestId + 1])
        .range([yAxisWidth, width]);
        */
    var xAxis = d3.svg.axis().scale(xScale).tickSize(1.5);
    chart.append('g')
        .attr({
            'class': 'x axis',
            'stroke': '#000',
            'fill': '#000',
            'transform': 'translate(0,' + (height - xAxisHeight) + ')'
        })
        .call(xAxis);
    var yScale = d3.scale.linear()
        .domain([minRating, maxRating])
        .range([height - xAxisHeight, 0]);
    var yAxis = d3.svg.axis().scale(yScale).orient('left').tickSize(1.5);
    chart.append('g')
        .attr({
            'class': 'y axis',
            'stroke': '#000',
            'fill': '#000',
            'transform': 'translate(' + yAxisWidth + ',0)'
        })
        .call(yAxis);

    var line = d3.svg.line()
        .x(function(d) { return xScale(d.contestId); })
        .y(function(d) { return yScale(d.rating); });
    chart.append('g').attr('class', 'ratingLine').append("path")
        .datum(teamSummary)
        .attr("class", "ratingLine")
        .attr({
            'fill': 'none',
            'stroke': 'black',
            'stroke-width': '1.5px'
        })
        .attr("d", line);

    chart.append('g').attr('class', 'ratingHrefLabel').selectAll('path')
        .data(teamSummary)
        .enter().append('path')
        .attr({
            'stroke': 'black',
            'stroke-width': '1.5px'
        })
        .attr('transform', function(d) { console.log(xScale(d.contestId)); return "translate(" + xScale(d.contestId) + "," + yScale(d.rating) + ")"; })
        .attr('fill', function(d) {return getRatingColor(d.rating);})
        .attr('d', d3.svg.symbol().type('square'));
}