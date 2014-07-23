cdoj
.controller("TrainingShowController", [
    "$scope", "$http", "$rootScope", "$window"
    "trainingDto", "trainingUserList", "trainingContestList", "$modal"
    ($scope, $http, $rootScope, $window
     trainingDto, trainingUserList, trainingContestList, $modal) ->
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.NOOP
      )
      $window.scrollTo(0, 0)

      $scope.trainingDto = trainingDto
      $scope.trainingUserList = trainingUserList
      $scope.trainingContestList = trainingContestList

      $scope.editTrainingUser = (trainingUserId) ->
        modalInstance = $modal.open(
          templateUrl: "template/modal/training-member-editor-modal.html"
          controller: "TrainingMemberEditorController"
          resolve:
            action: ->
              "edit"
            trainingUserData: ["$q", "$http", "Error"
              ($q, $http, $Error) ->
                deferred = $q.defer()
                $http.get(
                  "/training/trainingUserData/#{trainingUserId}"
                ).success((data) ->
                  if data.result == "success"
                    deferred.resolve(data)
                  else
                    $Error.error(data.error_msg)
                ).error(->
                  $Error.error("Network error!")
                )
                return deferred.promise
            ]
        )
        modalInstance.result.then(->
          $scope.$broadcast("list:refresh:trainingRankList")
        )

      $scope.addNewMember = ->
        modalInstance = $modal.open(
          templateUrl: "template/modal/training-member-editor-modal.html"
          controller: "TrainingMemberEditorController"
          resolve:
            action: ->
              "new"
            trainingUserData: ->
              trainingUserDto:
                trainingId: trainingDto.trainingId
                trainingUserName: ""
                type: 0
              trainingPlatformList: []
        )
        modalInstance.result.then(->
          $scope.$broadcast("list:refresh:trainingRankList")
        )

      $scope.calculatingRating = false
      $scope.calculateRating = ->
        $scope.calculatingRating = true
        $http.get(
            "/training/calculateRating/" + $scope.trainingDto.trainingId
        ).success((data) ->
          $scope.calculatingRating = false
          if data.result == "success"
            $window.alert("Success!")
            $scope.$broadcast("list:refresh:trainingRankList")
          else
            $window.alert(data.error_msg)
        ).error(->
          $scope.calculatingRating = false
          $window.alert("Network error!")
        )

      $scope.ratingColor = (rating) ->
        if (rating < 900)
          return "rating-gray"
        else if (rating < 1200)
          return "rating-green"
        else if (rating < 1500)
          return "rating-blue"
        else if (rating < 2200)
          return "rating-yellow"
        else
          return "rating-red"

      $scope.formatDouble = (rating) ->
        return _.sprintf("%.0f", rating)
  ])