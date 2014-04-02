cdoj
.config([
    "$routeProvider",
    ($routeProvider)->
      $routeProvider.when("/problem/list",
        templateUrl: "template/problem/list.html"
        controller: "ProblemListController"
      ).when("/problem/show/:problemId",
        templateUrl: "template/problem/show.html"
        controller: "ProblemShowController"
        resolve:
          problem: ["$q", "$route", "$http", "$window"
            ($q, $route, $http, $window)->
              deferred = $q.defer()
              problemId = $route.current.params.problemId
              $http.post("/problem/data/#{problemId}").success((data)->
                if data.result == "success"
                  deferred.resolve(data.problem)
                else
                  $window.alert(data.error_msg)
                  $window.location.href = "/#/problem/list"
              ).error(->
                $window.alert("Network error, please refresh page manually.")
                $window.location.href = "/#/problem/list"
              )
              return deferred.promise
          ]
      ).when("/problem/editor/:action",
        templateUrl: "template/problem/editor.html"
        controller: "ProblemEditorController"
        resolve:
          problem: ["$q", "$route", "$http", "$window",
            ($q, $route, $http, $window)->
              deferred = $q.defer()
              action = $route.current.params.action
              if action != "new"
                problemId = action
                $http.post("/problem/data/#{problemId}").success((data)->
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
                      $window.alert "Sample input has not same number of cases with sample output!"
                      $window.location.href = "/#/problem/list"
                    else
                      data.problem.samples = ({input: _sampleInput[i].toString(), output: _sampleOutput[i].toString()} for i in [0.._sampleInput.length - 1])

                    deferred.resolve(data.problem)
                  else
                    $window.alert data.error_msg
                    $window.location.href = "/#/problem/list"
                ).error(->
                  $window.alert("Network error, please refresh page manually.")
                  $window.location.href = "/#/problem/list"
                )
              else
                deferred.resolve(
                  action: action
                  description: ""
                  title: ""
                  isSpj: false
                  timeLimit: 1000
                  javaTimeLimit: 3000
                  memoryLimit: 65535
                  javaMemoryLimit: 65535
                  outputLimit: 65535
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