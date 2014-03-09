cdoj
.controller("UserCenterController", [
    "$scope", "$rootScope", "$http", "$routeParams", "$modal"
    ($scope, $rootScope, $http, $routeParams, $modal) ->
      $scope.targetUser =
        email: ""
      $scope.teamCondition = angular.copy($rootScope.teamCondition)
      $scope.editPermission = false
      checkPermission = ->
        if $rootScope.hasLogin == false
          $scope.editPermission = false
        else if $rootScope.currentUser.userId == $scope.targetUser.userId
          $scope.editPermission = true
      $rootScope.$watch("hasLogin", ->
        checkPermission()
      )

      targetUserName = angular.copy($routeParams.userName)
      $http.get("/user/userCenterData/#{targetUserName}").then (response)->
        data = response.data
        if data.result == "success"
          $scope.targetUser = data.targetUser
          $scope.problemStatus = data.problemStatus
          $scope.teamCondition.userId = data.targetUser.userId
          checkPermission()
        else
          $window.alert data.error_msg

      $scope.showTeamEditor = ->
        teamEditor = $modal.open(
          templateUrl: "template/modal/team-editor-modal.html"
          controller: "TeamEditorModalController"
        )
  ])