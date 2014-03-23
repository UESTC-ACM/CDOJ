cdoj
.run([
    "$rootScope", "$window"
    ($rootScope, $window)->
      $rootScope.currentPermission =
        value: $rootScope.AuthenticationType.NOOP
        userName: undefined
      $rootScope.editPermission =
        userName: undefined
      $rootScope.hasEditPermission = false
      # Permission check
      $rootScope.$on("permission:setPermission", (e, permission, userName)->
        $rootScope.currentPermission =
          value: permission
          userName: userName
      )
      $rootScope.$on("permission:setEditPermission", (e, userName)->
        $rootScope.editPermission =
          userName: userName
      )
      $rootScope.$on("permission:check", ->
        if $rootScope.currentPermission.value == $rootScope.AuthenticationType.ADMIN
          if $rootScope.isAdmin == false
            $window.alert("Permission denied!")
            $window.history.back()
        else if $rootScope.currentPermission.value == $rootScope.AuthenticationType.NORMAL
          if $rootScope.hasLogin == false
            $window.alert("Please login first!")
            $window.history.back()
        else if $rootScope.currentPermission.value == $rootScope.AuthenticationType.CURRENT_USER
          if $rootScope.hasLogin == false || ($rootScope.isAdmin == false && $rootScope.currentUser.userName != $rootScope.currentPermission.userName)
            $window.alert("Permission denied!")
            $window.history.back()

        if $rootScope.hasLogin == false
          $rootScope.hasEditPermission = false
        else if $rootScope.currentUser.userName == $rootScope.editPermission.userName
          $rootScope.hasEditPermission = true

        $rootScope.$broadcast("permission:changed")
      )
  ])