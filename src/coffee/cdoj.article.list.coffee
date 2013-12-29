initArticleList = ->
  $articleList = $("#article-list")
  if $articleList.length != 0
    articleList = new ListModule(
      listContainer: $articleList
      requestUrl: "/article/search"
      condition:
        "currentPage": null,
        "startId": undefined,
        "endId": undefined,
        "keyword": undefined,
        "title": undefined,
        "orderFields": "id",
        "orderAsc": "false"
      formatter: (data) ->
        console.log(data)
        getReadMore = (hasMore, articleId) ->
          if hasMore then "<a href=\"/article/show/#{articleId}\">Read more >></a>" else ""
        """
          <div class="cdoj-article">
            <h1><a href="/article/show/#{data.articleId}">#{data.title}</a></h1>
            <small>#{data.clicked} visited, create by #{data.ownerName}, last modified at <span class="cdoj-article-post-time">#{Date.create(data.time).relative()}</span></small>
            <div class="cdoj-article-summary">
              <div class="cdoj-article-summary-content"><textarea>#{data.content}</textarea></div>
            </div>
            <p>#{getReadMore(data.hasMore, data.articleId)}</p>
            <hr />
          </div>
        """
      after: ->
        $(".cdoj-article-summary-content").markdown()
    )
