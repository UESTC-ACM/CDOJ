cdoj
.controller("HeaderController", [
    "$scope", "$rootScope", "$http", "$element", "$compile", "$window", "$modal"
    ($scope, $rootScope, $http, $element, $compile, $window, $modal) ->
      $scope.userLoginDTO =
        userName: ""
        password: ""
      $scope.fieldInfo = []

      $scope.login = ->
        # Force update form
        $scope.userLoginDTO.userName = $("#userName").val()
        $scope.userLoginDTO.password = $("#password").val()
        userLoginDTO = angular.copy($scope.userLoginDTO)
        password = CryptoJS.SHA1(userLoginDTO.password).toString()
        userLoginDTO.password = password
        $http.post("/user/login", userLoginDTO).success((data)->
          if data.result == "success"
            $rootScope.$broadcast("currentUser:login",
              userName: data.userName
              email: data.email
              type: data.type
            )
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error"
        )

      $scope.logout = ->
        $http.post("/user/logout").success((data)->
          if data.result == "success"
            $rootScope.$broadcast("currentUser:logout")
        ).error(->
          $window.alert "Network error"
        )

      $scope.openForgetPasswordModal = ->
        $modal.open(
          templateUrl: "template/modal/forget-password-modal.html"
          controller: "ForgetPasswordModalController"
        )
  ])