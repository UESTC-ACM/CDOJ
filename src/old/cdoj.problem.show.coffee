# Submit and submit...
initProblemPage = ->
  if $("#problem-show").length > 0
    $currentUser = $("#currentUser")
    @userLogin = if $currentUser.length != 0 then true else false
    problemId = $("#problem-title").attr("value")
    if @userLogin
      $("#submit-code").click =>
        info =
          codeContent: $("#code-content").val()
          languageId: $(":radio[name='language']:checked").attr('value')
          contestId: null
          problemId: problemId
        jsonPost("/status/submit", info, (data)=>
          if data.result == "error"
            alert data.error_msg
          else if data.result == "success"
            window.location.href = "/status/list"
        )
        return false

    $statusList = $("#cdoj-best-solutions")
    if $statusList.length != 0
      statusList = new ListModule(
        listContainer: $statusList
        requestUrl: "/status/search"
        #autoRefresh: true
        #refreshInterval: 1000 # 1s
        condition:
          "currentPage": null,
          "countPerPage": 10,
          "startId": undefined,
          "endId": undefined,
          "userName": undefined,
          "problemId": problemId,
          "languageId": undefined,
          "contestId": undefined,
          "result": "OJ_AC",
          "orderFields": "timeCost,memoryCost,Length,statusId",
          "orderAsc": "true,true,true,true"
        formatter: (data) ->
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
              <td style="text-align: center;"><a href="/user/center/#{data.userName}" target="_blank">#{data.userName}</a></td>

              #{if data.returnTypeId == 1 then getCostInformation(data.timeCost, data.memoryCost) else "<td></td><td></td>"}

              <td style="text-align: center;">#{data.language}</td>

              <td style="text-align: center;">#{getCodeInfo(data.length, data.language, data.statusId, data.userName)}</td>
              <td style="text-align: center;">#{Date.create(data.time).format("{yyyy}-{MM}-{dd} {HH}:{mm}:{ss}")}</td>
              <td></td>
            </tr>
          """
        after: ->
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
      )