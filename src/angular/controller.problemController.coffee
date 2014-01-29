cdoj.controller("ProblemController", [
  "$scope", "$rootScope", "$http", "$window"
  ($scope, $rootScope, $http, $window)->
    $scope.problem =
      description: ""
      title: ""
      isSpj: false
      timeLimit: 1000
      javaTimeLimit: 3000
      memoryLimit: "--"
      javaMemoryLimit: "--"
      solved: 0
      tried: 0
      input: ""
      output: ""
      sampleInput: ""
      sampleOutput: ""
      hint: ""
      source: ""
    $scope.submitDTO =
      codeContent: ""
      languageId: 1
      contestId: 0
      problemId: 0
    $scope.problemId = 0
    $scope.contest = null
    $scope.fieldInfo = []
    $scope.$watch("problemId",
    ->
      problemId = angular.copy($scope.problemId)
      $http.post("/problem/data/#{problemId}").then (response)->
        data = response.data
        if data.result == "success"
          $scope.problem = data.problem
          $rootScope.title = $scope.problem.title
        else
          alert data.error_msg
    )
    $scope.submitCode = ->
      submitDTO = angular.copy($scope.submitDTO)
      submitDTO.problemId = $scope.problemId
      submitDTO.contestId = $scope.contestId
      console.log submitDTO
      if submitDTO.codeContent == undefined then return
      if $rootScope.hasLogin == false
        $window.alert "Please login first!"
      else
        $http.post("/status/submit", submitDTO).then (response)->
          data = response.data
          if data.result == "success"
            $window.location.href = "/status/list"
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
])
