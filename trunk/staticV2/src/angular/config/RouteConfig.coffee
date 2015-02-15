angular.module("cdojV2").config([
  "$routeProvider",
  ($routeProvider) ->
    $routeProvider.when("/",
      redirectTo: "/home/"
    ).when("/home/",
      templateUrl: getTemplateUrl("index", "index")
      controller: "IndexPageController"
    ).when("/404/",
      templateUrl: getTemplateUrl("index", "404")
    ).otherwise(
      redirectTo: "/404/"
    )
])