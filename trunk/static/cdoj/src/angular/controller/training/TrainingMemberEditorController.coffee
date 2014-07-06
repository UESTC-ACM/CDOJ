cdoj
.controller("TrainingMemberEditorController", [
    "$scope", "$http", "$modalInstance", "action", "trainingUserDto"
    ($scope, $http, $modalInstance, action, trainingUserDto) ->

      $scope.action = action
      $scope.trainingUserDto = trainingUserDto
      if $scope.action == "new"
        $scope.title = "Add new member"
      else
        $scope.title = "Edit"

      $scope.searchUser = (keyword) ->
        condition =
          keyword: keyword
        $http.post("/user/typeAheadList", condition).then ((response) ->
          data = response.data
          if data.result == "success"
            return data.list
          else
            $window.alert data.error_msg
        )

      $scope.add = ->
        trainingUserEditDto = angular.copy($scope.trainingUserDto)

      $scope.dismiss = ->
        $modalInstance.dismiss("close")
  ])