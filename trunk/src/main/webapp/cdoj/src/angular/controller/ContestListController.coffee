cdoj
.controller("ContestListController", [
    "$scope", "$rootScope", "$window", "$modal", "$http"
    ($scope, $rootScope, $window, $modal, $http)->
      $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.NOOP)
      $window.scrollTo(0, 0)

      $scope.showAddContestModal = ->
        if $rootScope.isAdmin == false then return
        $modal.open(
          templateUrl: "template/modal/add-contest-modal.html"
          controller: "AddContestModalController"
        )

      $scope.enterContest = (contest)->
        if contest.type == $rootScope.ContestType.INHERIT
          type = contest.parentType
        else
          type = contest.type
        if type == $rootScope.ContestType.PRIVATE
          if $rootScope.hasLogin == false
            $window.alert "Please login first!"
          else
            contestLoginDTO =
              contestId: contest.contestId
              # Avoid field error
              password: "1234567890123456789012345678901234567890"
            $http.post("/contest/loginContest", contestLoginDTO).success((data)->
              if data.result == "success"
                # The use has logined before
                $window.location.href = "/#/contest/show/" + contest.contestId
              else
                # Open password modal
                $modal.open(
                  templateUrl: "template/modal/contest-password-modal.html"
                  controller: "ContestPasswordModalController"
                  resolve:
                    contest: -> contest
                )
            ).error(-> $window.alert "Network error!")
        else if type == $rootScope.ContestType.INVITED
          contestLoginDTO =
            contestId: contest.contestId
          # Avoid field error
            password: "1234567890123456789012345678901234567890"
          $http.post("/contest/loginContest", contestLoginDTO).success((data)->
            if data.result == "success"
              # The use has logined before
              $window.location.href = "/#/contest/show/" + contest.contestId
            else
              $window.alert data.error_msg
          ).error(-> $window.alert "Network error!")
        else
          $window.location.href = "/#/contest/show/" + contest.contestId
  ])
cdoj
.controller("ContestPasswordModalController", [
    "$scope", "$rootScope", "$window", "$modalInstance", "contest", "$http"
    ($scope, $rootScope, $window, $modalInstance, contest, $http)->
      $scope.contest = contest
      $scope.fieldInfo = []

      $scope.enter = ->
        contestLoginDTO =
          contestId: contest.contestId
          password: CryptoJS.SHA1(contest.password).toString()
        $http.post("/contest/loginContest", contestLoginDTO).success((data)->
          if data.result == "success"
            # Success!
            $modalInstance.close()
            $window.location.href = "/#/contest/show/" + contest.contestId
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
        ).error(-> $window.alert "Network error!")
      $scope.cancel = ->
        $modalInstance.dismiss()
  ])