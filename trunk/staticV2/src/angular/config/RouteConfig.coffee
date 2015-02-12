cdojV2
.config([
    "$routeProvider",
    ($routeProvider) ->
      $routeProvider.when("/",
        templateUrl: "templateV2/index/index.html"
      ).when("/404/",
        templateUrl: "templateV2/index/404.html"
      ).otherwise(
        redirectTo: "/404/"
      )
  ])