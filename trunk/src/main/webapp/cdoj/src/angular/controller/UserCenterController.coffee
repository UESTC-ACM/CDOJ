cdoj
.controller("UserCenterController", [
    "$scope", "$rootScope", "$http", "$routeParams", "$modal"
    ($scope, $rootScope, $http, $routeParams, $modal) ->
      $scope.targetUser =
        email: ""
      $scope.teamCondition = angular.copy($rootScope.teamCondition)

      targetUserName = angular.copy($routeParams.userName)
      $http.get("/user/userCenterData/#{targetUserName}").then (response)->
        data = response.data
        if data.result == "success"
          $scope.targetUser = data.targetUser
          $scope.problemStatus = data.problemStatus
          $scope.teamCondition.userId = data.targetUser.userId
        else
          $window.alert data.error_msg

      $scope.showTeamEditor = ->
        teamEditor = $modal.open(
          templateUrl: "template/modal/team-editor-modal.html"
          controller: "TeamEditorModalController"
        )
  ])