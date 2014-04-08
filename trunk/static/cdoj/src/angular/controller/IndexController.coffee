cdoj
.controller("IndexController", [
    "$scope", "$rootScope", "$window", "$http"
    ($scope, $rootScope, $window, $http) ->
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.NOOP
      )
      $window.scrollTo(0, 0)

      $rootScope.title = "Home"
      articleCondition = angular.copy($rootScope.articleCondition)
      articleCondition.type = $rootScope.ArticleType.NOTICE
      articleCondition.orderFields = "order"
      articleCondition.orderAsc = "true"
      $scope.articleCondition = articleCondition

      $scope.isRecentNews = true
      $scope.isRecentContests = false
      $scope.isArticle = false
      $scope.currentArticleId = 0

      clearSelectState = ->
        $scope.isRecentNews = false
        $scope.isRecentContests = false
        $scope.isArticle = false

      $scope.showRecentNews = ->
        $scope.$broadcast("list:refresh:article")
        clearSelectState()
        $scope.isRecentNews = true

      $scope.recentContestsDescription = """
该数据源允许直接引用，但请注明本段文字。

数据来源：http://contests.acmicpc.info/contests.json 。

本数据源力求支持所有国内ACMer的常用OJ，如果希望添加某个OJ或者发现某个OJ无法正常获取比赛信息，
请联系doraemonok#163.com (#替换成@)。
"""
      $scope.recentContests = []
      $scope.showRecentContests = ->
        $http.get("/recentContest").success((data) ->
          $scope.recentContests = _.map(data.recentContestList, (contest) ->
            contest.link = contest.link.unescapeHTML()
            return contest
          )
        ).error(->
          $window.alert "Network error, please refresh page manually."
        )
        clearSelectState()
        $scope.isRecentContests = true

      $scope.article =
        content: ""
      $scope.showArticle = (articleId) ->
        $http.get("/article/data/#{articleId}").success((data) ->
          if data.result == "success"
            $scope.article = data.article
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error, please refresh page manually."
        )
        clearSelectState()
        $scope.isArticle = true
        $scope.currentArticleId = articleId
  ])