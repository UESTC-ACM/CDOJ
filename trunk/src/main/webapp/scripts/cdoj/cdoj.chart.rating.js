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

function drawStatusChart(teamStatus, totUsers, totContests) {
    var margin = { top: 10, right: 10, bottom: 10, left: 10 };
    var width = 241 - margin.left - margin.right;
    var height = 176 - margin.top - margin.bottom;
    var radius = Math.min(width, height) / 2;

    console.log(teamStatus, totUsers, totContests);
    var data = [{
        range: "1",
        tot: subSum(teamStatus, 1, 1),
        color: '#EE0000'
    }, {
        range: "2-3",
        tot: subSum(teamStatus, 2, 3),
        color: '#DDCC00'
    }, {
        range: "4-9",
        tot: subSum(teamStatus, 4, 9),
        color: '#6666FF'
    }, {
        range: "10-15",
        tot: subSum(teamStatus, 10, 15),
        color: '#00A900'
    }, {
        range: ">16",
        tot: subSum(teamStatus, 16, totUsers),
        color: '#999999'
    }];
    console.log(data);

    var arc = d3.svg.arc()
        .outerRadius(radius - 10)
        .innerRadius(radius - 40);

    var pie = d3.layout.pie()
        .sort(null)
        .value(function(d) { return d.tot; });

    var chart = d3.select('#statusChart').append('svg')
        .attr('width', width + margin.left + margin.right)
        .attr('height', height + margin.top + margin.bottom)
        .append('g')
        .attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')');

    data.forEach(function(d) {
        d.tot = +d.tot;
    });

    var g = chart.selectAll(".arc")
        .data(pie(data))
        .enter().append("g")
        .attr("class", "arc");

    g.append("path")
        .attr("d", arc)
        .style("fill", function(d) { return d.data.color; });

    g.append("text")
        .attr("transform", function(d) { return "translate(" + arc.centroid(d) + ")"; })
        .attr("dy", ".35em")
        .style("text-anchor", "middle")
        .text(function(d) {
            if (d.data.tot > 0) return d.data.range;
            return null;
        });
}

function drawAllUsersRatingChart(chartId, usersSummary, userRank) {
    console.log(usersSummary);
    var minRating = d3.min(usersSummary, function(userSummary) {
        return d3.min(userSummary, function(data) {
            return data.rating;
        });
    });
    var maxRating = d3.max(usersSummary, function(userSummary) {
        return d3.max(userSummary, function(data) {
            return data.rating;
        });
    });
    var maxContestId = d3.max(usersSummary, function(userSummary) {
        return d3.max(userSummary, function(data) {
            return data.contestId;
        });
    });
    var ratingBetween = Math.max(500.0, maxRating - minRating);

    minRating = Math.max(0, minRating - ratingBetween / 24);
    maxRating = maxRating + ratingBetween / 24;
    var colors = {
        gray: '#999999',
        green: '#00A900',
        blue: '#6666FF',
        yellow: '#DDCC00',
        red: '#EE0000'
    };

    console.log(minRating, maxRating, maxContestId);

    var margin = { top: 10, right: 200, bottom: 20, left: 20 };
    var width = 1033 - margin.left - margin.right;
    var height = 600 - margin.top - margin.bottom;

    var chart = d3.select(chartId).append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    //Warning under IE 7 8
    if (Sys.ie678) {
        chart.append('text')
            .attr('x', (width / 2))
            .attr('y', 0 - (margin.top / 2))
            .attr('text-anchor', 'middle')
            .style('font-size', '16px')
            .style('fill', 'red')
            .style('text-decoration', 'underline')
            .text('This chart is not fully supported under IE6 7 8, Please download chrome/firefox/opera/safari/IE9/IE10');
    }

    // Render our axis.
    var yAxisWidth = 50;
    var xAxisHeight = 20;

    var xScale = d3.scale.ordinal()
        .domain(d3.range(maxContestId + 1))
        .rangePoints([yAxisWidth, width]);
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

    var labelHeight = height / userRank.length;

    $.each(userRank, function(index, data) {
        var lineData = [{
            'x': xScale(data.lastContestId),
            'y': yScale(data.rating)
        }, {
            'x': width + 30,
            'y': labelHeight * (data.rank - 0.5) - 4
        }];
        var lineFunction = d3.svg.line()
            .x(function(d) { return d.x; })
            .y(function(d) { return d.y; })
            .interpolate("linear");
        chart.append("path")
            .attr("class", "ratingLine")
            .attr({
                'fill': 'none',
                'stroke': '#ccc',
                'stroke-width': '1.5px'
            })
            .attr("d", lineFunction(lineData));
    });

    chart.append('g').attr('class', 'trainingUserHrefLabel').selectAll('text')
        .data(userRank)
        .enter().append('text')
        .attr('x', width + 40)
        .attr('y', function(d) {return labelHeight * (d.rank - 0.5);})
        .attr('text-anchor', 'left')
        .style('font-size', '12px')
        .style('fill', function(d) {return colors[getRatingColor(d.rating)];})
        .text(function(d) {return d.name;});

    chart.append('g').attr('class', 'trainingUserHrefLabelCircle').selectAll('path')
        .data(userRank)
        .enter().append('path')
        .attr({
            'stroke': 'steelblue',
            'stroke-width': '3px'
        })
        .attr('transform', function(d) { return "translate(" + (width + 30) + "," + (labelHeight * (d.rank - 0.5) - 4) + ")"; })
        .attr('d', d3.svg.symbol().type('circle').size(64))
        .attr('fill', 'white');

    $.each(usersSummary, function(index, teamSummary) {
        teamSummary = teamSummary.splice(1);
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
            .attr('transform', function(d) { return "translate(" + xScale(d.contestId) + "," + yScale(d.rating) + ")"; })
            .attr('fill', function(d) {return colors[getRatingColor(d.rating)];})
            .attr('d', d3.svg.symbol().type('square').size(64));
    });

}

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
    var margin = { top: 10, right: 20, bottom: 20, left: 20 };
    var width = 769 - margin.left - margin.right;
    var height = 400 - margin.top - margin.bottom;

    var chart = d3.select("#ratingChart").append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    //Warning under IE 7 8
    if (Sys.ie678) {
        chart.append('text')
            .attr('x', (width / 2))
            .attr('y', 0 - (margin.top / 2))
            .attr('text-anchor', 'middle')
            .style('font-size', '16px')
            .style('fill', 'red')
            .style('text-decoration', 'underline')
            .text('This chart is not fully supported under IE6 7 8, Please download chrome/firefox/opera/safari/IE9/IE10');
    }

    // Render our axis.
    var yAxisWidth = 50;
    var xAxisHeight = 20;

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
        .attr('transform', function(d) { return "translate(" + xScale(d.contestId) + "," + yScale(d.rating) + ")"; })
        .attr('fill', function(d) {return colors[getRatingColor(d.rating)];})
        .attr('d', d3.svg.symbol().type('square'));
}