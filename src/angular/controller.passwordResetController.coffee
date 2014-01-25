cdoj.controller("PasswordResetController",[
  "$scope", "$http",
  ($scope, $http) ->
    $scope.userActivateDTO =
      userName: ""
      serialKey: ""
      password: ""
      passwordRepeat: ""
    $scope.submit = ->
      password = CryptoJS.SHA1($scope.userActivateDTO.password).toString()
      $scope.userActivateDTO.password = password
      passwordRepeat = CryptoJS.SHA1($scope.userActivateDTO.passwordRepeat).toString()
      $scope.userActivateDTO.passwordRepeat = passwordRepeat
      $http.post("/user/resetPassword", $scope.userActivateDTO).then (response)->
        data = response.data
        if data.result == "success"
          alert("Success!")
          window.location.href = "/"
        else if data.result == "field_error"
          $scope.fieldInfo = data.field
        else
          alert data.error_msg
])