angular.module("cdojV2").factory("ArticleService", [
  "BaseRpcService", "$q"
  (baseRpcService, $q) ->
    ArticleCondition =
      currentPage: null
      startId: undefined
      endId: undefined
      keyword: undefined
      title: undefined
      userId: undefined
      userName: undefined
      type: undefined
      orderFields: "id"
      orderAsc: "false"
    ArticleType =
      NOTICE: 0
      ARTICLE: 1
      COMMENT: 2
    Url =
      SEARCH: "/article/search"
      DATA: (articleId) -> "/article/data/#{articleId}"

    _getAllNotice = ->
      condition = angular.copy(ArticleCondition)
      condition.type = ArticleType.NOTICE
      condition.orderFields = "order"
      condition.orderAsc = "true"

      deferred = $q.defer()
      console.log(deferred)
      baseRpcService.post(Url.SEARCH, condition)
        .then(
          (data) -> deferred.resolve(data)
          (error_msg) -> deferred.reject(error_msg)
        )
      return deferred.promise

    return {
      getAllNotice: -> _getAllNotice()
    }
])