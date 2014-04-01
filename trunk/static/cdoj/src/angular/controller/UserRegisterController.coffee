cdoj
.controller("UserRegisterController", [
    "$scope", "$rootScope", "$http", "$window"
    ($scope, $rootScope, $http, $window) ->
      $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.NOOP)
      $window.scrollTo(0, 0)
      $scope.userRegisterDTO =
        departmentId: 1
        email: ""
        motto: ""
        nickName: ""
        password: ""
        passwordRepeat: ""
        school: ""
        studentId: ""
        userName: ""
        sex: 0
        size: 2
        phone: ""
        grade: 3
        name: ""

      $scope.fieldInfo = []
      $scope.register = ->
        userRegisterDTO = angular.copy($scope.userRegisterDTO)
        if angular.isUndefined userRegisterDTO.password || angular.isUndefined userRegisterDTO.passwordRepeat
          $window.scrollTo(0, 0)
          return
        password = CryptoJS.SHA1(userRegisterDTO.password).toString()
        userRegisterDTO.password = password
        passwordRepeat = CryptoJS.SHA1(userRegisterDTO.passwordRepeat).toString()
        userRegisterDTO.passwordRepeat = passwordRepeat
        $http.post("/user/register", userRegisterDTO).success((data)->
          if data.result == "success"
            $rootScope.hasLogin = true
            $rootScope.currentUser =
              userName: data.userName
              email: data.email
              type: data.type
            $rootScope.$broadcast("data:refresh")
            $window.history.back();
          else if data.result == "field_error"
            $window.scrollTo(0, 0)
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
  ])
