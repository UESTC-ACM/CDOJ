cdoj.controller("UserProfileEditController", [
  "$scope", "$http", "$element", "$window", "UserProfile"
  ($scope, $http, $element, $window, $userProfile)->
    $scope.userEditDTO = 0
    $scope.$watch(
      ->
        $userProfile.getProfile()
    ,
      ->
        $scope.userEditDTO = $userProfile.getProfile()
    , true)
    $scope.edit = ->
      userEditDTO = angular.copy($scope.userEditDTO)

      userEditDTO.newPassword = undefined if userEditDTO.newPassword == ""
      userEditDTO.newPasswordRepeat = undefined if userEditDTO.newPasswordRepeat == ""
      if userEditDTO.newPassword == undefined && userEditDTO.newPasswordRepeat == undefined
        userEditDTO = _.omit(userEditDTO, "newPassword")
        userEditDTO = _.omit(userEditDTO, "newPassowrdRepeat")
      else
        newPassword = CryptoJS.SHA1(userEditDTO.newPassword).toString()
        userEditDTO.newPassword = newPassword
        newPasswordRepeat = CryptoJS.SHA1(userEditDTO.newPasswordRepeat).toString()
        userEditDTO.newPasswordRepeat = newPasswordRepeat
      if userEditDTO.oldPassword == undefined then return
      oldPassword = CryptoJS.SHA1(userEditDTO.oldPassword).toString()
      userEditDTO.oldPassword = oldPassword
      $http.post("/user/edit", userEditDTO).then (response)->
        data = response.data
        if data.result == "success"
          $element.modal("hide")
        else if data.result == "field_error"
          $scope.fieldInfo = data.field
        else
          $window.alert data.error_msg
])