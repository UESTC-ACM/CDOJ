cdoj
.controller("ForgetPasswordModalController", [
    "$scope", "$http", "$modalInstance", "$window"
    ($scope, $http, $modalInstance, $window) ->
      $scope.DTO =
        userName: ""
      $scope.userName = ""
      $scope.buttonText = "Send Email"
      $scope.onSend = false
      $scope.sendSerialKey = ->
        userName = angular.copy($scope.DTO.userName)
        if userName.length > 0
          $scope.buttonText = "Sending..."
          $scope.onSend = true
          $http.post("/user/sendSerialKey/#{userName}").success((data)->
            if data.result == "success"
              $window.alert "We send you an Email with the url to reset your password right now, please check your mail box."
              $modalInstance.close();
            else if data.result == "failed"
              $window.alert "Unknown error occurred."
            else
              $window.alert data.error_msg;
            $scope.buttonText = "Send Email"
            $scope.onSend = false
          ).error(->
            $window.alert "Network error."
          )
        else
          $window.alert "Please input your user name!"

      $scope.dismiss = ->
        $modalInstance.dismiss()
      $scope.$on("$routeChangeStart", ->
        $modalInstance.dismiss()
      )
  ])
