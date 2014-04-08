cdoj
.controller("PasswordResetController", [
    "$scope", "$http", "$window", "$routeParams", "$rootScope"
    ($scope, $http, $window, $routeParams, $rootScope) ->
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.NOOP
      )
      $window.scrollTo(0, 0)

      $scope.userActivateDTO =
        userName: $routeParams.userName
        serialKey: $routeParams.serialKey
        password: ""
        passwordRepeat: ""
      $scope.filedInfo = []
      $scope.submit = ->
        userActivateDTO = angular.copy($scope.userActivateDTO)
        password =
          CryptoJS.SHA1(userActivateDTO.password).toString()
        userActivateDTO.password = password
        passwordRepeat =
          CryptoJS.SHA1(userActivateDTO.passwordRepeat).toString()
        userActivateDTO.passwordRepeat = passwordRepeat
        $http.post("/user/resetPassword", userActivateDTO).success((data) ->
          if data.result == "success"
            $window.alert("Success, please login manually!")
            $window.location.href = "/"
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
  ])