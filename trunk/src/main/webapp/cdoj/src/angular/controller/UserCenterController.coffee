cdoj
.controller("UserCenterController", [
    "$scope", "$rootScope", "$http", "$routeParams", "$modal", "$window", "UserProfile"
    ($scope, $rootScope, $http, $routeParams, $modal, $window, $userProfile) ->
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
        $scope.$broadcast("userCenter:permissionChange")
      $scope.$on("refresh", ->
        $window.location.reload()
      )

      currentTab = angular.copy($routeParams.tab)
      $scope.activeProblemsTab = false
      $scope.activeTeamsTab = false
      $scope.activeMessagesTab = false
      $scope.activeEditTab = false
      if currentTab == "teams"
        $scope.activeTeamsTab = true
      else if currentTab == "messages"
        $scope.activeMessagesTab = true
      else if currentTab == "edit"
        $scope.activeEditTab = true
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

      $scope.userEditDTO = 0
      $scope.$on("userCenter:permissionChange", ->
        if $scope.editPermission
          $userProfile.setProfile $scope.targetUser.userName
          $scope.$on("UserProfile:success", ->
            $scope.userEditDTO = $userProfile.getProfile()
          )
      )
      $scope.fieldInfo = []
      $scope.edit = ->
        userEditDTO = angular.copy($scope.userEditDTO)
        userEditDTO.newPassword = undefined if userEditDTO.newPassword == ""
        userEditDTO.newPasswordRepeat = undefined if userEditDTO.newPasswordRepeat == ""
        if angular.isUndefined userEditDTO.newPassword && angular.isUndefined userEditDTO.newPasswordRepeat
          userEditDTO = _.omit(userEditDTO, "newPassword")
          userEditDTO = _.omit(userEditDTO, "newPassowrdRepeat")
        else
          newPassword = CryptoJS.SHA1(userEditDTO.newPassword).toString()
          userEditDTO.newPassword = newPassword
          newPasswordRepeat = CryptoJS.SHA1(userEditDTO.newPasswordRepeat).toString()
          userEditDTO.newPasswordRepeat = newPasswordRepeat
        if angular.isUndefined userEditDTO.oldPassword then return
        oldPassword = CryptoJS.SHA1(userEditDTO.oldPassword).toString()
        userEditDTO.oldPassword = oldPassword
        $http.post("/user/edit", userEditDTO).then (response)->
          data = response.data
          if data.result == "success"
            $window.alert "Success!"
            $scope.$broadcast("refresh")
          else if data.result == "field_error"
            $window.scrollTo(0,0)
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg

      $scope.newTeam =
        teamName: ""
      $scope.createNewTeam = ->
        teamDTO = angular.copy($scope.newTeam)
        $http.post("/team/createTeam", teamDTO).then (response)->
          data = response.data
          if data.result == "success"
            $scope.$broadcast("refreshList")
          else
            $window.alert data.error_msg
  ])