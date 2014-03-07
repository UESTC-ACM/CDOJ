_.mixin(_.str.exports());

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
      ).when("/contest/list",
        templateUrl: "template/contest/list.html"
        controller: "ContestListController"
      ).when("/contest/show/:contestId",
        templateUrl: "template/contest/show.html"
        controller: "ContestShowController"
      ).when("/contest/editor/:action",
        templateUrl: "template/contest/editor.html"
        controller: "ContestEditorController"
      ).when("/contest/register/:contestId",
        templateUrl: "template/contest/register.html"
        controller: "ContestRegisterController"
      ).when("/status/list",
        templateUrl: "template/status/list.html"
        controller: "StatusListController"
      ).when("/user/list",
        templateUrl: "template/user/list.html"
        controller: "UserListController"
      ).when("/user/center/:userName",
        templateUrl: "template/user/center.html"
        controller: "UserCenterController"
      ).when("/user/activate/:userName/:serialKey",
        templateUrl: "template/user/activation.html"
        controller: "PasswordResetController"
      ).when("/team/list",
        templateUrl: "template/team/list.html"
        controller: "TeamListController"
      )
  ])