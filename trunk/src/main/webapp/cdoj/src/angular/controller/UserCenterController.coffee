cdoj
.controller("UserCenterController", [
    "$scope", "$rootScope", "$http", "$routeParams"
    ($scope, $rootScope, $http, $routeParams) ->
      $scope.targetUser =
        email: ""

      targetUserName = angular.copy($routeParams.userName)
      $http.get("/user/userCenterData/#{targetUserName}").then (response)->
        data = response.data
        if data.result == "success"
          $scope.targetUser = data.targetUser
          $scope.problemStatus = data.problemStatus
        else
          $window.alert data.error_msg
  ])