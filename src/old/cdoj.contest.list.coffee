initContestList = ->
  $contestList = $("#contest-list")
  if $contestList.length != 0
    contestList = new ListModule(
      listContainer: $contestList
      requestUrl: "/contest/search"
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
        @user = getCurrentUser()
        adminSpan = ->
          result = ""
          if @user.userLogin && @user.currentUserType == AuthenticationType.ADMIN
            # admin
            result += """
                      <div class="btn-toolbar" role="toolbar">
                        <div class="btn-group">
                          <button type="button" class="btn btn-default btn-sm contest-visible-state-editor" contest-id="#{data.contestId}" visible="#{data.isVisible}">
                            <i class="#{if data.isVisible then "fa fa-eye" else "fa fa-eye-slash"}"></i>
                          </button>
                          <button type="button" class="btn btn-default btn-sm contest-editor" contest-id="#{data.contestId}"><i class="fa fa-pencil"></i></button>
                        </div>
                      </div>
                      """
          return result
        """
          <div class="col-md-12">
            <div class="panel panel-default">
              <div class="panel-heading">
                <h3 class="panel-title">
                  <a href="/contest/show/#{data.contestId}" target="_blank">#{data.title}</a>
                  <span class='pull-right admin-span'>#{adminSpan()}</span>
                </h3>
              </div>
              <div class="panel-body">
                <span>#{Date.create(data.time).format("{yyyy}-{MM}-{dd} {HH}:{mm}:{ss}")}--#{Date.create(data.time + data.length * 1000).format("{yyyy}-{MM}-{dd} {HH}:{mm}:{ss}")}</span>
              </div>
            </div>
          </div>
        """
      after: ->
        @user = getCurrentUser()
        if @user.userLogin && @user.currentUserType == AuthenticationType.ADMIN
          $(".contest-editor").click (e) =>
            $el = $(e.currentTarget)
            openInNewTab "/contest/editor/#{$el.attr("contest-id")}"
            return false
          $(".contest-visible-state-editor").click (e) =>
            $el = $(e.currentTarget)
            visible = if $el.attr("visible") == "true" then true else false
            queryString = "/contest/operator/#{$el.attr("contest-id")}/isVisible/#{!visible}"
            $.post(queryString, (data) ->
              if data.result == "success"
                visible = !visible
                $el.attr("visible", visible)
                $el.empty().append("<i class=\"#{if visible then "fa fa-eye" else "fa fa-eye-slash"}\"></i>")
            )
            return false
    )
