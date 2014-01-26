# FIXME This statement doesn't work...
###
cdoj.directive("autoFill", ["$timeout",
  ($timeout)->
    restrict: "E"
    require: "?ngModel"
    link: ($scope, $element, $attrs, $ngModel)->
      if $ngModel != undefined
        $scope.check = ->
          val = $element.val()
          if $ngModel.$viewValue != val
            $ngModel.$setViewValue(val)
          $timeout($scope.check, 300)
        $scope.check()
])
###