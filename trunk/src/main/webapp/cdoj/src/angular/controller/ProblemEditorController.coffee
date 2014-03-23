cdoj
.controller("ProblemEditorController", [
    "$scope", "$http", "$window", "$routeParams", "$rootScope"
    ($scope, $http, $window, $routeParams, $rootScope)->
      # Administrator only
      $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.ADMIN)
      $window.scrollTo(0, 0)

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
      $scope.samples = []

      $scope.addSample = ->
        $scope.samples.add(
          input: ""
          output: ""
        )
      $scope.removeSample = (index)->
        $scope.samples.splice(index, 1);

      if $scope.action != "new"
        $scope.title = "Edit problem " + $scope.action
        problemId = angular.copy($scope.action)
        $http.post("/problem/data/#{problemId}").success((data)->
          if data.result == "success"
            $scope.problem = data.problem

            _sampleInput = $scope.problem.sampleInput
            try
              _sampleInput = JSON.parse _sampleInput

            if _sampleInput not instanceof Array
              _sampleInput = [_sampleInput]

            _sampleOutput = $scope.problem.sampleOutput
            try
              _sampleOutput = JSON.parse _sampleOutput

            if _sampleOutput not instanceof Array
              _sampleOutput = [_sampleOutput]

            if _sampleInput.length != _sampleOutput.length
              alert "Sample input has not same number of cases with sample output!"
            else
              $scope.samples = ({input: _sampleInput[i].toString(), output: _sampleOutput[i].toString()} for i in [0.._sampleInput.length - 1])

          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error, please refresh page manually."
        )
      else
        $scope.title = "New problem"
        $scope.addSample()

      $scope.submit = ->
        problemEditDTO = angular.copy($scope.problem)
        problemEditDTO.action = angular.copy($scope.action)
        problemEditDTO.sampleInput = JSON.stringify(_.map($scope.samples, (sample)->
          sample.input))
        problemEditDTO.sampleOutput = JSON.stringify(_.map($scope.samples, (sample)->
          sample.output))

        $http.post("/problem/edit", problemEditDTO).success((data)->
          if data.result == "success"
            $window.location.href = "#/problem/show/#{data.problemId}"
          else if data.result == "field_error"
            # must be empty title
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
  ])