cdoj
.controller("ContestRegisterController", [
    "$scope", "$rootScope", "$http", "$window", "$modal", "$routeParams"
    ($scope, $rootScope, $http, $window, $modal, $routeParams)->
      if $rootScope.hasLogin == false
        $window.location.href = "/#/contest/list"
      contestId = $routeParams.contestId
      $scope.contest = 0
      contestCondition = angular.copy($rootScope.contestCondition)
      contestCondition.startId = contestCondition.endId = contestId
      $http.post("/contest/search", contestCondition).then (response)->
        data = response.data
        if data.result == "success"
          if data.list.length == 1
            $scope.contest = data.list[0]
          else
            $window.alert("Wrong contest id.")
        else
          $window.alert(data.error_msg)

      $scope.contestTeamCondition = angular.copy($rootScope.contestTeamCondition)
      $scope.contestTeamCondition.contestId = contestId

      $scope.team =
        teamName: ""
        invitedUsers: []
        leaderId: null
        teamId: null
        teamUsers: []
      $scope.searchTeam = (teamName)->
        teamCondition = angular.copy $rootScope.teamCondition
        teamCondition.teamName = teamName
        teamCondition.userId = $rootScope.currentUser.userId
        teamCondition.allow = true
        $http.post("/team/typeAHeadSearch", teamCondition).then (response)->
          data = response.data
          if data.result == "success"
            return data.list
          else
            return []
      $scope.showRegisterButton = false
      $scope.selectTeam = ->
        $http.get("/team/typeAHeadItem/#{$scope.team.teamName}").then (response)->
          data = response.data
          if data.result == "success"
            $scope.team = data.team
            $scope.showRegisterButton = true
          else
            $window.alert data.error_msg
      $scope.register = ->
        $http.get("/contest/register/#{$scope.team.teamId}/#{$scope.contest.contestId}").then (response)->
          data = response.data
          if data.result == "success"
            $window.alert "Register success! please wait for verify"
            $rootScope.$broadcast("refresh");
          else
            $window.alert data.error_msg
      $scope.review = (team)->
        $modal.open(
          templateUrl: "template/modal/contest-registry-review-modal.html"
          controller: "ContestRegistryReviewModalController"
          resolve:
            team: -> team
        )
])
cdoj
.controller("ContestRegistryReviewModalController", [
    "$scope", "$rootScope", "$http", "$modalInstance", "team", "$window"
    ($scope, $rootScope, $http, $modalInstance, team, $window)->
      $scope.team = team
      $scope.review =
        result: ""
      _.each($scope.team.teamUsers, (teamUser)->
        $http.get("/user/profile/#{teamUser.userName}").then (response)->
          data = response.data
          if data.result == "success"
            _.extend(teamUser, data.user)
            teamUser.sex = _.findWhere($rootScope.genderTypeList,
              genderTypeId: data.user.sex).description
            teamUser.departmentId = _.findWhere($rootScope.departmentList,
              departmentId: data.user.departmentId).name
            teamUser.grade = _.findWhere($rootScope.gradeTypeList,
              gradeTypeId: data.user.grade).description
            teamUser.size = _.findWhere($rootScope.tShirtsSizeTypeList,
              sizeTypeId: data.user.size).description
            teamUser.type = _.findWhere($rootScope.authenticationTypeList,
              authenticationTypeId: data.user.type).description
          else
            $window.alert data.error_msg
      )
      $scope.review = (dto)->
        $http.post("/contest/registryReview", dto).then (response)->
          data = response.data
          if data.result == "success"
            $window.alert "Success!"
            $scope.team.status = dto.status
            if dto.status == $rootScope.ContestRegistryStatus.ACCEPTED
              $scope.team.statusName = "Accepted"
            else
              $scope.team.statusName = "Refused"
            $modalInstance.close()
      $scope.accept = ->
        $scope.review(
          contestTeamId: $scope.team.contestTeamId
          status: $rootScope.ContestRegistryStatus.ACCEPTED
          comment: $scope.review.result
        )

      $scope.refuse = ->
        $scope.review(
          contestTeamId: $scope.team.contestTeamId
          status: $rootScope.ContestRegistryStatus.REFUSED
          comment: $scope.review.result
        )

      $scope.reasonList = [
      ]
])