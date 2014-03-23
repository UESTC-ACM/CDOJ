cdoj
.directive("uiTime",
->
  restrict: "A"
  scope:
    time: "="
    inline: "="
    format: "@"
    show: "@"
    change: "@"
  controller: [
    "$scope",
    ($scope) ->
      $scope.timeString = ""
      $scope.showRelativeTime = ->
        $scope.timeString = Date.create($scope.time).relative()
      $scope.showRealTime = ->
        $scope.timeString = Date.create($scope.time).format("{yyyy}-{MM}-{dd} {HH}:{mm}:{ss}")
      $scope.showTime = ->
        if $scope.show == "real"
          $scope.showRealTime()
        else
          $scope.showRelativeTime()
      $scope.$watch("time", ->
        $scope.showTime()
      )
      $scope.mouseOver = ->
        if $scope.change == "false" then return
        if $scope.show == "real"
          $scope.showRelativeTime()
        else
          $scope.showRealTime()
      $scope.mouseLeave = ->
        if $scope.change == "false" then return
        setTimeout(->
          $scope.$apply(->
            $scope.showTime()
          )
        , 400)
      $scope.getInline = ->
        if $scope.inline
          return "inline"
        else
          return "block"
  ]
  template: """
    <div ng-mouseover="mouseOver()"
         ng-mouseleave="mouseLeave()"
         style="width: 100%;"
         ng-style="{display: getInline()}">{{timeString}}</div>
  """
)