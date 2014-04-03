cdoj.controller("ProblemShowController", [
  "$scope", "$rootScope", "$window", "$modal", "problem"
  ($scope, $rootScope, $window, $modal, problem) ->
    $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.NOOP)
    $window.scrollTo(0, 0)

    $scope.problem = problem

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
      ).result.then (result) ->
        if result == "success"
          $window.location.href = "/#/status/list?problemId=" +
            $scope.problem.problemId
    $scope.gotoStatusList = ->
      # TODO
      $window.location.href = "/#/status/list?problemId=" +
        $scope.problem.problemId
])
