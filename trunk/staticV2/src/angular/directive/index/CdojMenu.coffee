cdojV2
.directive("cdojMenu", [
    ->
      restrict: "E"
      replace: true
      controller: "MenuController"
      templateUrl: getTemplateUrl("index", "menu")
  ])
