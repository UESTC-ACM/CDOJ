cdoj.controller("ProblemShowController", [
  "$scope", "$rootScope", "$http", "$window", "$routeParams", "$modal"
  ($scope, $rootScope, $http, $window, $routeParams, $modal)->
    $scope.problem =
      description: ""
      title: ""
      isSpj: false
      timeLimit: 1000
      javaTimeLimit: 3000
      memoryLimit: "--"
      javaMemoryLimit: "--"
      solved: 0
      tried: 0
      input: ""
      output: ""
      sampleInput: ""
      sampleOutput: ""
      hint: ""
      source: ""
    $scope.submitDTO =
      codeContent: ""
      languageId: 1
      contestId: 0
      problemId: 0
    $scope.problemId = 0
    $scope.contest = null
    $scope.fieldInfo = []

    problemId = angular.copy($routeParams.problemId)
    $http.post("/problem/data/#{problemId}").then (response)->
      data = response.data
      if data.result == "success"
        $scope.problem = data.problem
        $rootScope.title = $scope.problem.title
      else
        alert data.error_msg

    $scope.openSubmitModal = ->
      $modal.open(
        templateUrl: "template/modal/submit-modal.html"
        controller: "SubmitModalController"
        resolve:
          submitDTO: ->
            codeContent: ""
            problemId: $scope.problem.problemId
            contestId: null
            languageId: 2 #default C++
          title: ->
            "#{$scope.problem.title}"
      ).result.then (result)->
        if result == "success"
          console.log "fuck"
])
