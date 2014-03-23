cdoj
.controller("ContestListController", [
    "$scope", "$rootScope", "$window", "$modal"
    ($scope, $rootScope, $window, $modal)->
      $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.NOOP)

      $scope.showAddContestModal = ->
        if $rootScope.isAdmin == false then return
        $modal.open(
          templateUrl: "template/modal/add-contest-modal.html"
          controller: "AddContestModalController"
        )
  ])
