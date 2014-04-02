cdoj.directive("equals",
->
  restrict: "A"
  require: "?ngModel"
  link: ($scope, $element, $attrs, $ngModel)->
    if angular.isUndefined $ngModel then return
    # watch own value and re-validate on change
    $scope.$watch($attrs.ngModel, ->
      validate())

    # observe the other value and re-validate on change
    $attrs.$observe("equals", ()->
      validate())

    validate = ->
      val1 = angular.copy($ngModel.$viewValue)
      val2 = angular.copy($attrs.equals)

      val2 = "" if angular.isUndefined val2
      val1 = "" if angular.isUndefined val1
      # set validity
      $ngModel.$setValidity("equals", val1 == val2)
)