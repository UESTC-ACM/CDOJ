cdoj
.directive("uiCode",
->
  restrict: "E"
  scope:
    code: "="
  link: ($scope, $element)->
    $scope.$watch("code", ->
      result = prettyPrintOne($scope.code.trim().escapeHTML())
      $($element).empty().append(result)
    )
  template: """
<pre></pre>
"""
  replace: true
)