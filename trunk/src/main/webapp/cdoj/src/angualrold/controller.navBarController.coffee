cdoj.controller("navBarController", [
  "$scope", "$window"
  ($scope, $window) ->
    $scope.isActive = (viewLocation) ->
      return viewLocation == $window.location.pathname.split("/")[1]
])
