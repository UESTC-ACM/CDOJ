angular.module("cdojV2").controller("MainController", [
  "$scope", "$mdSidenav"
  ($scope, $mdSidenav) ->
    LEFT_SIDEBAR_ID = "left-sidenav"

    # Open left sidebar if it is closed.
    $scope.openMenu = ->
      if $mdSidenav(LEFT_SIDEBAR_ID).isOpen() == false
        $mdSidenav(LEFT_SIDEBAR_ID).open()
])