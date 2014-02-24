cdoj.controller("ArticleController", [
  "$scope", "$rootScope", "$http", "$window"
  ($scope, $rootScope, $http, $window)->
    $scope.article =
      content: ""
      title: ""
    $scope.articleId = 0
    $scope.$watch("articleId",
    ->
      articleId = angular.copy($scope.articleId)
      $http.get("/article/data/ArticleDTO/#{articleId}").then (response)->
        data = response.data
        if data.result == "success"
          $scope.article = data.article
          $rootScope.title = $scope.article.title
        else
          $window.alert data.error_msg
    )
])