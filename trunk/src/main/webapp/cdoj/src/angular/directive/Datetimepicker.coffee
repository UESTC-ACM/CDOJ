cdoj
.directive("uiDatetimepicker",
  ->
    restrict: 'A'
    link: ($scope, $element) ->
      $($element).datetimepicker()
  )