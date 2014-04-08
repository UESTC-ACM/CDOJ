cdoj
.controller("TeamEditorModalController", [
    "$scope", "$rootScope", "$http", "$window", "$modalInstance", "team"
    ($scope, $rootScope, $http, $window, $modalInstance, team) ->
      $scope.teamDTO =
        teamId: team.teamId
        teamName: team.teamName
        memberList: ""
      $scope.newMember =
        userName: ""
      $scope.memberList = [].concat(team.teamUsers, team.invitedUsers)

      $scope.searchUser = (keyword) ->
        condition =
          keyword: keyword
        $http.post("/user/typeAheadList", condition).then ((response) ->
          data = response.data
          if data.result == "success"
            return data.list
          else
            $window.alert data.error_msg
        )
      $scope.addMemberClick = ->
        if $scope.memberList.length < 3
          $http.get(
            "/user/typeAheadItem/" + $scope.newMember.userName
          ).success((data) ->
            if data.result == "success"
              result = data.user
              # Check if exists
              if _.where($scope.memberList, {userId: result.userId}).length > 0
                $window.alert "You can not add the same member twice"
              else
                $scope.addMember result
            else
              $window.alert data.error_msg
          ).error(->
            $window.alert "Network error."
          )
      $scope.addMember = (user) ->
        if $scope.memberList.length >= 3
          $window.alert "Member number should no more than 3."
        else if (
          $window.confirm "Are you sure that invite " + user.userName +
          " into team " + $scope.teamDTO.teamName + "?"
        )
          teamDTO = angular.copy($scope.teamDTO)
          teamDTO.memberList = "#{user.userId}"
          $http.post("/team/addMember", teamDTO).success((data) ->
            if data.result == "success"
              $scope.memberList.add user
              $rootScope.$broadcast("list:refresh:team")
            else
              $window.alert data.error_msg
          ).error(->
            $window.alert "Network error."
          )
      $scope.removeMember = (index) ->
        if index >= 1 && index < 3
          user = $scope.memberList[index]
          if (
            $window.confirm "Are you sure that remove " + user.userName +
            " from team " + $scope.teamDTO.teamName + "?"
          )
            teamDTO = angular.copy($scope.teamDTO)
            teamDTO.memberList = "#{user.userId}"
            $http.post("/team/removeMember", teamDTO).success((data) ->
              if data.result == "success"
                $scope.memberList.splice(index, 1)
                $rootScope.$broadcast("list:refresh:team")
              else
                $window.alert data.error_msg
            ).error(->
              $window.alert "Network error."
            )
      $scope.deleteTeam = ->
        if (
          $window.confirm "This action will delete team " +
          $scope.teamDTO.teamName + " forever, are you sure?"
        )
          teamDTO = angular.copy($scope.teamDTO)
          $http.post("/team/deleteTeam", teamDTO).success((data) ->
            if data.result == "success"
              $window.alert "Done!"
              $modalInstance.close()
              $rootScope.$broadcast("list:refresh:team")
            else
              $window.alert data.error_msg
          ).error(->
            $window.alert "Network error."
          )
      $scope.dismiss = ->
        $modalInstance.dismiss()
      $scope.$on("$routeChangeStart", ->
        $modalInstance.dismiss()
      )
  ])