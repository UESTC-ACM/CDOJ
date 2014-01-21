# Angular!
cdoj.controller('ContestEditorController', [
  "$scope", "$http",
  ($scope, $http) ->
    $scope.contest =
      action : 0
      contestId: 0
      title: 0
      type: 0
      time: 0
      lengthHours: 0
      lengthDays: 0
      lengthMinutes: 0
      description: 0
      problemList: 0
    $scope.problemList = []

    $scope.$watch("problemList", (newVal) ->
      $scope.contest.problemList = _.map(newVal, (val) ->
        return val.problemId
      ).join(",")
    )

    $scope.updateProblemTitle = (problem) ->
      if problem.problemId == ""
        problem.title = "Invalid problem id!"
      else
        $http.get("/problem/query/#{problem.problemId}/title")
        .then(
            (response) ->
              data = response.data
              if data.result == "success"
                if data.list.length == 1
                  problem.title = data.list[0]
                else
                  problem.title = "No such problem!"
              else
                problem.title = data.error_msg
          )

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

    $scope.updateProblemTitle = (problem) ->
      if problem.problemId == undefined
        problem.title = "Invalid problem id!"
      else
        $scope.updateProblemTitle(problem)

    $scope.submit = ->
      contestEditDTO = angular.copy($scope.contest)
      contestEditDTO.time = Date.create(contestEditDTO.time).getTime()
      $http.post("/contest/edit", contestEditDTO).success (data)=>
        if data.result == "success"
          window.location.href = "/contest/show/#{data.contestId}"
        else
          # TODO
          alert data.error_msg
])