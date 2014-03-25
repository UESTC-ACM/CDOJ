cdoj
.controller("ContestListController", [
    "$scope", "$rootScope", "$window", "$modal"
    ($scope, $rootScope, $window, $modal)->
      $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.NOOP)
      $window.scrollTo(0, 0)

      $scope.showAddContestModal = ->
        if $rootScope.isAdmin == false then return
        $modal.open(
          templateUrl: "template/modal/add-contest-modal.html"
          controller: "AddContestModalController"
        )
  ])
