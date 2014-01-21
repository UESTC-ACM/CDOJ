# Angular!
cdoj.directive("uiDatetimepicker",
  ->
    restrict: 'A'
    link:
      ($scope, $element) ->
        console.log $element
        $element.datetimepicker()
)