cdoj
.controller("UserCenterController", [
    "$scope", "$rootScope", "$http", "$routeParams", "$modal", "$window",
    "$location", "targetUser", "userEditDTO"
    ($scope, $rootScope, $http, $routeParams, $modal, $window, $location,
     targetUser, userEditDTO) ->
      targetUserName = angular.copy($routeParams.userName)
      currentTab = angular.copy($routeParams.tab)
      if angular.isUndefined currentTab
        $location.path("/user/center/" + targetUserName + "/problems")

      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.NOOP
      )
      $window.scrollTo(0, 0)

      $scope.targetUser =
        email: ""
      $scope.teamCondition = angular.copy($rootScope.teamCondition)
      $scope.messageCondition = angular.copy($rootScope.messageCondition)
      $scope.userEditDTO = userEditDTO
      permissionChanged = ->
        if $rootScope.hasEditPermission == false
          $scope.messagesTabTitle =
              "Your messages with " + $scope.targetUser.userName
          $scope.messageCondition.userAId = $scope.currentUser.userId
          $scope.messageCondition.userBId = $scope.targetUser.userId
        else
          $scope.messagesTabTitle = $scope.targetUser.userName + "'s messages"
          $scope.messageCondition.userId = $scope.currentUser.userId
      $scope.$on("permission:changed", ->
        permissionChanged()
      )
      $scope.$on("currentUser:changed", ->
        permissionChanged()
      )

      $scope.activeProblemsTab = false
      $scope.activeTeamsTab = false
      $scope.activeMessagesTab = false
      $scope.activeEditTab = false
      $scope.activeBlogTab = false
      if currentTab == "teams"
        $scope.activeTeamsTab = true
      else if currentTab == "messages"
        $scope.activeMessagesTab = true
      else if currentTab == "edit"
        $scope.activeEditTab = true
      else if currentTab == "blog"
        $scope.activeBlogTab = true
      else
        $scope.activeProblemsTab = true

      $scope.selectBlogTab = ->
        $scope.$broadcast("list:refresh:article")
      $scope.selectTeamTab = ->
        $scope.$broadcast("list:refresh:team")
      $scope.selectMessagesTab = ->
        $scope.$broadcast("list:refresh:message")


      $scope.$emit("permission:setEditPermission", targetUserName)
      $scope.$emit("permission:check")
      $scope.targetUser = targetUser.targetUser
      $scope.problemStatus = targetUser.problemStatus
      $scope.teamCondition.userId = targetUser.targetUser.userId

      articleCondition = angular.copy($rootScope.articleCondition)
      articleCondition.userName = targetUserName
      $scope.articleCondition = articleCondition

      $scope.fieldInfo = []
      $scope.edit = ->
        if ($rootScope.currentUser.hasLogin == false ||
            $rootScope.currentUser.type ==
              $rootScope.AuthenticationType.CONSTANT
        )
          $window.alert("Permission denied!")
          return
        userEditDTO = angular.copy($scope.userEditDTO)
        if userEditDTO.newPassword == ""
          userEditDTO.newPassword = undefined
        if userEditDTO.newPasswordRepeat == ""
          userEditDTO.newPasswordRepeat = undefined
        if (
          angular.isUndefined userEditDTO.newPassword &&
            angular.isUndefined userEditDTO.newPasswordRepeat
        )
          userEditDTO = _.omit(userEditDTO, "newPassword")
          userEditDTO = _.omit(userEditDTO, "newPassowrdRepeat")
        else
          newPassword = CryptoJS.SHA1(userEditDTO.newPassword).toString()
          userEditDTO.newPassword = newPassword
          newPasswordRepeat =
            CryptoJS.SHA1(userEditDTO.newPasswordRepeat).toString()
          userEditDTO.newPasswordRepeat = newPasswordRepeat
        if angular.isUndefined userEditDTO.oldPassword
          $window.scrollTo(0, 0)
          return
        oldPassword = CryptoJS.SHA1(userEditDTO.oldPassword).toString()
        userEditDTO.oldPassword = oldPassword
        $http.post("/user/edit", userEditDTO).success((data) ->
          if data.result == "success"
            $window.alert "Success!"
            $window.location.href = "/#/"
          else if data.result == "field_error"
            $window.scrollTo(0, 0)
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )

      $scope.newTeam =
        teamName: ""
      $scope.createNewTeam = ->
        teamDTO = angular.copy($scope.newTeam)
        $http.post("/team/createTeam", teamDTO).success((data) ->
          if data.result == "success"
            $scope.$broadcast("list:refresh:team", (data) ->
              $modal.open(
                templateUrl: "template/modal/team-editor-modal.html"
                controller: "TeamEditorModalController"
                resolve:
                  team: ->
                    data.list[0]
              )
            )
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
      $scope.selectEditTab = ->
        $http.get(
            "/user/profile/" + $scope.targetUser.userName
        ).success((data) ->
          if data.result == "success"
            $scope.userEditDTO  = data.user
          else
            $window.alert(data.error_msg)
        ).error(->
          $window.alert("Network error!")
        )
  ])