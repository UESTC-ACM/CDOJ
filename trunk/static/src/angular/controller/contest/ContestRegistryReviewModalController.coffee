cdoj
.controller("ContestRegistryReviewModalController", [
    "$scope", "$rootScope", "$http", "$modalInstance", "team", "$window"
    ($scope, $rootScope, $http, $modalInstance, team, $window) ->
      $scope.team = team
      $scope.review =
        result: ""
      $scope.teamUsers =
        angular.copy($scope.team.teamUsers).concat(
          angular.copy($scope.team.invitedUsers)
        )

      _.each($scope.teamUsers, (teamUser) ->
        $http.get(
          "/user/profile/" + teamUser.userName
        ).success((data) ->
          if data.result == "success"
            _.extend(teamUser, data.user)
            teamUser.sex = _.findWhere(
              $rootScope.genderTypeList
              genderTypeId: data.user.sex
            ).description
            teamUser.departmentId = _.findWhere(
              $rootScope.departmentList
              departmentId: data.user.departmentId
            ).name
            teamUser.grade = _.findWhere(
              $rootScope.gradeTypeList
              gradeTypeId: data.user.grade
            ).description
            teamUser.size = _.findWhere(
              $rootScope.tShirtsSizeTypeList
              sizeTypeId: data.user.size
            ).description
            teamUser.type = _.findWhere(
              $rootScope.authenticationTypeList
              authenticationTypeId: data.user.type
            ).description
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
      )
      $scope.review = (dto) ->
        $http.post("/contest/registryReview", dto).success((data) ->
          if data.result == "success"
            $window.alert "Success!"
            $scope.team.status = dto.status
            if dto.status == $rootScope.ContestRegistryStatus.ACCEPTED
              $scope.team.statusName = "Accepted"
            else
              $scope.team.statusName = "Refused"
            $modalInstance.close()
        ).error(->
          $window.alert "Network error."
        )
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
      $scope.$on("$routeChangeStart", ->
        $modalInstance.dismiss()
      )
  ])