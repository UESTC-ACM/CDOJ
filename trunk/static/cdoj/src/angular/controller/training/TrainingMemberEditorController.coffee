cdoj
.controller("TrainingMemberEditorController", [
    "$scope", "$http", "$modalInstance", "action", "trainingUserData"
    ($scope, $http, $modalInstance, action, trainingUserData) ->

      $scope.action = action
      $scope.trainingUserDto = trainingUserData.trainingUserDto
      $scope.trainingPlatformList = trainingUserData.trainingPlatformInfoDtoList
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

      editTrainingPlatform = (postData, onSuccess) ->
        $http.post("/training/editTrainingPlatformInfo",
          postData
        ).success((data) ->
          if data.result == "success"
            onSuccess(data)
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )

      $scope.addPlatform = ->
        editTrainingPlatform(
          action: "new"
          trainingPlatformInfoEditDto:
            trainingUserId: $scope.trainingUserDto.trainingUserId
          (data) ->
            $scope.trainingPlatformList.add(data.trainingPlatformInfoDto)
        )

      editTrainingUser = (postData) ->
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