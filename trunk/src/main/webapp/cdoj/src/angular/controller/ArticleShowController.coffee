cdoj
.controller("ArticleShowController", [
    "$scope", "$rootScope", "$routeParams", "$http", "$window"
    ($scope, $rootScope, $routeParams, $http, $window)->
      $scope.article =
        content: ""
        title: ""

      articleId = angular.copy($routeParams.articleId)
      $http.get("/article/data/#{articleId}").then (response)->
        data = response.data
        if data.result == "success"
          $scope.article = data.article
          $rootScope.title = $scope.article.title
        else
          $window.alert data.error_msg
  ])