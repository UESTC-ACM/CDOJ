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
          trainingDto: ["$q", "$route", "$http", "Error",
            ($q, $route, $http, $Error) ->
              deferred = $q.defer()
              action = $route.current.params.action
              if action != "new"
                trainingId = action
                $http.post("/training/data/#{trainingId}").success((data) ->
                  if data.result == "success"
                    data.trainingDto.action = action
                    deferred.resolve(data.trainingDto)
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
      ).when("/training/show/:trainingId",
        templateUrl: "template/training/show.html"
        controller: "TrainingShowController"
        resolve:
          trainingDto: ["$q", "$route", "$http", "Error",
            ($q, $route, $http, $Error) ->
              deferred = $q.defer()
              trainingId = $route.current.params.trainingId
              $http.post("/training/data/#{trainingId}").success((data) ->
                if data.result == "success"
                  deferred.resolve(data.trainingDto)
                else
                  $Error.error(data.error_msg)
              ).error(->
                $Error.error("Network error!")
              )
              return deferred.promise
          ]
      )
    ])