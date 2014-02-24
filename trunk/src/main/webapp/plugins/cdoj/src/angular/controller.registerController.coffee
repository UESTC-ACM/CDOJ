cdoj.controller("RegisterController", [
  "$scope", "$rootScope", "$http", "$element", "$window"
  ($scope, $rootScope, $http, $element, $window) ->
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
    $scope.fieldInfo = []
    $scope.register = ->
      userRegisterDTO = angular.copy($scope.userRegisterDTO)
      if userRegisterDTO.password == undefined then return
      if userRegisterDTO.passwordRepeat == undefined then return
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
          $element.modal("hide")
        else if data.result == "field_error"
          $scope.fieldInfo = data.field
        else
          $window.alert data.error_msg
])
