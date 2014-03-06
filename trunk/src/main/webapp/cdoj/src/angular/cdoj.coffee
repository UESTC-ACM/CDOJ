cdoj = angular.module('cdoj', [
  "ui.bootstrap"
  "ngRoute"
  "monospaced.elastic"
])
cdoj
.run([
  "$rootScope", "$http"
  ($rootScope, $http)->
    _.extend($rootScope, GlobalVariables)
    _.extend($rootScope, GlobalConditions)
    $http.get("/globalData").then (response)->
      data = response.data
      if data.result == "error"
        alert(data.error_msg)
      else
        _.extend($rootScope, data)
    $rootScope.finalTitle = "UESTC Online Judge"
])
.config([
    "$routeProvider",
    ($routeProvider)->
      $routeProvider.when("/",
        templateUrl: "template/index/index.html"
        $controller: "IndexController"
      ).when("/article/show/:articleId",
        templateUrl: "template/article/show.html"
        controller: "ArticleShowController"
      ).when("/article/editor/:action",
        templateUrl: "template/article/editor.html"
        controller: "ArticleEditorController"
      ).when("/problem/list",
        templateUrl: "template/problem/list.html"
        controller: "ProblemListController"
      ).when("/problem/show/:problemId",
        templateUrl: "template/problem/show.html"
        controller: "ProblemShowController"
      ).when("/problem/editor/:action",
        templateUrl: "template/problem/editor.html"
        controller: "ProblemEditorController"
      )
  ])