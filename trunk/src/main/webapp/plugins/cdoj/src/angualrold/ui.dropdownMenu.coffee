cdoj.directive("uiDropdownMenu",
->
  restrict: "A"
  link: ($scope, $element)->
    $element.find('form').click (e)=>
      e.stopPropagation()
)