cdoj.factory("UserProfile", [
  "$http", "$window"
  ($http, $window)->
    userProfile = 0

    setProfile: (userName)->
      $http.get("/user/profile/#{userName}").then (response)->
        data = response.data
        if data.result == "success"
          userProfile = data.user
        else
          $window.alert data.error_msg
    getProfile: -> userProfile
])