# Angular!
cdoj.directive("uiDatetimepicker",
  ->
    restrict: 'A'
    link:
      ($scope, $element, $attrs) ->
        console.log $element
        $element.datetimepicker()
)