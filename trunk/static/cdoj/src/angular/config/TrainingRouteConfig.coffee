cdoj
.config([
    "$routeProvider",
    ($routeProvider) ->
      $routeProvider.when("/training/list",
        templateUrl: "template/training/list.html"
        controller: "TrainingListController"
      ).when("/training/editor/:action",
        templateUrl: "template/training/editor.html"
        controller: "TrainingEditorController"
        resolve:
          training: ["$q", "$route", "$http", "Error",
            ($q, $route, $http, $Error) ->
              deferred = $q.defer()
              action = $route.current.params.action
              if action != "new"
                trainingId = action
                $http.post("/training/data/#{trainingId}").success((data) ->
                  if data.result == "success"
                    data.training.action = action
                    deferred.resolve(data.training)
                  else
                    $Error.error(data.error_msg)
                ).error(->
                  $Error.error("Network error!")
                )
              else
                deferred.resolve(
                  action: action
                  description: ""
                  title: ""
                )
              return deferred.promise
          ]
      )
    ])