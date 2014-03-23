cdoj
.controller("ProblemListController", [
    "$scope", "$rootScope", "$http"
    ($scope, $rootScope, $http)->
      $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.NOOP)
      $rootScope.title = "Problem list"
  ])