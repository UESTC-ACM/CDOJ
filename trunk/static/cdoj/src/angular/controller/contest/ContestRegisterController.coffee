cdoj
.controller("ContestRegisterController", [
    "$scope", "$rootScope", "$http", "$window", "$modal", "contest"
    ($scope, $rootScope, $http, $window, $modal, contest) ->
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.NOOP
      )
      $window.scrollTo(0, 0)

      $scope.contest = contest
      contestId = contest.contestId

      $scope.contestTeamCondition =
        angular.copy($rootScope.contestTeamCondition)
      $scope.contestTeamCondition.contestId = contestId

      $scope.team =
        teamName: ""
        invitedUsers: []
        leaderId: null
        teamId: null
        teamUsers: []
      $scope.searchTeam = (teamName) ->
        teamCondition = angular.copy $rootScope.teamCondition
        teamCondition.teamName = teamName
        teamCondition.userId = $rootScope.currentUser.userId
        teamCondition.allow = true
        $http.post("/team/typeAHeadSearch", teamCondition).then((response) ->
          data = response.data
          if data.result == "success"
            return data.list
          else
            return []
        )
      $scope.showRegisterButton = false
      $scope.selectTeam = ->
        $http.get(
          "/team/typeAHeadItem/" + $scope.team.teamName
        ).success((data) ->
          if data.result == "success"
            $scope.team = data.team
            $scope.showRegisterButton = true
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
      $scope.register = ->
        $http.get(
            "/contest/register/" + $scope.team.teamId + "/" + contestId
        ).success((data) ->
          if data.result == "success"
            $window.alert "Register success! please wait for verify"
            $scope.team =
              teamName: ""
              invitedUsers: []
              leaderId: null
              teamId: null
              teamUsers: []
            $scope.showRegisterButton = false
            $rootScope.$broadcast("list:refresh:team")
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
      $scope.review = (team) ->
        $modal.open(
          templateUrl: "template/modal/contest-registry-review-modal.html"
          controller: "ContestRegistryReviewModalController"
          resolve:
            team: ->
              team
        )
  ])