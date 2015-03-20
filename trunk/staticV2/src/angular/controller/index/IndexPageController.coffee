angular.module("cdojV2").controller("IndexPageController", [
  "$rootScope", "$scope", "ArticleService"
  ($rootScope, $scope, articleService) ->
    $rootScope.$broadcast("pageChangeEvent", "Home")
    $rootScope.$broadcast("pageTitleChangeEvent", "Home", "")

    $scope.articleList = []
    $scope.onLoading = true

    articleService.getAllNotice().then((data) ->
      $scope.articleList = data.list
    , (reason) ->
      console.log(reason)
    ).finally(->
      $scope.onLoading = false
    )
])