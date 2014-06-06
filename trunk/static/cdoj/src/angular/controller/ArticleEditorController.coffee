cdoj
.controller("ArticleEditorController", [
    "$rootScope", "$scope", "$http", "$routeParams", "$window"
    ($rootScope, $scope, $http, $routeParams, $window) ->
      # Current user only
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.CURRENT_USER
        $routeParams.userName
      )
      $window.scrollTo(0, 0)

      $scope.article =
        content: ""
        title: ""
      $scope.fieldInfo = []
      $scope.action = $routeParams.action
      $scope.userName = $routeParams.userName

      if $scope.action != "new"
        articleId = angular.copy($scope.action)
        $http.get("/article/data/#{articleId}").success((data) ->
          if data.result == "success"
            $scope.article = data.article
            $scope.title = "Edit: " + $scope.article.title
            $scope.isNotice =
                $scope.article.type == $rootScope.ArticleType.NOTICE
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error, please refresh page manually."
        )
      else
        $scope.title = "New article"

      $scope.submit = ->
        articleEditDto = angular.copy($scope.article)
        if $scope.isNotice
          articleEditDto.type = $rootScope.ArticleType.NOTICE
        else
          articleEditDto.type = $rootScope.ArticleType.ARTICLE
        $http.post("/article/edit",
          action: angular.copy($scope.action)
          articleEditDto: articleEditDto
        ).success((data) ->
          if data.result == "success"
            $window.location.href = "/#/article/show/#{data.articleId}"
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
  ])