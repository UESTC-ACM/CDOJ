cdoj
.directive("uiTimeLength",
->
  restrict: "A"
  scope:
    length: "="
  controller: [
    "$scope",
    ($scope) ->
      $scope.timeString = ""
      $scope.$watch("length", ->
        $scope.showTimeLength()
      )
      $scope.showTimeLength = ->
        length = Math.floor(parseInt($scope.length) / 1000)
        second = length % 60
        length = (length - second) / 60

        minute = length % 60
        length = (length - minute) / 60

        hours = length % 24
        length = (length - hours) / 24
        days = length
        $scope.timeString = ""
        if days > 0
          $scope.timeString =
            $scope.timeString + days +
              " #{if days == 1 then "day" else "days"} "
        $scope.timeString =
            $scope.timeString +
              _.sprintf("%d:%02d:%02d", hours, minute, second)
  ]
  template: """
    {{timeString}}
  """
)
