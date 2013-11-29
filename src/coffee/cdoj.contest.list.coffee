# Move most common functions in a base list module
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
        "orderFields": "time",
        "orderAsc": "true"
      formatter: (data) ->
        console.log(data)
        @user = getCurrentUser()
        adminSpan = ->
          result = ""
          if @user.userLogin && @user.currentUserType == "1"
            # admin
            result += """
                      <div class="btn-toolbar" role="toolbar">
                        <div class="btn-group">
                          <button type="button" class="btn btn-default btn-sm problem-visible-state-editor" contest-id="#{data.contestId}" visible="#{data.isVisible}">
                            <i class="#{if data.isVisible then "fa fa-eye" else "fa fa-eye-slash"}"></i>
                          </button>
                          <button type="button" class="btn btn-default btn-sm contest-editor" problem-id="#{data.contestId}"><i class="fa fa-pencil"></i></button>
                        </div>
                      </div>
                      """
          return result
        """
          <div class="col-md-12">
            <div class="panel panel-default">
              <div class="panel-heading">
                <h3 class="panel-title">
                  <a href="/contest/show/#{data.contestId}">#{data.title}</a>
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

    )
