cdoj
.config([
    "$routeProvider",
    ($routeProvider) ->
      $routeProvider.when("/user/list",
        templateUrl: "template/user/list.html"
        controller: "UserListController"
      ).when("/user/center/:userName/:tab",
        templateUrl: "template/user/center.html"
        controller: "UserCenterController"
        resolve:
          targetUser: ["$q", "$route", "$http", "Error", "$rootScope"
            ($q, $route, $http, $Error, $rootScope) ->
              deferred = $q.defer()
              userName = $route.current.params.userName
              $http.get("/user/userCenterData/#{userName}").success((data) ->
                if data.result == "success"
                  deferred.resolve(
                    targetUser: data.targetUser
                    problemStatus: data.problemStatus
                  )
                else
                  $Error.error(data.error_msg)
              ).error(->
                $Error.error("Network error!")
              )
              return deferred.promise
          ]
          userEditDTO: ["$q", "$route", "$http", "Error", "$rootScope"
            ($q, $route, $http, $Error, $rootScope) ->
              deferred = $q.defer()
              userName = $route.current.params.userName
              tab = $route.current.params.tab
              if tab == "edit"
                $http.get(
                    "/user/profile/" + userName
                ).success((data) ->
                  if data.result == "success"
                    deferred.resolve(data.user)
                  else
                    $Error.error(data.error_msg)
                ).error(->
                  $Error.error("Network error!")
                )
              else
                deferred.resolve(null)
              return deferred.promise
          ]
      ).when("/user/center/:userName",
        templateUrl: "template/user/center.html"
        controller: "UserCenterController"
        resolve:
          targetUser: ->
            targetUser: undefined
            problemStatus: undefined
          userEditDTO: -> undefined
      ).when("/user/activate/:userName/:serialKey",
        templateUrl: "template/user/activation.html"
        controller: "PasswordResetController"
      ).when("/user/register",
        templateUrl: "template/user/register.html"
        controller: "UserRegisterController"
      )
  ])