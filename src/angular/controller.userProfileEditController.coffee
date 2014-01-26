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
]
)
cdoj.controller("UserProfileEditController", [
  "$scope", "$http", "UserProfile"
  ($scope, $http, $userProfile)->
    $scope.userEditDTO = 0
    $scope.$watch(
      ->
        $userProfile.getProfile()
    ,
      ->
        $scope.userEditDTO = $userProfile.getProfile()
    , true)
])