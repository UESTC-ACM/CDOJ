angular.module("cdojV2").directive("cdojMain", [
  ->
    restrict: "E"
    replace: true
    templateUrl: getTemplateUrl("global", "main")
    controller: [
      "$scope", "$mdSidenav"
      ($scope, $mdSidenav) ->
        $scope.hideSidenav = ->
          # Close sidenav
          $mdSidenav("left-sidenav").close()
    ]
])
