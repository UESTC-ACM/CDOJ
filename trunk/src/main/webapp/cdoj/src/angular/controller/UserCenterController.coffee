cdoj
.controller("UserCenterController", [
    "$scope", "$rootScope", "$http", "$routeParams", "$modal", "$window"
    ($scope, $rootScope, $http, $routeParams, $modal, $window) ->
      $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.NOOP)
      $window.scrollTo(0, 0)
      $scope.targetUser =
        email: ""
      $scope.teamCondition = angular.copy($rootScope.teamCondition)
      $scope.messageCondition = angular.copy($rootScope.messageCondition)
      $scope.userEditDTO = undefined
      # FIXME(mzry1992) This will cause the message list refresh automatically!
      permissionChanged = ->
        if $rootScope.hasEditPermission == false
          $scope.messagesTabTitle = "Your messages with " + $scope.targetUser.userName
          $scope.messageCondition.userAId = $scope.currentUser.userId
          $scope.messageCondition.userBId = $scope.targetUser.userId
        else
          $scope.messagesTabTitle = $scope.targetUser.userName + "'s messages"
          $scope.messageCondition.userId = $scope.currentUser.userId
          if angular.isUndefined $scope.userEditDTO
            $http.get("/user/profile/#{$scope.targetUser.userName}").success((data)->
              if data.result == "success"
                $scope.userEditDTO = data.user
            )
      $scope.$on("permission:changed", ->
        permissionChanged()
      )
      $scope.$on("currentUser:changed", ->
        permissionChanged()
      )

      currentTab = angular.copy($routeParams.tab)
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

      targetUserName = angular.copy($routeParams.userName)
      $scope.$emit("permission:setEditPermission", targetUserName)
      $scope.$emit("permission:check")
      $http.get("/user/userCenterData/#{targetUserName}").success((data)->
        if data.result == "success"
          $scope.targetUser = data.targetUser
          $scope.problemStatus = data.problemStatus
          $scope.teamCondition.userId = data.targetUser.userId
        else
          $window.alert data.error_msg
      ).error(->
        $window.alert "Network error, please refresh page manually."
      )

      articleCondition = angular.copy($rootScope.articleCondition)
      articleCondition.userName = targetUserName
      $scope.articleCondition = articleCondition

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
        if angular.isUndefined userEditDTO.oldPassword
          $window.scrollTo(0, 0)
          return
        oldPassword = CryptoJS.SHA1(userEditDTO.oldPassword).toString()
        userEditDTO.oldPassword = oldPassword
        $http.post("/user/edit", userEditDTO).success((data)->
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
        $http.post("/team/createTeam", teamDTO).success((data)->
          if data.result == "success"
            $scope.$broadcast("list:refresh:team", (data)->
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
  ])