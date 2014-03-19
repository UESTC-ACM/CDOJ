cdoj
.directive("autoFillSync", [
    "$timeout",
    ($timeout)->
      require: "ngModel"
      link: ($scope, $elem, $attrs, $ngModel)->
        origVal = $elem.val()
        $timeout(->
          newVal = $elem.val()
          if ($ngModel.$pristine && origVal != newVal)
            $ngModel.$setViewValue(newVal)
        , 500)
])