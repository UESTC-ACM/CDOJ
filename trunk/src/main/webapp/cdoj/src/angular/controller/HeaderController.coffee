cdoj
.controller("HeaderController", [
    "$scope", "$rootScope", "$http", "$element", "$compile", "$window", "UserProfile", "$modal"
    ($scope, $rootScope, $http, $element, $compile, $window, $userProfile, $modal) ->
      $rootScope.hasLogin = false
      $rootScope.currentUser =
        email: ""
      $rootScope.isAdmin = false
      $scope.userLoginDTO =
        userName: ""
        password: ""
      $scope.fieldInfo = []

      $scope.login = ->
        userLoginDTO = angular.copy($scope.userLoginDTO)
        if angular.isUndefined userLoginDTO.password then return
        password = CryptoJS.SHA1(userLoginDTO.password).toString()
        userLoginDTO.password = password
        $http.post("/user/login", userLoginDTO).then (response)->
          data = response.data
          if data.result == "success"
            $rootScope.hasLogin = true
            $rootScope.currentUser =
              userName: data.userName
              email: data.email
              type: data.type
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg

      $scope.logout = ->
        $http.post("/user/logout").then (response)->
          data = response.data
          if data.result == "success"
            $rootScope.hasLogin = false
            $rootScope.currentUser =
              email: ""

      $scope.openRegisterModal = ->
        registerModal = $modal.open(
          templateUrl: "template/modal/register-modal.html"
          controller: "RegisterModalController"
        )

      $scope.openForgetPasswordModal = ->
        forgetPasswordModal = $modal.open(
          templateUrl: "template/modal/forget-password-modal.html"
          controller: "ForgetPasswordModalController"
        )
      $scope.openUserProfileEditor = ->
        $userProfile.setProfile $rootScope.currentUser.userName
        userProfileEditor = $modal.open(
          templateUrl: "template/modal/profile-edit-modal.html"
          controller: "UserProfileEditorController"
        )

      $scope.readMessage = (message)->
        console.log message
  ])