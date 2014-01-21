initContestPage = ->
  $contest = $("#contest-show")
  if $contest.length > 0
    contestId = $("#contest-title").attr("value")
    currentTime = $("#contest-current-time").attr("value")
    startTime = $("#contest-start-time").attr("value")
    endTime = $("#contest-end-time").attr("value")
    contestType = $("#contest-type").attr("value")
    contestStatus = $("#contest-status").attr("value")
    contestTab = $("#contest-show-tab")
    contestProblemSummary = $("#contest-problem-summary")
    contestStatusTabHref = contestTab.find("a[href='#tab-contest-status']")
    $processBar = $("#contest-progress-bar")
    if contestStatus == ContestStatus.PENDING
      #
    else if contestStatus == ContestStatus.RUNNING
      #
    else if contestStatus == ContestStatus.ENDED
      $processBar.addClass("progress-bar-success").css("width", "100%")

    contestProblemSummary.find("a").click (e) =>
      $el = $(e.currentTarget)
      href = $el.attr("href")
      if href.startsWith("#")
        tabHref = contestTab.find("a[href='#{href}']")
        if tabHref.length > 0
          tabHref.tab("show")
        return false
      else
        return true

    currentUser = getCurrentUser()
    if currentUser.userLogin
      $("#submit-code").click =>
        info =
          codeContent: $("#code-content").val()
          languageId: $(":radio[name='language']:checked").attr('value')
          contestId: contestId
          problemId: $("#problem-selector").find("option:selected").val()
        jsonPost("/status/submit", info, (data)=>
          if data.result == "error"
            alert data.error_msg
          else if data.result == "success"
            contestStatusTabHref.tab("show")
        )
        return false

    initContestStatusList(contestId)
    return
