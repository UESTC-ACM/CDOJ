cdoj
.directive("activeLink", [
    "$location"
    ($location)->
      restrict: "A"
      link: ($scope, $element, $attr)->
        position = $attr.activeLink
        $scope.location = $location
        $scope.$watch("location.path()", (newPath)->
          currentPosition = newPath.split("/")[1]
          currentPosition = "" if angular.isUndefined(currentPosition)
          if currentPosition == position
            $element.addClass("active")
          else
            $element.removeClass("active")
        )
  ])
