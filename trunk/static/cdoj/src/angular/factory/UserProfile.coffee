cdoj.factory("UserProfile", [
  "$http", "$window", "$rootScope"
  ($http, $window, $rootScope)->
    userProfile = 0

    setProfile: (userName)->
      $http.get("/user/profile/#{userName}").success((data)->
        if data.result == "success"
          userProfile = data.user
          $rootScope.$broadcast("UserProfile:success")
        else
          $window.alert data.error_msg
      )
    getProfile: ->
      userProfile
])