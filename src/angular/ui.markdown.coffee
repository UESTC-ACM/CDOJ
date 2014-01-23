# Angular!
cdoj.directive("uiMarkdown",
  ->
    restrict: "A"
    scope:
      content: "="
    link:
      ($scope, $element) ->
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