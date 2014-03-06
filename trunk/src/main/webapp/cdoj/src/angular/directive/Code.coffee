cdoj
.directive("uiCode",
  ->
    restrict: "E"
    scope:
      code: "="
    controller: [
      "$scope"
      ($scope, $element)->
        $scope.prettifiedCode = ""
        $scope.$watch("code", ->
          $scope.prettifiedCode = prettyPrintOne($scope.code.trim().escapeHTML())
        )
    ]
    template: """
<pre ng-bind-html="prettifiedCode"></pre>
"""
    replace: true
  )