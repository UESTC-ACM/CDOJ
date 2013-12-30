# I'm lazy so I write this class in this file
class SearchModule
  constructor: (@father) ->
    @search = @father.searchGroup
    initCondition = @father.options.condition

    $conditionForm = @search.find("#condition")
    $advancedSearchButton = $conditionForm.find("#search-button")
    $advancedResetButton = $conditionForm.find("#reset-button")

    $advancedSearchButton.click =>
      currentCondition = Object.merge(
        initCondition
        $conditionForm.getFormData()
      )
      @father.options.condition = currentCondition
      @father.refresh currentCondition
      @close()
      return false
    $advancedResetButton.click =>
      # TODO
      $conditionForm.resetFormData()
      return false

    $rejudgeButton = $conditionForm.find("#rejudge-button")
    if $rejudgeButton.length > 0
      $rejudgeButton.click =>
        currentCondition = Object.merge(
          initCondition
          $conditionForm.getFormData()
        )
        jsonPost("/status/count", currentCondition, (datas) =>
          if datas.result == "success"
            if confirm("Rejudge all #{datas.count} records")
              jsonPost("/status/rejudge", currentCondition, (datas) =>
                if datas.result == "success"
                  alert("Done!")
                else
                  alert(datas.error_msg)
              )
          else
            alert(datas.error_msg)
        )
        @father.options.condition = currentCondition
        @father.refresh currentCondition
        @close()
        return false

  set: (data) ->
    $conditionForm = @search.find("#condition")
    $conditionForm.setFormData(data)

  close: ->
    $advancedButton = @search.find("#advanced")
    console.log $advancedButton
    $advancedButton.dropdown('toggle')

class ListModule
  ###
    options:
      listContainer: list container
      requestUrl: search request url
      condition: defalut condition
      format: list item format
      after: function will be called after datas apeended
  ###
  constructor: (@options) ->
    @listContainer = @options.listContainer
    # TODO pass condition to search module
    @searchGroup = @listContainer.find("#advance-search")
    @searchModule = new SearchModule this

    @pageInfo = @listContainer.find("#page-info")
    @refreshLock = 0

    # Get url parameter
    params = getParam()
    @searchModule.set params
    @options.condition = jsonMerge(@options.condition, params)

    @refresh(@options.condition)
    self = this
    if @options.autoRefresh == true
      setInterval(() ->
        self.triggerRefresh()
      if @options.refreshInterval == undefined then 1000 else @options.refreshInterval)

  triggerRefresh: ->
    this.refresh @options.condition

  refresh: (condition) ->
    if @refreshLock == 0
      @refreshLock = 1
      @list = @listContainer.find("#list-container")
      # Get list via requestUrl
      jsonPost(@options.requestUrl
        condition
        (datas) =>
          # Add pagination if exists
          @pageInfo.empty()
          if datas.pageInfo != undefined
            @pageInfo.append(datas.pageInfo)
            @pageInfo.find("a").click (e) =>
              $el = $(e.currentTarget)
              if $el.attr("href") == undefined
                return false
              condition.currentPage = $el.attr("href")
              @refresh(condition);
              return false

          # Clear first
          @list.empty()
          datas.list.each((data) =>
            # TODO We can pass the status of prev list item to next list item
            @list.append(@options.formatter data)
          )
          if @options.after != undefined
            @options.after()
          @refreshLock = 0
      )
    return
