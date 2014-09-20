cdoj
.run([
    "$rootScope"
    ($rootScope) ->
      $rootScope.hasLogin = false
      $rootScope.currentUser =
        email: ""
      $rootScope.isAdmin = false

      $rootScope.$on("currentUser:login", (e, currentUser) ->
        _.extend($rootScope.currentUser, currentUser)
        $rootScope.hasLogin = true
        $rootScope.$broadcast("currentUser:changed")
      )
      $rootScope.$on("currentUser:logout", ->
        $rootScope.hasLogin = false
        $rootScope.currentUser =
          email: ""
        $rootScope.$broadcast("currentUser:changed")
      )
      $rootScope.$on("currentUser:changed", ->
        $rootScope.$broadcast("currentUser:updated")
      )
      $rootScope.$on("currentUser:updated", ->
        if $rootScope.hasLogin && $rootScope.currentUser.type == 1
          $rootScope.isAdmin = true
        else
          $rootScope.isAdmin = false
        # Check permission after current user changed
        $rootScope.$broadcast("permission:check")
      )
  ])