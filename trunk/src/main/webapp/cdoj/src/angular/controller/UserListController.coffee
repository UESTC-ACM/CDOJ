cdoj
.controller("UserListController", [
    "$scope", "$rootScope"
    ($scope, $rootScope)->
      $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.NOOP)
      $rootScope.title = "User list"
  ])