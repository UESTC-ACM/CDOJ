MathJax.Hub.Config(
  tex2jax:
    inlineMath: [
      [ '$', '$' ],
      [ '\\[', '\\]' ]
    ]
)

cdoj
.directive("markdown",
  ->
    restrict: "EA"
    scope:
      content: "="
    link: ($scope, $element) ->
      if angular.isDefined $scope.content
        $scope.$watch("content",
        ->
          content = angular.copy($scope.content)
          content = marked(content)
          $element.empty().append(content)
          MathJax.Hub.Queue(["Typeset", MathJax.Hub, $element[0]]);
          $($element).find("pre").each (id, el)=>
            $el = $(el)
            if $el.attr("type") != "no-prettify"
              text = prettyPrintOne($el[0].innerText.escapeHTML())
              $el.empty().append(text)
        , true)
    template: """
<div></div>
"""
  )