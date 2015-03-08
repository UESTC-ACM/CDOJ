angular.module("cdojV2").controller("IndexPageController", [
  "$rootScope", "$scope", "ArticleService"
  ($rootScope, $scope, articleService) ->
    $rootScope.$broadcast("pageChangeEvent", "Home")
    $rootScope.$broadcast("pageTitleChangeEvent", "Home", "")

    $scope.articleList = []
    $scope.onLoading = true

    articleService.getAllNotice((data) ->
      # Do something
      $scope.articleList = data.list
      $scope.onLoading = false
    , (data) ->
      $scope.onLoading = false

      # Do something
      #setIsLoading(false)
    )
])