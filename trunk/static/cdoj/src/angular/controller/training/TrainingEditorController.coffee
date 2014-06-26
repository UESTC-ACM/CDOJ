cdoj
.controller("TrainingEditorController", [
    "$scope", "$http", "$rootScope", "$window", "training"
    ($scope, $http, $rootScope, $window, training) ->
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.ADMIN
      )
      $window.scrollTo(0, 0)

      $scope.training = training
      $scope.fieldInfo = []
      $scope.action = training.action

      if $scope.action != "new"
        $scope.title = "Edit training " + $scope.action
      else
        $scope.title = "New training"

      $scope.submit = ->
        trainingEditDto = angular.copy($scope.training)

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