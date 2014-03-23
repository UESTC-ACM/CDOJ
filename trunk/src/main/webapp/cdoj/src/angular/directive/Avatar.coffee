cdoj
.directive("uiAvatar",
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
        if angular.isUndefined($scope.email) || $scope.email == null || $scope.email == "" then return
        $scope.size = $($element).width() if $scope.size == undefined
        $scope.image = "retro" if $scope.image == undefined
        $scope.rating = "pg" if $scope.rating == undefined
        url =
          "http://www.gravatar.com/avatar/#{CryptoJS.MD5($scope.email).toString()}.jpg?#{if $scope.size then "s=" + $scope.size + "&" else ""}#{if $scope.rating then "r=" + $scope.rating + "&" else ""}#{if $scope.image then "d=" + encodeURIComponent($scope.image) else ""}"
        $element.attr("src", url)
      )
  ]
)