cdoj.directive("uiAvatar",
->
  restrict: "A"
  scope:
    email: "="
    rating: "@"
    image: "@"
    size: "@"
  controller: [
    "$scope", "$element",
    ($scope, $element)->
      $scope.$watch("email",
        ->
          $scope.size = $element.width() if $scope.size == undefined
          $scope.image = "retro" if $scope.image == undefined
          $scope.rating = "pg" if $scope.rating == undefined
          url =
            "http://www.gravatar.com/avatar/#{hex_md5($scope.email)}.jpg?#{if $scope.size then "s=" + $scope.size + "&" else ""}#{if $scope.rating then "r=" + $scope.rating + "&" else ""}#{if $scope.image then "d=" + encodeURIComponent($scope.image) else ""}"
          $element.attr("src", url)
      )
  ]
)