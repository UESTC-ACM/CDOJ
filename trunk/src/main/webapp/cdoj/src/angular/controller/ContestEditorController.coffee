cdoj
.controller("ContestEditorController", [
    "$scope", "$rootScope", "$http", "$window", "$routeParams"
    ($scope, $rootScope, $http, $window, $routeParams) ->
      # Administrator only
      $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.ADMIN)
      $window.scrollTo(0, 0)

      $scope.contest =
        problemList: ""
      $scope.problemList = []
      $scope.fieldInfo = []
      $scope.action = $routeParams.action

      if $scope.action != "new"
        $scope.title = "Edit contest " + $scope.action
        contestId = angular.copy($scope.action)
        $http.post("/contest/data/#{contestId}").success((data)->
          if data.result == "success"
            $scope.contest.action = $scope.action
            $scope.contest.contestId = data.contest.contestId
            $scope.contest.title = data.contest.title
            $scope.contest.type = data.contest.type
            $scope.contest.time = Date.create(data.contest.startTime).format("{yyyy}-{MM}-{dd} {HH}:{mm}")
            length = Math.floor(data.contest.length / 1000)
            length = Math.floor(length / 60)
            $scope.contest.lengthMinutes = length % 60
            length = Math.floor(length / 60)
            $scope.contest.lengthHours = length % 24
            length = Math.floor(length / 24)
            $scope.contest.lengthDays = length
            $scope.contest.description = data.contest.description

            $scope.problemList = _.map(data.problemList, (val)->
              problemId: val.problemId
              title: val.title
            )
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error, please refresh page manually."
        )
      else
        $scope.contest.action = $scope.action
        $scope.contest.type = $rootScope.ContestType.PUBLIC
        $scope.contest.time = Date.create().format("{yyyy}-{MM}-{dd} {HH}:{mm}")
        $scope.contest.lengthMinutes = 0
        $scope.contest.lengthHours = 5
        $scope.contest.lengthDays = 0
        $scope.title = "New contest"

      $scope.$watch("problemList", () ->
        $scope.contest.problemList = _.map($scope.problemList, (val) ->
          return val.problemId
        ).join(",")
      , true)

      $scope.updateProblemTitle = (problem) ->
        if isNaN(parseInt(problem.problemId))
          problem.title = "Invalid problem id!"
        else
          $http.get("/problem/query/#{problem.problemId}/title").success (data) ->
            if data.result == "success"
              if data.list.length == 1
                problem.title = data.list[0]
              else
                problem.title = "No such problem!"
            else
              problem.title = data.error_msg

      $scope.addProblem = ->
        problemId = ""
        if $scope.problemList.length > 0
          lastId = parseInt($scope.problemList.last().problemId)
          if !isNaN(lastId)
            problemId = lastId + 1
        problem =
          problemId: problemId
          title: ""
        $scope.updateProblemTitle(problem)
        $scope.problemList.add(problem)

      $scope.removeProblem = (index) ->
        $scope.problemList.splice(index, 1);

      $scope.submit = ->
        contestEditDTO = angular.copy($scope.contest)
        contestEditDTO.time = Date.create(contestEditDTO.time).getTime()
        $http.post("/contest/edit", contestEditDTO).success((data)->
          if data.result == "success"
            $window.location.href = "#/contest/show/#{data.contestId}"
          else
            # TODO
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
  ])