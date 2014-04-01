cdoj
.directive("autoFillSync", [
    "$timeout",
    ($timeout)->
      require: "ngModel"
      link: ($scope, $elem, $attrs, $ngModel)->
        $timeout(->
          newVal = $elem.val()
          if ($ngModel.$pristine)
            $ngModel.$setViewValue(newVal)
        , 500)
  ])