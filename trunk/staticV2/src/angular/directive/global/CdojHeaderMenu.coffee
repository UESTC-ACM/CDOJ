angular.module("cdojV2").directive("cdojHeaderMenu", [
  ->
    restrict: "E"
    replace: true
    controller: "MenuController"
    templateUrl: getTemplateUrl("global", "headerMenu")
])
