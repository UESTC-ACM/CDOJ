cdoj = angular.module('cdoj', [
  "ui.bootstrap"
  "ngRoute"
])

cdoj.config([
  "$routeProvider",
  ($routeProvider)->
    $routeProvider.when("/problem/list",
      templateUrl: "template/problem/list.html"
      controller: ProblemListController
    )
])