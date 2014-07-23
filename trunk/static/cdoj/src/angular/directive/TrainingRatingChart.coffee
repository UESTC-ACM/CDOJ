cdoj
.directive("uiTrainingRatingChart",
->
  restrict: "E"
  scope:
    trainingUserList: "="
    trainingContestList: "="
  controller: [
    "$scope", "$http", "$window", "$element"
    ($scope, $http, $window, $element) ->
      drawChart = ->
        if ($scope.trainingUserList.length == 0 ||
          $scope.trainingContestList.length == 0) then return

        trainingUserList = $scope.trainingUserList
        trainingContestList = $scope.trainingContestList

        minimalRating = d3.min(trainingUserList, (user) ->
          d3.min(user.ratingHistoryList, (rating) ->
            rating.rating
          )
        )
        maximalRating = d3.max(trainingUserList, (user) ->
          d3.max(user.ratingHistoryList, (rating) ->
            rating.rating
          )
        )
        ratingBetween = Math.max(500.0, maximalRating - minimalRating)
        minimalRating = Math.max(0, minimalRating - ratingBetween / 24)
        maximalRating = maximalRating + ratingBetween / 24
        contestMap = {}
        for contest, i in trainingContestList
          contestMap[contest.trainingContestId] = i

        $el = $($element)
        width = Math.max(
          1000,
            trainingContestList.length * 35
        )
        height = 600
        margin =
          top: 10
          right: 200
          bottom: 20
          left: 50

        # Draw chart
        chart =
          d3.select("#rating-chart")
          .append("svg")
          .attr("width", width)
          .attr("height", height)
          .append("g")

        # Draw x-label
        xScale =
          d3.scale
          .ordinal()
          .domain(d3.range(trainingContestList.length))
          .rangePoints([margin.left, width - margin.right])
        xAxis = d3.svg.axis().scale(xScale).tickFormat(
          (d) ->
            if d == 0 then "" else d
        )
        chart.append("g")
        .attr(
          class: "x axis"
          transform: "translate(0, " + (height - margin.bottom) + ")"
        )
        .call(xAxis)

        # Draw y-label
        yScale =
          d3.scale
          .linear()
          .domain([minimalRating, maximalRating])
          .range([height - margin.bottom, 0 + margin.top])
        yAxis = d3.svg.axis().scale(yScale).orient("left")
        chart.append("g")
        .attr(
          class: "y axis"
          transform: "translate(" + margin.left + ", 0)"
        )
        .call(yAxis)

        # Draw background
        ratingValues = [minimalRating]
        for rating in [900, 1200, 1500, 2200]
          if rating > minimalRating && rating < maximalRating
            ratingValues.add(rating)
        ratingValues.add(maximalRating)
        for i in [1...ratingValues.length]
          chart.append("rect")
          .attr("x", xScale(0))
          .attr("y", yScale(ratingValues[i]))
          .attr("width", width - margin.right - margin.left)
          .attr("height", yScale(ratingValues[i - 1]) - yScale(ratingValues[i]))
          .attr("fill", ->
            if (ratingValues[i - 1] < 900)
              return "#EAEAEA"
            else if (ratingValues[i - 1] < 1200)
              return "#C7ECCC"
            else if (ratingValues[i - 1] < 1500)
              return "#E1E1FE"
            else if (ratingValues[i - 1] < 2200)
              return "#F8F4CC"
            else
              return "#FFCCCB"
          )
        ratingValues = [minimalRating]
        for rating in [0...4000] by 100
          if rating > minimalRating && rating < maximalRating
            ratingValues.add(rating)
        ratingValues.add(maximalRating)
        for i in [1...ratingValues.length]
          rating = ratingValues[i]
          chart.append("line")
          .attr("x1", xScale(0))
          .attr("y1", yScale(rating))
          .attr("x2", xScale(trainingContestList.length - 1))
          .attr("y2", yScale(rating))
          .attr("stroke", "#A9A9A9")

        console.log(ratingValues)

        # Draw rating
        _.each(trainingUserList, (data) ->
          # Line coordinate map
          line =
            d3.svg.line()
            .x((d) ->
              xScale(contestMap[d.trainingContestId]))
            .y((d) ->
              yScale(d.rating))

          # Draw rating line
          chart.append("g")
          .attr("class", "rating-line")
          .append("path")
          .datum(data.ratingHistoryList)
          .attr("class", "rating")
          .attr(
            fill: "none"
            stroke: "black"
            "stroke-width": "1.5px"
          ).attr("d", line)

          # Draw rating label
          chart.append("g")
          .attr("class", "rating-line-label")
          .selectAll("path")
          .data(data.ratingHistoryList)
          .enter().append("path")
          .attr(
            stroke: "black"
            "stroke-width": "1.5px"
          ).attr("transform", (d) ->
            "translate(" +
            xScale(contestMap[d.trainingContestId]) + ", " + yScale(d.rating) +
            ")"
          ).attr("fill", (d) ->
            if (d.rating < 900)
              return "#999999"
            else if (d.rating < 1200)
              return "#00A900"
            else if (d.rating < 1500)
              return "#6666FF"
            else if (d.rating < 2200)
              return "#DDCC00"
            else
              return "#EE0000"
          ).attr("d", d3.svg.symbol().type("square"))
        )
      drawChart()
  ]
  replace: true
  templateUrl: "template/training/trainingRatingChart.html"
)