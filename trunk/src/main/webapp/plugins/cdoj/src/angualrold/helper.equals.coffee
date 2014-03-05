cdoj.directive("equals",
()->
  restrict: "A"
  require: "?ngModel"
  link: ($scope, $element, $attrs, $ngModel)->
    if $ngModel == undefined then return
    # watch own value and re-validate on change
    $scope.$watch($attrs.ngModel, -> validate())

    # observe the other value and re-validate on change
    $attrs.$observe("equals", ()-> validate())

    validate = ->
      val1 = angular.copy($ngModel.$viewValue)
      val2 = angular.copy($attrs.equals)

      val2 = "" if val2 == undefined
      val1 = "" if val1 == undefined
      # set validity
      $ngModel.$setValidity("equals", val1 == val2)
)