cdojV2
.config([
    "$routeProvider",
    ($routeProvider) ->
      $routeProvider.when("/",
        templateUrl: "template/index/index.html"
        controller: "IndexController"
      ).when("/404/",
        templateUrl: "template/index/404.html"
      ).otherwise(
        redirectTo: "/404/"
      )
  ])