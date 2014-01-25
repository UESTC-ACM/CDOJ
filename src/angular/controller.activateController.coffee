cdoj.controller("ActivateController", [
  "$scope", "$http", "$element"
  ($scope, $http, $element) ->
    $scope.userName = ""
    $scope.buttonText = "Send Email"
    $scope.onSend = false
    $scope.sendSerialKey = ->
      if $scope.userName.length > 0
        $scope.buttonText = "Sending..."
        $scope.onSend = true
        userName = angular.copy($scope.userName)
        $http.post("/user/sendSerialKey/#{userName}").then (response)->
          data = response.data
          if data.result == "success"
            alert "We send you an Email with the url to reset your password right now, please check your mail box."
            $element.modal("hide");
          else if data.result == "failed"
            alert "Unknown error occurred."
          else
            alert data.error_msg;
          $scope.buttonText = "Send Email"
          $scope.onSend = false
])
