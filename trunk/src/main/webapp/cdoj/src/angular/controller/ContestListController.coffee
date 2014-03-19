cdoj
.controller("ContestListController", [
    "$scope", "$rootScope", "$window", "$modal"
    ($scope, $rootScope, $window, $modal)->
      $scope.showAddContestModal = ->
        if $rootScope.isAdmin == false then return
        $modal.open(
          templateUrl: "template/modal/add-contest-modal.html"
          controller: "AddContestModalController"
        )
  ])
cdoj
.controller("AddContestModalController", [
    "$scope", "$rootScope", "$modalInstance",
    ($scope, $rootScope, $modalInstance)->

])