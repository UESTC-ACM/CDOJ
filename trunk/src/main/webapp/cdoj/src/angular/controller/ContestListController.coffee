cdoj
.controller("ContestListController", [
    "$scope", "$rootScope", "$window"
    ($scope, $rootScope, $window)->
      $scope.toRegisterPage = (contest)->
        if $rootScope.hasLogin == false
          $window.alert "Please login first!"
        else
          $window.location.href = "/#/contest/register/#{contest.contestId}"
  ])