cdoj
.controller("ProblemListController", [
    "$scope", "$rootScope", "$window"
    ($scope, $rootScope, $window)->
      $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.NOOP)
      $window.scrollTo(0, 0)
      $rootScope.title = "Problem list"
  ])