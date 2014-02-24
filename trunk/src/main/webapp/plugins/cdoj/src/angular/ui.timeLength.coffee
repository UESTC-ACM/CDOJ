cdoj.directive("uiTimeLength",
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
        length = parseInt($scope.length)
        second = length % 60
        length = (length - second) / 60

        minute = length % 60
        length = (length - minute) / 60

        hours = length % 24
        length = (length - hours) / 24
        days = length
        $scope.timeString = ""
        if days > 0
          $scope.timeString = $scope.timeString + days + " #{if days == 1 then "day" else "days"} "
        $scope.timeString = $scope.timeString + "%d:%02d:%02d".sprintf(hours, minute, second)
  ]
  template: """
    {{timeString}}
  """
)
cdoj.directive("uiPenalty",
->
  restrict: "A"
  scope:
    penalty: "="
  controller: [
    "$scope",
    ($scope) ->
      $scope.timeString = ""
      $scope.$watch("penalty", ->
        $scope.showPenaltyMinutes()
      )
      $scope.showPenaltyMinutes = ->
        $scope.timeString = Math.round($scope.penalty / 60)
      $scope.showPenalty = ->
        length = parseInt($scope.penalty)
        second = length % 60
        length = (length - second) / 60

        minute = length % 60
        length = (length - minute) / 60

        hours = length
        $scope.timeString = ""
        $scope.timeString = $scope.timeString + "%d:%02d:%02d".sprintf(hours, minute, second)

      $scope.mouseOver = ->
        $scope.showPenalty()
      $scope.mouseLeave = ->
        setTimeout(->
          $scope.$apply(->
            $scope.showPenaltyMinutes()
          )
        , 400)
  ]
  template: """
    <span ng-mouseover="mouseOver()" ng-mouseleave="mouseLeave()">{{timeString}}</span>
  """
)