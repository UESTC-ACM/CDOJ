cdoj
.controller("ContestPasswordModalController", [
    "$scope", "$rootScope", "$window", "$modalInstance", "contest", "$http"
    ($scope, $rootScope, $window, $modalInstance, contest, $http) ->
      $scope.contest = contest
      $scope.fieldInfo = []

      $scope.enter = ->
        contestLoginDTO =
          contestId: contest.contestId
          password: CryptoJS.SHA1(contest.password).toString()
        $http.post(
          "/contest/loginContest"
          contestLoginDTO
        ).success((data) ->
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