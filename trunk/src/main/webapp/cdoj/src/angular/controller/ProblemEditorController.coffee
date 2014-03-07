cdoj
.controller("ProblemEditorController", [
    "$scope", "$http", "$window", "$routeParams"
    ($scope, $http, $window, $routeParams)->
      $scope.problem =
        description: ""
        title: ""
        isSpj: false
        timeLimit: 1000
        javaTimeLimit: 3000
        memoryLimit: 65535
        javaMemoryLimit: 65535
        outputLimit: 65535
        isVisible: false
        input: ""
        output: ""
        sampleInput: ""
        sampleOutput: ""
        hint: ""
        source: ""
      $scope.fieldInfo = []
      $scope.action = $routeParams.action

      if $scope.action != "new"
        $scope.title = "Edit problem " + $scope.action
        problemId = angular.copy($scope.action)
        $http.post("/problem/data/#{problemId}").then (response)->
          data = response.data
          if data.result == "success"
            $scope.problem = data.problem
          else
            $window.alert data.error_msg
      else
        $scope.title = "New problem"

      $scope.submit = ->
        problemEditDTO = angular.copy($scope.problem)
        problemEditDTO.action = angular.copy($scope.action)
        $http.post("/problem/edit", problemEditDTO).then (response)->
          data = response.data
          if data.result == "success"
            $window.location.href = "#/problem/show/#{data.problemId}"
          else if data.result == "field_error"
            # must be empty title
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
  ])