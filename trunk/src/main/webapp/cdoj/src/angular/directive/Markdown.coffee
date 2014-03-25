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
        # replace
        content = content.replace /@([a-zA-Z0-9_]{4,24})\s/g, "[@$1](/#/user/center/$1)"
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