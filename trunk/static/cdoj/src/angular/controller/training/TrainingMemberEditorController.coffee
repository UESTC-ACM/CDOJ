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
      $scope.fieldInfo = []

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

      editTrainingUser = (postData) ->
        console.log postData
        $http.post("/training/editTrainingUser", postData).success((data) ->
          if data.result == "success"
            $modalInstance.close()
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )

      $scope.new = ->
        trainingUserEditDto = angular.copy($scope.trainingUserDto)
        editTrainingUser(
          action: "new"
          trainingUserEditDto: trainingUserEditDto
        )

      $scope.edit = ->
        trainingUserEditDto = angular.copy($scope.trainingUserDto)
        editTrainingUser(
          action: "edit"
          trainingUserEditDto: trainingUserEditDto
        )

      $scope.dismiss = ->
        $modalInstance.dismiss("close")
  ])