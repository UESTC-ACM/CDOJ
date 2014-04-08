cdoj
.controller("StatusListController", [
    "$scope", "$rootScope", "$window"
    ($scope, $rootScope, $window) ->
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.NOOP
      )
      $window.scrollTo(0, 0)
      $scope.refresh = ->
        $scope.$broadcast("list:refresh:status")
      $scope.resetStatusCondition = ->
        $scope.$broadcast("list:reset:status")
  ])