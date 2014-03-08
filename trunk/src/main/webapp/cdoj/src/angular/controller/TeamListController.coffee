cdoj
.controller("TeamListController", [
    "$scope", "$rootScope", "$http", "$window"
    ($scope, $rootScope, $http, $window)->
      $scope.hideMemberPanel = true
      $scope.teamDTO =
        teamName: ""
        memberList: ""
      $scope.newMember = ""
      $scope.memberList = []

      $scope.toggleMemberPanel = ->
        if $rootScope.hasLogin == false
          $window.alert "Please login first!"
        else
          teamName = angular.copy($scope.teamDTO.teamName)
          if teamName == ""
            $window.alert "Please enter a valid team name."
          else if $scope.hideMemberPanel
            $scope.addMember($rootScope.currentUser.userName)
            $scope.hideMemberPanel = false

      $scope.searchUser = (keyword)->
        condition =
          keyword: keyword
        $http.post("/user/typeAheadSearch", condition).then (response)->
          data = response.data
          if data.result == "success"
            return data.list
          else
            $window.alert data.error_msg

      $scope.addMemberClick = ->
        if $scope.memberList.length < 3
          $scope.addMember($scope.newMember)

      $scope.addMember = (userName)->
        condition =
          userName: userName
        $http.post("/user/typeAheadSearch", condition).then (response)->
          data = response.data
          if data.result == "success"
            result = data.list
            if result.size == 0 || result[0].userName != userName
              $window.alert "No such user!"
            else
              # Check if exists
              if _.where($scope.memberList, {userId: result[0].userId}).length > 0
                $window.alert "You can not add the same member twice"
              else
                $scope.memberList.add result[0]
          else
            $window.alert data.error_msg
      $scope.removeMember = (index)->
        if index >= 1 && index < 3
          $scope.memberList.splice(index, 1);
      $scope.createTeam = ->
        if $scope.memberList.length < 1 || $scope.memberList.length > 3
          $window.alert "Member number should between 1 and 3."
        else
          teamDTO = angular.copy($scope.teamDTO)
          teamDTO.memberList = _.map($scope.memberList,(val) ->
                return val.userId
          ).join(",")
          $http.post("/team/createTeam", teamDTO).then (response)->
            data = response.data
            if data.result == "success"
              $scope.hideMemberPanel = true
              $scope.teamDTO.teamName = ""
              $scope.memberList = []
              # TODO
            else
              $window.alert data.error_msg

])