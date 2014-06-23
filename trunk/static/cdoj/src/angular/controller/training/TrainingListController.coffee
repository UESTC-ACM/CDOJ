cdoj
.controller("TrainingListController", [
    "$scope", "$rootScope", "$window"
    ($scope, $rootScope, $window) ->
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.NOOP
      )
      $window.scrollTo(0, 0)
      $rootScope.title = "Training list"
  ])