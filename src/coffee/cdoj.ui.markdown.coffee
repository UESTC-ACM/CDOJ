# Angular!
cdoj.directive("uiMarkdown",
  ->
    restrict: "A"
    scope:
      content: "=content"
    link: ($scope, $element, $attrs) ->
      refresh = ()->
        $element.prettify()
        $element.mathjax()

      refresh()

      if $scope.content != undefined
        $scope.$watch("content",
        (newVal) ->
          newVal = marked(newVal)
          $element.empty().append(newVal)
          refresh()
        )
)