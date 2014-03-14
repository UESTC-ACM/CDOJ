cdoj
.controller("UserCenterController", [
    "$scope", "$rootScope", "$http", "$routeParams", "$modal", "$window"
    ($scope, $rootScope, $http, $routeParams, $modal, $window) ->
      $scope.targetUser =
        email: ""
      $scope.teamCondition = angular.copy($rootScope.teamCondition)
      $scope.messageCondition = angular.copy($rootScope.messageCondition)
      $scope.editPermission = false
      checkPermission = ->
        if $rootScope.hasLogin == false
          $scope.editPermission = false
        else if $rootScope.currentUser.userId == $scope.targetUser.userId
          $scope.editPermission = true
        $scope.messageCondition = angular.copy($rootScope.messageCondition)
        if $scope.editPermission == false
          $scope.messagesTabTitle = "Your messages with #{$scope.targetUser.userName}"
          $scope.messageCondition.userAId = $scope.currentUser.userId
          $scope.messageCondition.userBId = $scope.targetUser.userId
        else
          $scope.messagesTabTitle = "#{$scope.targetUser.userName}'s messages"
          $scope.messageCondition.userId = $scope.currentUser.userId

      currentTab = angular.copy($routeParams.tab)
      $scope.activeProblemsTab = false
      $scope.activeTeamsTab = false
      $scope.activeMessagesTab = false
      if currentTab == "teams"
        $scope.activeTeamsTab = true
      else if currentTab == "messages"
        $scope.activeMessagesTab = true
      else
        $scope.activeProblemsTab = true

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