angular.module("cdojV2").directive("cdojSidenavMenu", [
  ->
    restrict: "E"
    replace: true
    controller: "MenuController"
    templateUrl: getTemplateUrl("global", "sidenavMenu")
])
