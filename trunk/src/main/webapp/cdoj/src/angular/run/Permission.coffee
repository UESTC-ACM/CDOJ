cdoj
.run([
    "$rootScope", "$window"
    ($rootScope, $window)->
      $rootScope.currentPermission = $rootScope.AuthenticationType.NOOP
      # Permission check
      $rootScope.$on("permission:setPermission", (e, permission)->
        $rootScope.currentPermission = permission
      )
      $rootScope.$on("permission:check", ->
        if $rootScope.currentPermission == $rootScope.AuthenticationType.ADMIN
          if $rootScope.isAdmin == false
            $window.alert("Permission denied!")
            $window.history.back()
        else if $rootScope.currentPermission == $rootScope.AuthenticationType.NORMAL
          if $rootScope.hasLogin == false
            $window.alert("Please login first!")
            $window.history.back()
      )
  ])