cdoj
.directive("uiReadmore",
  ->
    restrict: "A"
    scope:
      hasMore: "="
      articleId: "="
    link: ($scope, $element) ->
      if $scope.hasMore
        $element.empty().append("<a href=\"/article/show/#{$scope.articleId}\" target=\"_blank\">Read more >></a>")
  )