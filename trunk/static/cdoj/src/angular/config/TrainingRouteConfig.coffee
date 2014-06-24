cdoj
.config([
    "$routeProvider",
    ($routeProvider) ->
      $routeProvider.when("/training/list",
        templateUrl: "template/training/list.html"
        controller: "TrainingListController"
      )
    ])