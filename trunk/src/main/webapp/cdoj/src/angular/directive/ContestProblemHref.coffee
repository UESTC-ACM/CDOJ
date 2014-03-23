cdoj
.directive("uiContestProblemHref"
  ->
    restrict: "E"
    scope:
      problemId: "="
      problemList: "="
    controller: [
      "$scope"
      ($scope)->
        $scope.order = -1
        $scope.orderCharacter = "-"
        $scope.$watch("problemId + problemList", ->
          target = _.findWhere($scope.problemList, "problemId": $scope.problemId)
          if target != undefined
            $scope.orderCharacter = target.orderCharacter
            $scope.order = target.order
        )
        $scope.select = ->
          if $scope.order != -1
            $scope.$emit("contestShow:showProblemTab", $scope.order)
    ]
    template: """
<a href="javascript:void(0);" ng-bind="orderCharacter" ng-click="select()"></a>
"""
    replace: true
)