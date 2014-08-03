cdoj
.directive("uiTrainingRatingChart",
->
  restrict: "E"
  scope:
    trainingUserList: "="
    trainingContestList: "="
  controller: [
    "$scope", "$http", "$window", "$element", "$timeout"
    ($scope, $http, $window, $element, $timeout) ->
      $scope.selectedUser = null
      $scope.selectUser = ->
        updateChart(
          _.filter($scope.trainingUserList, (user) ->
            if $scope.selectedUser == null
              return true
            else
              return user.trainingUserId == $scope.selectedUser
          )
          $scope.trainingContestList
        )

      # Set width and height
      width = 1000
      height = 600
      margin =
        top: 10
        right: 10
        bottom: 20
        left: 50
      # Initialize chart
      chart =
        d3.select("#rating-chart")
        .append("svg")
        .attr("width", width)
        .attr("height", height)
      chart
      .append("g")
      .attr(
        class: "x axis"
        transform: "translate(0, " + (height - margin.bottom) + ")"
      )
      chart
      .append("g")
      .attr(
        class: "y axis"
        transform: "translate(" + margin.left + ", 0)"
      )
      background = chart.append("g").attr("class", "background")
      ratingLinesContainer = chart.append("g").attr("class", "rating-lines")
      tooltipHiddenTimer = null
      tooltip =
        d3.select("body")
        .append("div")
        .style("position", "absolute")
        .style("z-index", "99999")
        .style("visibility", "hidden")
        .style("border", "1px solid rgb(255, 221, 221)")
        .style("padding", "2px")
        .style("font-size", "12px")
        .style("background-color", "rgba(0, 0, 0, 0.8)")
        .style("color", "rgb(255, 255, 255)")
        .html("")
        .on("mouseover", ->
          if tooltipHiddenTimer != null
            $timeout.cancel(tooltipHiddenTimer)
            tooltipHiddenTimer = null
        )
        .on("mouseout", ->
          if tooltipHiddenTimer == null
            tooltipHiddenTimer = $timeout(->
              tooltip.style("visibility", "hidden")
            , 300)
        )

      updateChart = (trainingUserList, trainingContestList) ->
        if (trainingUserList.length == 0 ||
          trainingContestList.length == 0) then return
        # Map contest id to x-position in x-axis
        contestMap = {}
        for contest, i in trainingContestList
          contestMap[contest.trainingContestId] = i

        # Calculate rating range
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

        # Draw x-label
        xScale =
          d3.scale
          .ordinal()
          .domain(d3.range(trainingContestList.length))
          .rangePoints([margin.left, width - margin.right])
        xAxis = d3.svg.axis().scale(xScale).tickFormat(
          (d) ->
            d + 1
        )
        chart.selectAll("g.x.axis").call(xAxis)

        # Draw y-label
        yScale =
          d3.scale
          .linear()
          .domain([minimalRating, maximalRating])
          .range([height - margin.bottom, 0 + margin.top])
        yAxis = d3.svg.axis().scale(yScale).orient("left")
        chart.selectAll("g.y.axis").call(yAxis)

        # Draw background
        background.selectAll("rect").remove()
        background.selectAll("line").remove()
        ratingValues = [minimalRating]
        for rating in [900, 1200, 1500, 2200]
          if rating > minimalRating && rating < maximalRating
            ratingValues.add(rating)
        ratingValues.add(maximalRating)
        for i in [1...ratingValues.length]
          background.append("rect")
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
          background.append("line")
          .attr("x1", xScale(0))
          .attr("y1", yScale(rating))
          .attr("x2", xScale(trainingContestList.length - 1))
          .attr("y2", yScale(rating))
          .attr("stroke", "#A9A9A9")

        # Draw rating
        line =
          d3.svg.line()
          .x((d) ->
            xScale(contestMap[d.trainingContestId]))
          .y((d) ->
            yScale(d.rating))
        # remove
        ratingLinesContainer.selectAll("g.rating-line").remove()
        ratingLines =
          ratingLinesContainer.selectAll("g.rating-line").data(trainingUserList)
        # Draw rating line for every user
        ratingLineContainer =
          ratingLines.enter()
          .append("g")
          .attr("class", "rating-line")
          .datum((d) ->
            d.ratingHistoryList)
        ratingLine =
          ratingLineContainer
          .append("path")
          .attr("class", "rating")
          .attr(
            fill: "none"
            stroke: "black"
            "stroke-width": "1.5px"
          ).attr("d", line)
        getRatingColor = (rating) ->
          if (rating < 900)
            return "#999999"
          else if (rating < 1200)
            return "#00A900"
          else if (rating < 1500)
            return "#6666FF"
          else if (rating < 2200)
            return "#DDCC00"
          else
            return "#EE0000"
        ratingLabel =
          ratingLineContainer
          .selectAll("path.rating-label")
          .data((d) ->
            d)
          .enter()
          .append("path")
          .attr("class", "rating-label")
          .attr(
            stroke: "black"
            "stroke-width": "1.5px"
          ).attr("transform", (d) ->
            "translate(" +
              xScale(contestMap[d.trainingContestId]) +
              ", " +
              yScale(d.rating) +
              ")"
          ).attr("fill", (d) ->
            getRatingColor(d.rating)
          ).attr("d", d3.svg.symbol().type("square"))
          .on("mouseover", (d) ->
            formatDouble = (val) ->
              return _.sprintf("%.0f", val)
            tooltip.style("visibility", "visible")
            .style("top", (event.pageY-10)+"px")
            .style("left",(event.pageX+10)+"px")
            .html(->
              """
= <b style="color: #{getRatingColor(d.rating)};">
#{formatDouble(d.rating)}
</b> (#{if d.ratingVary > 0 then "+" else ""}#{formatDouble(d.ratingVary)})
<br/>
Rank: #{d.rank}
<br/>
<a href="/#/training/contest/show/#{d.trainingContestId}" target="_blank">
View contest
</a>
              """
            )
          ).on("mouseout", ->
            tooltipHiddenTimer = $timeout(->
              tooltip.style("visibility", "hidden")
            , 300)
          )

      updateChart($scope.trainingUserList, $scope.trainingContestList)
  ]
  replace: true
  templateUrl: "template/training/trainingRatingChart.html"
)