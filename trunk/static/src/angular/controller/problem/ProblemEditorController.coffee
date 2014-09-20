cdoj
.controller("ProblemEditorController", [
    "$scope", "$http", "$window", "$rootScope", "problem"
    ($scope, $http, $window, $rootScope, problem) ->
      # Administrator only
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.ADMIN
      )
      $window.scrollTo(0, 0)

      $scope.problem = problem
      $scope.fieldInfo = []
      $scope.action = problem.action
      $scope.samples = []

      $scope.addSample = ->
        $scope.samples.add(
          input: ""
          output: ""
        )
      $scope.removeSample = (index) ->
        $scope.samples.splice(index, 1)

      if $scope.action != "new"
        $scope.title = "Edit problem " + $scope.action
        $scope.samples = $scope.problem.samples
      else
        $scope.title = "New problem"
        $scope.addSample()

      $scope.submit = ->
        problemEditDTO = angular.copy($scope.problem)
        problemEditDTO.action = angular.copy($scope.action)
        problemEditDTO.sampleInput = JSON.stringify(
          _.map(
            $scope.samples
            (sample) ->
              sample.input
          )
        )
        problemEditDTO.sampleOutput = JSON.stringify(
          _.map(
            $scope.samples
            (sample) ->
              sample.output
          )
        )

        $http.post("/problem/edit", problemEditDTO).success((data) ->
          if data.result == "success"
            $window.location.href = "/#/problem/show/" + data.problemId
          else if data.result == "field_error"
            # must be empty title
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
  ])