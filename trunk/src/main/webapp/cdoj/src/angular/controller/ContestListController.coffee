cdoj
.controller("ContestListController", [
    "$scope", "$rootScope", "$window", "$http", "$modal"
    ($scope, $rootScope, $window, $http, $modal)->
      $scope.registerContest = (contest)->
        if $rootScope.hasLogin == false
          $window.alert "Please login first!"
        else
          $modal.open(
            templateUrl: "template/modal/contest-register-modal.html"
            controller: "ContestRegisterModalController"
            resolve:
              contest: -> contest
          )
  ])