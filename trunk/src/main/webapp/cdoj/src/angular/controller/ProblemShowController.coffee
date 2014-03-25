cdoj.controller("ProblemShowController", [
  "$scope", "$rootScope", "$http", "$window", "$routeParams", "$modal"
  ($scope, $rootScope, $http, $window, $routeParams, $modal)->
    $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.NOOP)
    $window.scrollTo(0, 0)
    $scope.problem =
      description: ""
      title: ""
      isSpj: false
      timeLimit: 1000
      javaTimeLimit: 3000
      memoryLimit: 65536
      javaMemoryLimit: "--"
      solved: 0
      tried: 0
      input: ""
      output: ""
      sampleInput: ""
      sampleOutput: ""
      hint: ""
      source: ""

    problemId = angular.copy($routeParams.problemId)
    $http.post("/problem/data/#{problemId}").success((data)->
      if data.result == "success"
        $scope.problem = data.problem
        $rootScope.title = $scope.problem.title
      else
        $window.alert data.error_msg
    ).error(->
      $window.alert "Network error, please refresh page manually."
    )

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
          $window.location.href = "/#/status/list?problemId=#{$scope.problem.problemId}"
    $scope.gotoStatusList = ->
      # TODO
      $window.location.href = "/#/status/list?problemId=#{$scope.problem.problemId}"
])
