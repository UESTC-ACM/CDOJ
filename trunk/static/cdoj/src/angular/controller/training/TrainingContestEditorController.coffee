cdoj
.controller("TrainingContestEditorController", [
    "$scope", "$http", "trainingContestData", "$window"
    ($scope, $http, trainingContestData, $window) ->

      $scope.action = trainingContestData.action
      $scope.trainingContestDto = trainingContestData.trainingContestDto
      $scope.rankList = trainingContestData.rankList
      if $scope.action == "new"
        $scope.title = "Add new contest"
      else
        $scope.action = "edit"
        $scope.title = "Edit"
      $scope.fieldInfo = []

      $scope.fieldTypeList = [
        {id: 0, description: "Unused"},
        {id: 1, description: "User name"},
        {id: 2, description: "Problem"}
      ]

      $scope.edit = ->
        console.log $scope.action, $scope.trainingContestDto, $scope.rankList
        $http.post("/training/editTrainingContest",
          action: $scope.action
          trainingContestEditDto: $scope.trainingContestDto
          trainingRankList: $scope.rankList
        ).success((data) ->
          if data.result == "success"
            $window.location.href = "/#/training/contest/" +
              $scope.trainingContestDto.trainingId +
              "/show/" + data.trainingContestId
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
  ])