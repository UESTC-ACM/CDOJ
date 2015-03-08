angular.module("cdojV2").directive("markdown",
  ->
    restrict: "EA"
    scope:
      markdownContent: "="
    link: ($scope, $element) ->
      if angular.isDefined $scope.markdownContent
        $scope.$watch("markdownContent",
          ->
            content = angular.copy($scope.markdownContent)
            # replace @user
            content =
              content.replace(
                /@([a-zA-Z0-9_]{4,24})\s/g, "[@$1](/#/user/center/$1)"
              )
            # render markdown content
            content = marked(content)
            $element.addClass("markdown-body")
            $element.empty().append(content)
            # render math equation
            MathJax.Hub.Queue(["Typeset", MathJax.Hub, $element[0]])
            # render code block
            _.each(angular.element($element).find("pre"), (el) ->
              $el = angular.element(el)
              if $el.attr("type") != "no-prettify"
                text = prettyPrintOne(_.str.escapeHTML($el.text()))
                $el.empty().append(text)
            )
        , true)
)