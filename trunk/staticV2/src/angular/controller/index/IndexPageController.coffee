angular.module("cdojV2").controller("IndexPageController", [
  "$rootScope", "$scope", "ArticleService", "$timeout"
  ($rootScope, $scope, articleService, $timeout) ->
    $rootScope.$broadcast("pageChangeEvent", "Home")
    $rootScope.$broadcast("pageTitleChangeEvent", "Home", "")

    $scope.articleList = []
    $scope.onLoading = true

    articleService.getAllNotice((data) ->
      # Do something
      $timeout(->
        $scope.articleList = data.list
        $scope.onLoading = false
      , 10)
    , (data) ->
      $scope.onLoading = false

      # Do something
      #setIsLoading(false)
    )
])