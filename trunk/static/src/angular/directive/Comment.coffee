cdoj
.directive("comment",
->
  restrict: "E"
  scope:
    contestId: "="
    problemId: "="
    articleId: "="
  controller: [
    "$rootScope", "$scope", "$http", "$window"
    ($rootScope, $scope, $http, $window) ->
      commentCondition = angular.copy($rootScope.articleCondition)
      commentCondition.contestId = $scope.contestId
      commentCondition.problemId = $scope.problemId
      commentCondition.parentId = $scope.articleId
      $scope.commentCondition = commentCondition

      sendEditRequest = (articleEditDto) ->
        $http.post("/article/edit", articleEditDto).success((data) ->
          if data.result == "success"
            $scope.newComment = ""
            $scope.$broadcast("list:refresh:comment")
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )

      $scope.newComment = ""
      $scope.comment = ->
        articleEditDto =
          content: $scope.newComment
          title: "Comment"
          type: $rootScope.ArticleType.COMMENT
          problemId: $scope.problemId
          contestId: $scope.contestId
          parentId: $scope.articleId
        sendEditRequest(action: "new", articleEditDto: articleEditDto)
        $scope.stashCommentList = false
        $window.scrollTo(0, 0)

      $scope.stashCommentList = false
      $scope.cancel = ->
        $scope.newComment = ""
        $scope.stashCommentList = false
        $window.scrollTo(0, 0)

      $scope.reply = (article) ->
        $scope.newComment = "@" + article.ownerName + " : "
        $scope.$broadcast("flandre:focus")
        $scope.stashCommentList = true

      $scope.delete = (article) ->
        if $window.confirm "Are you sure?"
          articleEditDto =
            content: "This comment is deleted by administrator."
            type: $rootScope.ArticleType.COMMENT
            articleId: article.articleId
          sendEditRequest(articleEditDto: articleEditDto, action: "edit")

  ]
  replace: true
  templateUrl: "template/article/comment.html"
)