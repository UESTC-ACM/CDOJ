cdoj
.controller("ArticleShowController", [
    "$scope", "$rootScope", "$routeParams", "$http", "$window"
    ($scope, $rootScope, $routeParams, $http, $window) ->
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.NOOP
      )
      $window.scrollTo(0, 0)

      $scope.article =
        content: ""
        title: ""

      articleId = angular.copy($routeParams.articleId)
      $http.get("/article/data/#{articleId}").success((data) ->
        if data.result == "success"
          $scope.article = data.article
          $rootScope.title = $scope.article.title
        else
          $window.alert data.error_msg
      ).error(->
        $window.alert "Network error, please refresh page manually."
      )
  ])