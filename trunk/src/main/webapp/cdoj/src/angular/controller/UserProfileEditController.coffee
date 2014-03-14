cdoj
.controller("UserProfileEditorController", [
    "$scope", "$http", "$modalInstance", "$window", "UserProfile"
    ($scope, $http, $modalInstance, $window, $userProfile)->
      $scope.userEditDTO = 0
      $scope.$watch(
        ->
          $userProfile.getProfile()
      ,
      ->
        $scope.userEditDTO = $userProfile.getProfile()
      , true)
      $scope.fieldInfo = []
      $scope.edit = ->
        userEditDTO = angular.copy($scope.userEditDTO)

        userEditDTO.newPassword = undefined if userEditDTO.newPassword == ""
        userEditDTO.newPasswordRepeat = undefined if userEditDTO.newPasswordRepeat == ""
        if angular.isUndefined userEditDTO.newPassword && angular.isUndefined userEditDTO.newPasswordRepeat
          userEditDTO = _.omit(userEditDTO, "newPassword")
          userEditDTO = _.omit(userEditDTO, "newPassowrdRepeat")
        else
          newPassword = CryptoJS.SHA1(userEditDTO.newPassword).toString()
          userEditDTO.newPassword = newPassword
          newPasswordRepeat = CryptoJS.SHA1(userEditDTO.newPasswordRepeat).toString()
          userEditDTO.newPasswordRepeat = newPasswordRepeat
        if angular.isUndefined userEditDTO.oldPassword then return
        oldPassword = CryptoJS.SHA1(userEditDTO.oldPassword).toString()
        userEditDTO.oldPassword = oldPassword
        $http.post("/user/edit", userEditDTO).then (response)->
          data = response.data
          if data.result == "success"
            $window.alert "Success!"
            $modalInstance.close()
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
      $scope.dismiss = ->
        $modalInstance.dismiss()
  ])