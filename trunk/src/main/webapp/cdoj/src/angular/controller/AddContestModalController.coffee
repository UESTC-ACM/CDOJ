cdoj
.controller("AddContestModalController", [
    "$scope", "$rootScope", "$modalInstance", "$window"
    ($scope, $rootScope, $modalInstance, $window)->
      $scope.$on("$routeChangeStart", ->
        $modalInstance.close()
      )
      $scope.$on("contestUploader:complete", (e, contestId)->
        $window.location.href = "/#/contest/show/#{contestId}"
      )
      $scope.$on("$routeChangeStart", ->
        $modalInstance.dismiss()
      )
  ])
cdoj