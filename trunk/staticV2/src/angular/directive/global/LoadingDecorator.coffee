angular.module("cdojV2").directive("cdojLoadingDecorator", [
  ->
    restrict: "E"
    transclude: true
    replace: true
    scope:
      onLoading: "="
    templateUrl: getTemplateUrl("global", "loadingDecorator")
])
