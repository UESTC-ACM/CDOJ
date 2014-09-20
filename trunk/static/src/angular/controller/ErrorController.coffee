cdoj
.controller("ErrorController", [
    "$routeParams", "$scope"
    ($routeParams, $scope) ->
      $scope.message = $routeParams.message
  ])