cdoj
.controller("ContestShowController", [
    "$scope", "$rootScope", "$http", "$window", "$modal", "$routeParams", "$timeout", "$interval"
    "$cookieStore"
    ($scope, $rootScope, $http, $window, $modal, $routeParams, $timeout, $interval
     $cookieStore) ->
      $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.NOOP)

      $scope.contestId = 0
      $scope.contest =
        title: ""
        description: ""
        currentTime: new Date().getTime()
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
      $scope.progressbar =
        max: 100
        value: 0
        type: "success"
        active: true

      cookieName = "contest" + $scope.contestId
      if angular.isUndefined $cookieStore.get(cookieName)
        $cookieStore.put(cookieName,
          lastClarificationCount: 0
        )

      $scope.contestId = angular.copy($routeParams.contestId)
      $http.get("/contest/data/#{$scope.contestId}").success((data)->
        if data.result == "success"
          $scope.contest = data.contest
          $scope.problemList = data.problemList
          if data.problemList.length > 0
            $scope.currentProblem = data.problemList[0]
        else
          $window.alert data.error_msg
      ).error(->
        $window.alert "Network error, please refresh page manually."
      )

      currentTimeTimer = undefined
      updateTime = ->
        $scope.contest.currentTime = $scope.contest.currentTime + 1000

        # Set progressbar properties
        if $scope.contest.status == $rootScope.ContestStatus.PENDING
          current = 0
          type = "primary"
          active = true
          if $scope.contest.currentTime >= $scope.contest.startTime
            $timeout(->
              $window.location.reload()
            , 500)
        else if $scope.contest.status == $rootScope.ContestStatus.RUNNING
          current = ($scope.contest.currentTime - $scope.contest.startTime)
          type = "danger"
          active = true
          if $scope.contest.currentTime >= $scope.contest.endTime
            $timeout(->
              $window.location.reload()
            , 500)
        else
          current = $scope.contest.length
          type = "success"
          active = false
        $scope.progressbar.value = current * 100 / $scope.contest.length
        $scope.progressbar.type = type
        $scope.progressbar.active = active
      currentTimeTimer = $interval(updateTime, 1000)

      clarificationTimer = undefined
      rankListTimer = undefined
      $scope.$on("$destroy", ->
        $interval.cancel(currentTimeTimer)
        $timeout.cancel(rankListTimer)
        $timeout.cancel(clarificationTimer)
      )

      $scope.totalUnreadedClarification = 0
      $scope.lastClarificationCount = 0
      refreshClarification = ->
        $rootScope.$broadcast("list:refresh:comment", (data)->
          $scope.totalUnreadedClarification = Math.max(0,
              data.pageInfo.totalItems - $cookieStore.get(cookieName).lastClarificationCount)
          $scope.lastClarificationCount = data.pageInfo.totalItems
          clarificationTimer = $timeout(refreshClarification, 10000)
        )
      clarificationTimer = $timeout(refreshClarification, 500)
      $scope.selectClarificationTab = ->
        $timeout.cancel(clarificationTimer)
        clarificationTimer = $timeout(refreshClarification, 0)
        contest = $cookieStore.get(cookieName)
        contest.lastClarificationCount = $scope.lastClarificationCount
        $cookieStore.put(cookieName, contest)
        $scope.totalUnreadedClarification = 0

      $scope.showProblemTab = ->
        $scope.$$childHead.$$nextSibling.$$nextSibling.tabs[1].select()
      $scope.chooseProblem = (order)->
        $scope.showProblemTab()
        $scope.currentProblem = _.findWhere($scope.problemList, order: order)

      $scope.$on("contestShow:showProblemTab", (e, order)->
        $scope.chooseProblem(order)
      )
      $scope.showStatusTab = ->
        # TODO Dirty code!
        $scope.$$childHead.$$nextSibling.$$nextSibling.tabs[3].select()
        $scope.refreshStatus()
      $scope.openSubmitModal = ->
        $modal.open(
          templateUrl: "template/modal/submit-modal.html"
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

      $scope.contestStatusCondition = angular.copy($rootScope.statusCondition)
      $scope.contestStatusCondition.contestId = $scope.contestId
      $scope.refreshStatus = ->
        $scope.$broadcast("refreshList")

      refreshRankList = ->
        contestId = angular.copy($scope.contestId)
        $http.get("/contest/rankList/#{contestId}").success((data)->
          if data.result == "success"
            $scope.rankList = data.rankList.rankList
            _.each($scope.problemList, (value, index)->
              value.tried = data.rankList.problemList[index].tried
              value.solved = data.rankList.problemList[index].solved
            )
            if $rootScope.hasLogin
              userStatus = _.findWhere(data.rankList.rankList,
                userName: $rootScope.currentUser.userName)
              if angular.isDefined userStatus
                _.each($scope.problemList, (value, index)->
                  value.hasSolved = userStatus.itemList[index].solved
                  value.hasTried = userStatus.itemList[index].tried > 0
                )
          rankListTimer = $timeout(refreshRankList, 10000)
        ).error(->
          rankListTimer = $timeout(refreshRankList, 10000)
        )

      rankListTimer = $timeout(refreshRankList, 500)
  ])
