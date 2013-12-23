# Move most common functions in a base list module
initProblemList = ->
  $problemList = $("#problem-list")
  if $problemList.length != 0
    problemList = new ListModule(
      listContainer: $problemList
      requestUrl: "/problem/search"
      condition:
        currentPage: null,
        startId: undefined,
        endId: undefined,
        title: undefined,
        source: undefined,
        isSpj: undefined,
        startDifficulty: undefined,
        endDifficulty: undefined,
        keyword: undefined,
        orderFields: undefined,
        orderAsc: undefined
      formatter: (data) ->
        #console.log(data);
        panelAC = "success"
        panelWA = "danger"
        # data.status = 1 if AC, 2 if TRIED, otherwise null
        @user = getCurrentUser()
        adminSpan = ->
          result = ""
          if @user.userLogin && @user.currentUserType == AuthenticationType.ADMIN
            # admin
            result += """
                      <td style="padding: 4px;">
<div class="btn-toolbar" role="toolbar">
                        <div class="btn-group">
                          <button type="button" class="btn btn-default btn-sm problem-visible-state-editor" problem-id="#{data.problemId}" visible="#{data.isVisible}">
                            <i class="#{if data.isVisible then "fa fa-eye" else "fa fa-eye-slash"}"></i>
                          </button>
                          <button type="button" class="btn btn-default btn-sm problem-editor" problem-id="#{data.problemId}"><i class="fa fa-pencil"></i></button>
                          <button type="button" class="btn btn-default btn-sm problem-data-editor" problem-id="#{data.problemId}"><i class="fa fa-cog"></i></button>
                        </div>
                      </div>
</td>
                      """
          return result

        difficulty = (value) ->
          star = "<i class='fa fa-star'></i>"
          starEmpty = "<i class='fa fa-star-o'></i>"
          value = Math.max(1, Math.min(5, value))
          result = ""
          value.times =>
            result += star
          (5 - value).times =>
            result += starEmpty
          return result

        """
          <tr>
            <td style="text-align: right;">#{data.problemId}</td>
            <td><a href="/problem/show/#{data.problemId}">#{data.title}</a></td>
            <td class="#{if data.status == AuthorStatusType.PASS
                           panelAC
                         else if data.status == AuthorStatusType.FAIL
                           panelWA
                         }" style="text-align: right;"><a href="/status/list?problemId=#{data.problemId}">x #{data.solved}</a></td>
            #{adminSpan()}
          </tr>
        """
      after: ->
        @user = getCurrentUser()
        if @user.userLogin && @user.currentUserType == AuthenticationType.ADMIN
          $(".difficulty-span").find("i").click (e) =>
            $el = $(e.currentTarget)
            $pa = $el.parent()
            problemId = $pa.attr("value")
            difficulty = $el.index() + 1
            queryString = "/problem/operator/#{problemId}/difficulty/#{difficulty}"
            $.post(queryString, (data) ->
              if data.result == "success"
                $pa.find("i").removeClass("fa-star").removeClass("fa-star-o").addClass("fa-star-o")
                difficulty.times((i) ->
                  $($pa.find("i")[i]).removeClass("fa-star-o").addClass("fa-star")
                )
            )
            return false
          $(".problem-editor").click (e) =>
            $el = $(e.currentTarget)
            window.location.href = "/problem/editor/#{$el.attr("problem-id")}"
            return false
          $(".problem-data-editor").click (e) =>
            $el = $(e.currentTarget)
            window.location.href = "/problem/dataEditor/#{$el.attr("problem-id")}"
            return false
          $(".problem-visible-state-editor").click (e) =>
            $el = $(e.currentTarget)
            visible = if $el.attr("visible") == "true" then true else false
            queryString = "/problem/operator/#{$el.attr("problem-id")}/isVisible/#{!visible}"
            $.post(queryString, (data) ->
              if data.result == "success"
                visible = !visible
                $el.attr("visible", visible)
                $el.empty().append("<i class=\"#{if visible then "fa fa-eye" else "fa fa-eye-slash"}\"></i>")
            )
            return false
    )
