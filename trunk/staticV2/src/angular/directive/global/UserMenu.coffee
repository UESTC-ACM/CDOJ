angular.module("cdojV2").directive("cdojUser", [
  ->
    restrict: "E"
    replace: true
    controller: "UserController"
    templateUrl: getTemplateUrl("global", "user")
])
