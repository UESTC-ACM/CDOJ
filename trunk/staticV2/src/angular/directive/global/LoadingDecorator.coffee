angular.module("cdojV2").directive("cdojLoadingDecorator", [
  ->
    restrict: "A"
    transclude: true
    scope:
      onLoading: "="
    templateUrl: getTemplateUrl("global", "loadingDecorator")
])
