cdoj
.controller("TrainingEditorController", [
    "$scope", "$http", "$rootScope", "$window", "trainingDto"
    ($scope, $http, $rootScope, $window, trainingDto) ->
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.ADMIN
      )
      $window.scrollTo(0, 0)

      $scope.trainingDto = trainingDto
      $scope.fieldInfo = []
      $scope.action = trainingDto.action

      if $scope.action != "new"
        $scope.title = "Edit training " + $scope.action
      else
        $scope.title = "New training"

      $scope.submit = ->
        trainingEditDto = angular.copy($scope.trainingDto)

        $http.post("/training/edit",
          action: angular.copy($scope.action)
          trainingEditDto: trainingEditDto
        ).success((data) ->
          if data.result == "success"
            $window.location.href = "/#/training/show/#{data.trainingId}"
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )

  ])