cdoj
.directive("uiDatetimepicker",
  ->
    restrict: 'A'
    require: "ngModel"
    link: ($scope, $element, $attrs, $controller) ->
      $($element).datetimepicker().on("changeDate", (e)->
        $scope.$apply ->
          $controller.$setViewValue e.date.valueOf()
      )
  )