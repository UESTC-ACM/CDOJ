cdoj = angular.module('cdoj', [
  "ui.bootstrap"
  "ngRoute"
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
      ).when("/problem/list",
        templateUrl: "template/problem/list.html"
        controller: "ProblemListController"
      )
  ])