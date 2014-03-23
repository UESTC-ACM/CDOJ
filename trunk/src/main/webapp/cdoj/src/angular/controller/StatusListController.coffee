cdoj
.controller("StatusListController", [
    "$scope", "$rootScope"
    ($scope, $rootScope)->
      $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.NOOP)
      $scope.refresh = ->
        $scope.$broadcast("list:refresh:status")
  ])