cdoj.directive("uiTime",
->
  restrict: "A"
  scope:
    time: "="
    inline: "="
  controller: [
    "$scope",
    ($scope) ->
      $scope.timeString = ""
      $scope.$watch("time", ->
        $scope.showRelativeTime()
      )
      $scope.showRelativeTime = ->
        $scope.timeString = Date.create($scope.time).relative()
      $scope.showRealTime = ->
        $scope.timeString = Date.create($scope.time).format("{yyyy}-{MM}-{dd} {HH}:{mm}:{ss}")
      $scope.showRelativeTimeDelay = ->
        setTimeout(->
          $scope.$apply(->
            $scope.showRelativeTime()
          )
        , 400)
      $scope.getInline = ->
        if $scope.inline
          return "inline"
        else
          return "block"
  ]
  template: """
    <div ng-mouseover="showRealTime()"
         ng-mouseleave="showRelativeTimeDelay()"
         style="width: 100%;"
         ng-style="{display: getInline()}">{{timeString}}</div>
  """
)