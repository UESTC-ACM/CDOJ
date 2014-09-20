cdoj
.controller("TrainingContestShowController", [
    "$scope", "$http", "trainingContestData"
    ($scope, $http, trainingContestData) ->

      $scope.trainingContestDto = trainingContestData.trainingContestDto
      $scope.rankList = trainingContestData.rankList
  ])