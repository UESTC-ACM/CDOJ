angular.module("cdojV2").factory("ArticleService", [
  "BaseRpcService", "ToastService"
  (baseRpcService, toast) ->
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

    _getAllNotice = (onSuccess, onError) ->
      condition = angular.copy(ArticleCondition)
      condition.type = ArticleType.NOTICE
      condition.orderFields = "order"
      condition.orderAsc = "true"

      baseRpcService.post(Url.SEARCH, condition, onSuccess, onError)

    showError = (data) ->
      toast.error(data.error_msg)

    return {
      getAllNotice: (onSuccess) -> _getAllNotice(onSuccess, showError)
    }
])