cdoj
.controller("TrainingContestEditorController", [
    "$scope", "$http", "trainingContestData", "$window"
    ($scope, $http, trainingContestData, $window) ->

      $scope.action = trainingContestData.action
      $scope.trainingContestDto = trainingContestData.trainingContestDto
      if $scope.action == "new"
        $scope.title = "Add new contest"
      else
        $scope.title = "Edit"
      $scope.fieldInfo = []

      $scope.fieldTypeList = [
        {id: 0, description: "Unused"},
        {id: 1, description: "User name"},
        {id: 2, description: "Problem"}
      ]
  ])