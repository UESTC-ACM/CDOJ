cdoj
.controller("TrainingShowController", [
    "$scope", "$http", "$rootScope", "$window", "trainingDto", "$modal"
    ($scope, $http, $rootScope, $window, trainingDto, $modal) ->
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.NOOP
      )
      $window.scrollTo(0, 0)

      $scope.trainingDto = trainingDto
      _trainingUserCriteria = angular.copy($rootScope.trainingUserCriteria)
      _trainingUserCriteria.trainingId = trainingDto.trainingId
      $scope.trainingUserCriteria = _trainingUserCriteria

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
        modalInstance.result.then( ->
          $scope.$broadcast("list:refresh:trainingRankList")
        )

      $scope.addNewMember = ->
        modalInstance = $modal.open(
          templateUrl: "template/modal/training-member-editor-modal.html"
          controller: "TrainingMemberEditorController"
          resolve:
            action: ->
              "new"
            trainingUserDto: ->
              trainingId: trainingDto.trainingId
              trainingUserName: ""
              type: 0
        )
        modalInstance.result.then( ->
          $scope.$broadcast("list:refresh:trainingRankList")
        )
  ])