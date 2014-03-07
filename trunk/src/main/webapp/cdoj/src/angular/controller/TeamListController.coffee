cdoj
.controller("TeamListController", [
    "$scope", "$rootScope", "$http", "$window"
    ($scope, $rootScope, $http, $window)->
      $scope.hideMemberPanel = true
      $scope.teamDTO =
        teamName: ""

      $scope.toggleMemberPanel = ->
        teamName = angular.copy($scope.teamDTO.teamName)
        if teamName == ""
          $window.alert "Please enter a valid team name."
        else if $scope.hideMemberPanel
          $http.get("/team/checkTeamExists/#{teamName}").then (response)->
            data = response.data
            if data.result == "error"
              $window.alert data.error_msg
            else if data.result == false
              $scope.hideMemberPanel = false
            else
              $window.alert "Team name has been used!"
])