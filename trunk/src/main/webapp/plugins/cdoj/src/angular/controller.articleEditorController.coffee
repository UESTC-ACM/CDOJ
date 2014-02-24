cdoj.controller("ArticleEditorController", [
  "$scope", "$http", "$window"
  ($scope, $http, $window)->
    $scope.article =
      content: ""
      title: ""
    $scope.action = "new"
    $scope.fieldInfo = []
    $scope.$watch("action",
    ->
      if $scope.action != "new"
        articleId = angular.copy($scope.action)
        $http.get("/article/data/ArticleEditorShowDTO/#{articleId}").then (response)->
          data = response.data
          if data.result == "success"
            $scope.article = data.article
          else
            $window.alert data.error_msg
    )
    $scope.submit = ->
      articleEditDTO = angular.copy($scope.article)
      articleEditDTO.action = angular.copy($scope.action)
      $http.post("/article/edit", articleEditDTO).then (response)->
        data = response.data
        if data.result == "success"
          $window.location.href = "/article/show/#{data.articleId}"
        else if data.result == "field_error"
          $scope.fieldInfo = data.field
        else
          $window.alert data.error_msg
])