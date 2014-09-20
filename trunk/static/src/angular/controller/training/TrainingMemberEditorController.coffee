cdoj
.controller("TrainingMemberEditorController", [
    "$scope", "$http", "$modalInstance", "action", "trainingUserData", "$window"
    ($scope, $http, $modalInstance, action, trainingUserData, $window) ->

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

      $scope.updatePlatform = (platform) ->
        editTrainingPlatform(
          action: "edit"
          trainingPlatformInfoEditDto: platform
          (data) ->
            platform = data.trainingPlatformInfoDto
        )

      $scope.removePlatform = (platform) ->
        if $window.confirm("Are you sure?")
          editTrainingPlatform(
            action: "remove"
            trainingPlatformInfoEditDto: platform
            (data) ->
              $scope.trainingPlatformList =
                _.reject(
                  $scope.trainingPlatformList,
                  (value) ->
                    a = value.trainingPlatformInfoId
                    b = platform.trainingPlatformInfoId
                    return a == b
                )
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