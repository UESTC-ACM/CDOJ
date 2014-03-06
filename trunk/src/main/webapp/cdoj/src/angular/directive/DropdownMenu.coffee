# Prevent close dropdown menu when click form input
cdoj
.directive("uiDropdownMenu",
  ->
    restrict: "A"
    link: ($scope, $element)->
      $element.find("form").on("click", (e)=>
        e.stopPropagation()
      )
  )