cdoj
.controller("RegisterModalController", [
    "$scope", "$rootScope", "$http", "$modalInstance", "$window"
    ($scope, $rootScope, $http, $modalInstance, $window) ->
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
        if angular.isUndefined userRegisterDTO.password then return
        if angular.isUndefined userRegisterDTO.passwordRepeat then return
        password = CryptoJS.SHA1(userRegisterDTO.password).toString()
        userRegisterDTO.password = password
        passwordRepeat = CryptoJS.SHA1(userRegisterDTO.passwordRepeat).toString()
        userRegisterDTO.passwordRepeat = passwordRepeat
        $http.post("/user/register", userRegisterDTO).then (response)->
          data = response.data
          if data.result == "success"
            $rootScope.hasLogin = true
            $rootScope.currentUser =
              userName: data.userName
              email: data.email
              type: data.type
            $modalInstance.close()
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
      $scope.dismiss = ->
        $modalInstance.dismiss()
  ])
