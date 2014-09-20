cdoj
.directive("uiPenalty",
->
  restrict: "A"
  scope:
    penalty: "="
  controller: ["$scope",
    ($scope) ->
      $scope.timeString = ""
      $scope.$watch("penalty", ->
        penalty = $scope.penalty
        length = parseInt(penalty)
        second = length % 60
        length = (length - second) / 60

        minute = length % 60
        length = (length - minute) / 60

        hours = length
        $scope.timeString = _.sprintf("%d:%02d:%02d", hours, minute, second)
      )
  ]
  template: """
   {{timeString}}
"""
)