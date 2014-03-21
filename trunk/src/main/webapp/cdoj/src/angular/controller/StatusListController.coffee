cdoj
.controller("StatusListController", [
    "$scope", "$rootScope", "$http"
    ($scope, $rootScope, $http)->
      $scope.refresh = ->
        $scope.$broadcast("refreshList")
  ])