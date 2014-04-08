cdoj
.config([
    "$routeProvider",
    ($routeProvider) ->
      $routeProvider.when("/contest/list",
        templateUrl: "template/contest/list.html"
        controller: "ContestListController"
      ).when("/contest/show/:contestId",
        templateUrl: "template/contest/show.html"
        controller: "ContestShowController"
        resolve:
          contest: ["$q", "$route", "$http", "Error"
            ($q, $route, $http, $Error) ->
              deferred = $q.defer()
              contestId = $route.current.params.contestId
              $http.get("/contest/data/#{contestId}").success((data) ->
                if data.result == "success"
                  contest = data.contest
                  contest.problemList = data.problemList
                  deferred.resolve(contest)
                else
                  $Error.error data.error_msg
              ).error(->
                $Error.error "Network error."
              )
              return deferred.promise
          ]
      ).when("/contest/editor/:action",
        templateUrl: "template/contest/editor.html"
        controller: "ContestEditorController"
        resolve:
          contest: ["$q", "$route", "$http", "Error", "$rootScope"
            ($q, $route, $http, $Error, $rootScope) ->
              deferred = $q.defer()
              action = $route.current.params.action
              contest =
                action: action
                type: $rootScope.ContestType.PUBLIC
                time: Date.create().format("{yyyy}-{MM}-{dd} {HH}:{mm}")
                lengthMinutes: 0
                lengthHours: 5
                lengthDays: 0
              if action != "new"
                contestId = action
                $http.post("/contest/data/#{contestId}").success((data) ->
                  if data.result == "success"
                    contest.action = action
                    contest.contestId = data.contest.contestId
                    contest.title = data.contest.title
                    contest.type = data.contest.type
                    contest.time =
                      Date
                      .create(data.contest.startTime)
                      .format("{yyyy}-{MM}-{dd} {HH}:{mm}")
                    length = Math.floor(data.contest.length / 1000)
                    length = Math.floor(length / 60)
                    contest.lengthMinutes = length % 60
                    length = Math.floor(length / 60)
                    contest.lengthHours = length % 24
                    length = Math.floor(length / 24)
                    contest.lengthDays = length
                    contest.description = data.contest.description
                    if angular.isDefined data.contest.parentId
                      contest.type = $rootScope.ContestType.INHERIT
                      contest.parentId = data.contest.parentId
                    contest.problemList = data.problemList
                    deferred.resolve(contest)
                  else
                    $Error.error(data.error_msg)
                ).error(->
                  $Error.error("Network error!")
                )
              else
                deferred.resolve(contest)
              return deferred.promise
          ]
      ).when("/contest/register/:contestId",
        templateUrl: "template/contest/register.html"
        controller: "ContestRegisterController"
        resolve:
          contest: ["$q", "$route", "$http", "Error", "$rootScope"
            ($q, $route, $http, $Error, $rootScope) ->
              deferred = $q.defer()
              contestId = $route.current.params.contestId
              contestCondition = angular.copy($rootScope.contestCondition)
              contestCondition.startId = contestCondition.endId = contestId
              $http.post(
                "/contest/search"
                contestCondition
              ).success((data) ->
                if data.result == "success"
                  if data.list.length != 1
                    $Error.error "Incorrect contest ID!"
                  contest = data.list[0]
                  if contest.type != $rootScope.ContestType.INVITED
                    $Error.error "Incorrect contest ID!"
                  deferred.resolve(contest)
                else
                  $Error.error data.error_msg
              ).error(->
                $Error.error "Network error!"
              )
              return deferred.promise
          ]
      )
  ])