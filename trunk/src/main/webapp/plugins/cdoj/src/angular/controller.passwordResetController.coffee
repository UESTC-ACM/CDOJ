cdoj.controller("PasswordResetController",[
  "$scope", "$http", "$window"
  ($scope, $http, $window) ->
    $scope.userActivateDTO =
      userName: ""
      serialKey: ""
      password: ""
      passwordRepeat: ""
    $scope.submit = ->
      userActivateDTO = angular.copy($scope.userActivateDTO)
      password = CryptoJS.SHA1(userActivateDTO.password).toString()
      userActivateDTO.password = password
      passwordRepeat = CryptoJS.SHA1(userActivateDTO.passwordRepeat).toString()
      userActivateDTO.passwordRepeat = passwordRepeat
      $http.post("/user/resetPassword", userActivateDTO).then (response)->
        data = response.data
        if data.result == "success"
          $window.alert("Success!")
          $window.location.href = "/"
        else if data.result == "field_error"
          $scope.fieldInfo = data.field
        else
          $window.alert data.error_msg
])