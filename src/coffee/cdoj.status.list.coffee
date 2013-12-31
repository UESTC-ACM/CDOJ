# Move most common functions in a base list module
initStatusList = ->
  $statusList = $("#status-list")
  if $statusList.length != 0
    statusList = new ListModule(
      listContainer: $statusList
      requestUrl: "/status/search"
      #autoRefresh: true
      #refreshInterval: 1000 # 1s
      condition:
        "currentPage": null,
        "startId": undefined,
        "endId": undefined,
        "userName": undefined,
        "problemId": undefined,
        "languageId": undefined,
        "contestId": undefined,
        "result": [],
        "orderFields": "statusId",
        "orderAsc": "false"
      formatter: (data) ->
        getStatusImage = (id) ->
          switch id
            when 0, 16, 18 then "<i class='fa fa-spinner fa-spin'></i>" # QUEUING
            when 1 then "<i class='fa fa-check-circle'></i>" # AC
            when 2 then "<i class='fa fa-circle'></i>" # PE
            when 3, 4, 6, 13 then "<i class='fa fa-ban'></i>" # TLE MLE OLE RF
            when 5 then "<i class='fa fa-times-circle'></i>" # WA
            when 7 then "<i class='fa fa-warning'></i>" # CE
            when 8, 9, 10, 11, 12, 15 then "<i class='fa fa-bug'></i>" # RE
            when 14 then "<i class='fa fa-frown-o'></i>" # SE
            when 17 then "<i class='fa fa-gear fa-spin'></i>" # Running
            else ""
        getAlertClass = (id) ->
          switch id
            when 0, 16, 17, 18 then "status-info"
            when 1 then "status-success"
            else "status-danger"
        getContestHref = (contestId) ->
          """
          <small class="pull-right">
            <a href="/contest/show/#{contestId}">Contest <i class="fa fa-trophy"></i>#{contestId}</a>,&nbsp;
          </small>
          """
        getCostInformation = (timeCost, memoryCost) ->
          """
            <td style="text-align: center;">#{memoryCost} KB</td>
            <td style="text-align: center;">#{timeCost} MS</td>
          """
        currentUser = getCurrentUser()
        getReturnType = (returnType, returnTypeId, statusId, userName) ->
          if returnTypeId == 7
            if currentUser.userLogin && (currentUser.currentUserType == "1" || currentUser.currentUser == userName)
              "<a href=\"#\" value=\"#{statusId}\" class=\"ce-link\">#{returnType}</a>"
            else
              returnType
          else
            returnType
        getCodeInfo = (length, language, statusId, userName) ->
          if currentUser.userLogin && (currentUser.currentUserType == "1" || currentUser.currentUser == userName)
            "<a href=\"#\" value=\"#{statusId}\" class=\"code-link\">#{length} B</a>"
          else
            "#{length} B"
        """
          <tr>
            <td style="text-align: center;">#{data.statusId}</td>
            <td style="text-align: center;"><a href="/user/center/#{data.userName}">#{data.userName}</a></td>
            <td style="text-align: center;"><a href="/problem/show/#{data.problemId}">#{data.problemId}</a></td>
            <td style="text-align: center;" class="#{getAlertClass(data.returnTypeId)}">#{getReturnType(data.returnType, data.returnTypeId, data.statusId, data.userName)}</td>

            #{if data.returnTypeId == 1 then getCostInformation(data.timeCost, data.memoryCost) else "<td></td><td></td>"}

            <td style="text-align: center;">#{data.language}</td>

            <td style="text-align: center;">#{getCodeInfo(data.length, data.language, data.statusId, data.userName)}</td>
            <td style="text-align: center;">#{Date.create(data.time).format("{yyyy}-{MM}-{dd} {HH}:{mm}:{ss}")}</td>
            <td></td>
          </tr>
        """
        ###
          <div class="col-md-12">
            <div class="panel panel-default">
              <div class="panel-body">
                <div class="media">
                  <div class="pull-left">
                    <div class="status-sign #{getAlertClass(data.returnTypeId)}">
                      #{getStatusImage(data.returnTypeId)}
                    </div>
                  </div>
                  <div class="media-body ">
                    <h4 class="media-heading">
                      <span>#{getReturnType(data.returnType, data.returnTypeId, data.statusId, data.userName)}</span>
                      #{if data.returnTypeId == 1 then getCostInformation(data.timeCost, data.memoryCost) else ""}
                      <small class="pull-right">
                        ##{data.statusId}
                      </small>
                      #{if data.contestId != undefined then getContestHref(data.contestId) else ""}
                      <small class="pull-right">
                        <a href="/problem/show/#{data.problemId}">Prob <i class="fa fa-puzzle-piece"></i>#{data.problemId}</a>,&nbsp;
                      </small>
                    </h4>
                    <span><a href="/user/center/#{data.userName}"><i class="fa fa-user"></i>#{data.userName}</a></span>
                    <span class="pull-right label label-default">#{Date.create(data.time).format("{yyyy}-{MM}-{dd} {HH}:{mm}:{ss}")}</span>
                    <span class="pull-right label label-success" style="margin-right: 8px">#{getCodeInfo(data.length, data.language, data.statusId, data.userName)}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        ###
      after: ->
        $(".ce-link").click (e) =>
          $el = $(e.currentTarget)
          statusId = $el.attr("value")
          $.post("/status/info/#{statusId}", (data) ->
            compileInfo = ""
            if data.result == "success"
              compileInfo = data.compileInfo
            else
              compileInfo = data.error_msg
            compileInfo = compileInfo.trim()
            $modal = $("#compile-info-modal")
            $modal.find(".modal-body").empty().append("<pre>#{compileInfo}</pre>")
            $modal.find(".modal-body").prettify()
            $modal.modal("toggle")
          )
          return false
        $(".code-link").click (e) =>
          $el = $(e.currentTarget)
          statusId = $el.attr("value")
          $.post("/status/info/#{statusId}", (data) ->
            code = ""
            if data.result == "success"
              code = data.code
            else
              code = data.error_msg
            code = code.trim().escapeHTML();
            console.log(code)
            $modal = $("#code-modal")
            $modal.find(".modal-body").empty().append("<pre>#{code}</pre>")
            $modal.find(".modal-body").prettify()
            $modal.modal("toggle")
          )
          return false
        $("#status-refresh-button").click =>
          statusList.triggerRefresh()
          return false
    )
