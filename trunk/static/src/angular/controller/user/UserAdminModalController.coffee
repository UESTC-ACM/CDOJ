cdoj
.controller("UserAdminModalController", [
    "$scope", "$http", "$modalInstance", "UserProfile", "$window"
    ($scope, $http, $modalInstance, $userProfile, $window) ->
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
        if userEditDTO.newPassword == ""
          userEditDTO.newPassword = undefined
        if userEditDTO.newPasswordRepeat == ""
          userEditDTO.newPasswordRepeat = undefined
        if (
          angular.isUndefined userEditDTO.newPassword &&
            angular.isUndefined userEditDTO.newPasswordRepeat
        )
          userEditDTO = _.omit(userEditDTO, "newPassword")
          userEditDTO = _.omit(userEditDTO, "newPassowrdRepeat")
        else
          newPassword =
            CryptoJS.SHA1(userEditDTO.newPassword).toString()
          userEditDTO.newPassword = newPassword
          newPasswordRepeat =
            CryptoJS.SHA1(userEditDTO.newPasswordRepeat).toString()
          userEditDTO.newPasswordRepeat = newPasswordRepeat

        $http.post("/user/adminEdit", userEditDTO).success((data) ->
          if data.result == "success"
            $window.alert "Success!"
            $modalInstance.close()
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
      $scope.dismiss = ->
        $modalInstance.dismiss("close")
      $scope.$on("$routeChangeStart", ->
        $modalInstance.dismiss()
      )
  ])