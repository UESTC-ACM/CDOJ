cdoj
.config([
    "$routeProvider",
    ($routeProvider) ->
      $routeProvider.when("/problem/list",
        templateUrl: "template/problem/list.html"
        controller: "ProblemListController"
      ).when("/problem/show/:problemId",
        templateUrl: "template/problem/show.html"
        controller: "ProblemShowController"
        resolve:
          problem: ["$q", "$route", "$http", "Error"
            ($q, $route, $http, $Error) ->
              deferred = $q.defer()
              problemId = $route.current.params.problemId
              $http.post("/problem/data/#{problemId}").success((data) ->
                if data.result == "success"
                  deferred.resolve(data.problem)
                else
                  $Error.error(data.error_msg)
              ).error(->
                $Error.error("Network error!")
              )
              return deferred.promise
          ]
      ).when("/problem/editor/:action",
        templateUrl: "template/problem/editor.html"
        controller: "ProblemEditorController"
        resolve:
          problem: ["$q", "$route", "$http", "Error",
            ($q, $route, $http, $Error) ->
              deferred = $q.defer()
              action = $route.current.params.action
              if action != "new"
                problemId = action
                $http.post("/problem/data/#{problemId}").success((data) ->
                  if data.result == "success"
                    data.problem.action = action

                    _sampleInput = data.problem.sampleInput
                    try
                      _sampleInput = JSON.parse _sampleInput

                    if _sampleInput not instanceof Array
                      _sampleInput = [_sampleInput]

                    _sampleOutput = data.problem.sampleOutput
                    try
                      _sampleOutput = JSON.parse _sampleOutput

                    if _sampleOutput not instanceof Array
                      _sampleOutput = [_sampleOutput]

                    if _sampleInput.length != _sampleOutput.length
                      $Error.error("Sample input has not same number of cases
                       with sample output!")
                    else
                      data.problem.samples = _.map(
                        _.zip(_sampleInput, _sampleOutput)
                        (sample) ->
                          input: sample[0].toString()
                          output: sample[1].toString()
                      )
                    deferred.resolve(data.problem)
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
                  isSpj: false
                  timeLimit: 1000
                  memoryLimit: 64
                  outputLimit: 64
                  isVisible: false
                  input: ""
                  output: ""
                  sampleInput: ""
                  sampleOutput: ""
                  hint: ""
                  source: ""
                )
              return deferred.promise
          ]
      )
  ])