cdoj
.run([
    "$rootScope", "$http", "$timeout", "$window"
    ($rootScope, $http, $timeout, $window)->
      _.extend($rootScope, GlobalVariables)
      _.extend($rootScope, GlobalConditions)

      $rootScope.finalTitle = "UESTC Online Judge"

      fetchGlobalData = ->
        $http.get("/globalData").success((data)->
          _.extend($rootScope, data)
        ).error(->
          $timeout(fetchGlobalData, 500)
        )
      fetchData = ->
        $http.get("/data").success((data)->
          if data.result == "success"
            if $rootScope.hasLogin && data.hasLogin == false
              $window.alert "You has been logged out by server."
              $rootScope.$broadcast("currentUser:logout")
            _.extend($rootScope, data)
            $rootScope.$broadcast("currentUser:updated")
          $timeout(fetchData, 10000)
        ).error(->
          $timeout(fetchData, 500)
        )

      # Refresh event
      $rootScope.$on("globalData:refresh", ->
        $timeout(fetchGlobalData, 0)
      )
      $rootScope.$on("data:refresh", ->
        $timeout(fetchData, 0)
      )
  ])
