cdoj.controller("ContestController", [
  "$scope", "$rootScope", "$http", "$window", "$modal", "$interval"
  ($scope, $rootScope, $http, $window, $modal, $interval) ->
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
      refreshRankList()
    )

    refreshRankList = ->
      contestId = angular.copy($scope.contestId)
      console.log contestId
      $http.get("/contest/rankList/#{contestId}").then (response)->
        data = response.data
        if data.result == "success"
          $scope.rankList = data.rankList.rankList
          _.each($scope.problemList, (value, index)->
            value.tried = data.rankList.problemList[index].tried
            value.solved = data.rankList.problemList[index].solved
          )
    rankListTimer = $interval(refreshRankList, 10000)

    $scope.showProblemTab = ->
      # TODO Dirty code!
      $scope.$$childHead.tabs[1].select()
    $scope.showStatusTab = ->
      # TODO Dirty code!
      $scope.$$childHead.tabs[3].select()
    $scope.chooseProblem = (order)->
      $scope.showProblemTab()
      $scope.currentProblem = _.findWhere($scope.problemList, order: order)
    $scope.openSubmitModal = ->
      $modal.open(
        templateUrl: "submitModal.html"
        controller: "SubmitModalController"
        resolve:
          submitDTO: ->
            codeContent: ""
            problemId: $scope.currentProblem.problemId
            contestId: $scope.contest.contestId
            languageId: 2 #default C++
          title: ->
            "#{$scope.currentProblem.orderCharacter} - #{$scope.currentProblem.title}"
      ).result.then (result)->
        if result == "success"
          $scope.showStatusTab()
          angular.element("#status-list").scope().refresh()
])
cdoj.directive("uiContestProblemHref"
->
  restrict: "E"
  scope:
    problemId: "="
    problemList: "="
  controller: [
    "$scope"
    ($scope)->
      $scope.order = -1
      $scope.orderCharacter = "-"
      $scope.$watch("problemId + problemList", ->
        target = _.findWhere($scope.problemList, "problemId": $scope.problemId)
        if target != undefined
          $scope.orderCharacter = target.orderCharacter
          $scope.order = target.order
      )
      $scope.select = ->
        if $scope.order != -1
          angular.element("#contest-show").scope().chooseProblem($scope.order)
  ]
  template: """
<a href="#" ng-bind="orderCharacter" ng-click="select()"></a>
"""
  replace: true
)