cdoj.controller("UserCenterController", [
  "$scope", "$rootScope", "$http"
  ($scope, $rootScope, $http) ->
    $scope.targetUserName = ""
    $scope.targetUser =
      email: ""

    $scope.$watch("targetUserName", ->
      targetUserName = angular.copy($scope.targetUserName)
      $http.get("/user/userCenterData/#{targetUserName}").then (response)->
        data = response.data
        if data.result == "success"
          $scope.targetUser = data.targetUser
          $scope.problemStatus = data.problemStatus
        else
          $window.alert data.error_msg
    )
])