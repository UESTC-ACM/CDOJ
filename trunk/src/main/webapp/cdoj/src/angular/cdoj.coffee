cdoj = angular.module('cdoj', [
  "ui.bootstrap"
  "ngRoute"
])
cdoj
.run([
  "$rootScope", "$http"
  ($rootScope, $http)->
    _.extend($rootScope, GlobalVariables)
    $http.get("/globalData").then (response)->
      data = response.data
      if data.result == "error"
        alert(data.error_msg)
      else
        _.extend($rootScope, data)
])
.config([
    "$routeProvider",
    ($routeProvider)->
      $routeProvider.when("/problem/list",
        templateUrl: "template/problem/list.html"
        controller: "ProblemListController"
      )
  ])