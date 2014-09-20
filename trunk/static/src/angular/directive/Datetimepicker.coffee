cdoj
.directive("uiDatetimepicker",
->
  restrict: 'A'
  require: "ngModel"
  link: ($scope, $element, $attrs, $controller) ->
    $($element).datetimepicker().on("changeDate", (e) ->
      $scope.$apply ->
        # Date time picker use UTC locale, we need convert it manually.
        time = e.date.valueOf() - 8 * 60 * 60 * 1000
        $controller.$setViewValue time
    )
)