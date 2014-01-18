# Angular!
cdoj.directive("uiDatetimepicker",
  ->
    restrict: 'A'
    link: ($scope, $element, $attrs) ->
      $element.datetimepicker()
)