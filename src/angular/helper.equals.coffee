cdoj.directive("equals",
()->
  restrict: "A"
  require: "?ngModel"
  link: ($scope, $element, $attrs, $ngModel)->
    if $ngModel == undefined then return
    # watch own value and re-validate on change
    $scope.$watch($attrs.ngModel, -> validate())

    # observe the other value and re-validate on change
    $attrs.$observe("equals", (val)-> validate())

    validate = ->
      val1 = $ngModel.$viewValue
      val2 = $attrs.equals

      # set validity
      $ngModel.$setValidity("equals", val1 == val2)
)