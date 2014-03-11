cdoj
.controller("ContestRegisterModalController", [
    "$scope", "$rootScope", "$modalInstance", "$http", "contest"
    ($scope, $rootScope, $modalInstance, $http, contest)->
      $scope.contest = contest
      $scope.teamName = ""
      $scope.searchTeam = ->
        console.log contest
        return []
])