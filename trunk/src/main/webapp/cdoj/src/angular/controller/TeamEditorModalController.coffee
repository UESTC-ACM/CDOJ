cdoj
.controller("TeamEditorModalController", [
    "$scope", "$rootScope", "$http", "$window", "$modalInstance", "team"
    ($scope, $rootScope, $http, $window, $modalInstance, team)->
      $scope.teamDTO =
        teamId: team.teamId
        teamName: team.teamName
        memberList: ""
      $scope.newMember =
        userName: ""
      $scope.memberList = [].concat(team.teamUsers, team.invitedUsers)
      console.log $scope.memberList

      $scope.searchUser = (keyword)->
        condition =
          keyword: keyword
        $http.post("/user/typeAheadList", condition).then (response)->
          data = response.data
          if data.result == "success"
            return data.list
          else
            $window.alert data.error_msg
      $scope.addMemberClick = ->
        if $scope.memberList.length < 3
          $scope.addMember($scope.newMember.userName)
      $scope.addMember = (userName)->
        $http.get("/user/typeAheadItem/#{userName}").then (response)->
          data = response.data
          if data.result == "success"
            result = data.user
            # Check if exists
            if _.where($scope.memberList, {userId: result.userId}).length > 0
              $window.alert "You can not add the same member twice"
            else
              $scope.memberList.add result
          else
            $window.alert data.error_msg
      $scope.removeMember = (index)->
        if index >= 1 && index < 3
          $scope.memberList.splice(index, 1);

      $scope.editTeam = ->
        if $scope.memberList.length < 1 || $scope.memberList.length > 3
          $window.alert "Member number should between 1 and 3."
        else
          teamDTO = angular.copy($scope.teamDTO)
          teamDTO.memberList = _.map($scope.memberList,(val) ->
            return val.userId
          ).join(",")
          $http.post("/team/editTeam", teamDTO).then (response)->
            data = response.data
            if data.result == "success"
              $modalInstance.close()
            else
              $window.alert data.error_msg
      $scope.deleteTeam = ->
        if $window.confirm ("This action will delete team #{$scope.teamDTO.teamName} forever, are you sure?")
          teamDTO = angular.copy($scope.teamDTO)
          $http.post("/team/deleteTeam", teamDTO).then (response)->
            data = response.data
            if data.result == "success"
              $window.alert "Done!"
              $modalInstance.close()
              $rootScope.$broadcast("refreshList")
            else
              $window.alert data.error_msg

      $scope.dismiss = ->
        $modalInstance.dismiss()
  ])