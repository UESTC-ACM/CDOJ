cdoj
.run([
    "$rootScope", "$window", "Error"
    ($rootScope, $window, Error) ->
      $rootScope.currentPermission =
        value: $rootScope.AuthenticationType.NOOP
        userName: undefined
      $rootScope.editPermission =
        userName: undefined
      $rootScope.hasEditPermission = false
      # Permission check
      $rootScope.$on(
        "permission:setPermission"
        (e, permission, userName) ->
          $rootScope.currentPermission =
            value: permission
            userName: userName
      )
      $rootScope.$on(
        "permission:setEditPermission"
        (e, userName) ->
          $rootScope.editPermission =
            userName: userName
      )
      $rootScope.$on(
        "permission:check"
        ->
          if (
              $rootScope.currentPermission.value ==
              $rootScope.AuthenticationType.ADMIN
          )
            if $rootScope.isAdmin == false
              Error.error "Permission denied!"
          else if (
              $rootScope.currentPermission.value ==
              $rootScope.AuthenticationType.NORMAL
          )
            if $rootScope.hasLogin == false
              Error.error "Please login first!"
          else if (
              $rootScope.currentPermission.value ==
              $rootScope.AuthenticationType.CURRENT_USER
          )
            if (
                $rootScope.hasLogin == false || (
                  $rootScope.isAdmin == false &&
                  $rootScope.currentUser.userName !=
                  $rootScope.currentPermission.userName
                )
            )
              Error.error "Permission denied!"

          if $rootScope.hasLogin == false
            $rootScope.hasEditPermission = false
          else if (
            $rootScope.currentUser.type !=
              $rootScope.AuthenticationType.CONSTANT &&
            $rootScope.currentUser.userName ==
              $rootScope.editPermission.userName
          )
            $rootScope.hasEditPermission = true

          $rootScope.$broadcast("permission:changed")
      )
  ])