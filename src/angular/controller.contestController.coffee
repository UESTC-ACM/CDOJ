cdoj.controller("ContestController", [
  "$scope", "$rootScope", "$http", "$window"
  ($scope, $rootScope, $http, $window) ->
    $scope.contestId = 0
    $scope.contest =
      title: ""
    $scope.problemList = []
    $scope.currentProblem =
      description: ""
      title: ""
      input: ""
      output: ""
      sampleInput: ""
      sampleOutput: ""
      hint: ""
      source: ""
    $scope.$watch("contestId",
    ->
      contestId = angular.copy($scope.contestId)
      $http.get("/contest/data/#{contestId}").then (response)->
        data = response.data
        if data.result == "success"
          $scope.contest = data.contest
          $scope.problemList = data.problemList
          $rootScope.title = data.contest.title
          if data.problemList.length > 0
            $scope.currentProblem = data.problemList[0]
        else
          $window.alert data.error_msg
    )
    $scope.chooseProblem = (order)->
      $scope.showProblem = true
      $scope.currentProblem = _.findWhere($scope.problemList, order: order)
])