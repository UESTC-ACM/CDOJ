cdoj
.directive("uiPenalty",
->
  restrict: "A"
  link: ($scope, $element, $attr) ->
    penalty = $scope.$eval($attr.penalty)
    length = parseInt(penalty)
    second = length % 60
    length = (length - second) / 60

    minute = length % 60
    length = (length - minute) / 60

    hours = length
    timeString =  _.sprintf("%d:%02d:%02d", hours, minute, second)
    $($element).text(timeString)
)