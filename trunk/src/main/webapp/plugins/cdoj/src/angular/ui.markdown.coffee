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
          ->
            content = angular.copy($scope.content)
            content = marked(content)
            $element.empty().append(content)
            refresh()
          , true)
)