# I'm lazy so I write this class in this file
class SearchModule
  constructor: (@father) ->
    @search = @father.searchGroup
    initCondition = @father.options.condition

    $searchKeyword = @search.find("#search-keyword")
    $advancedButton = @search.find("#advanced")
    $searchButton = @search.find("#search")
    $conditionForm = @search.find("#condition")
    $advancedSearchButton = $conditionForm.find("#search-button")
    $advancedResetButton = $conditionForm.find("#reset-button")

    $searchButton.click =>
      currentCondition = Object.merge(
        initCondition
        keyword: $searchKeyword.val()
      )
      @father.refresh currentCondition
      return false
    $advancedButton.click =>
      this.toggle()
      return false
    $advancedSearchButton.click =>
      currentCondition = Object.merge(
        initCondition
        $conditionForm.getFormData()
      )
      @father.refresh currentCondition
      return false
    $advancedResetButton.click =>
      # TODO
      $conditionForm.resetFormData()
      return false

  toggle: ->
    $advancedForm = @search.find("#condition")
    # OK, just add or remove class on #condition, make this runs first :-)
    isOpen = if $advancedForm.hasClass("open") then true else false
    if isOpen
      $advancedForm.removeClass("open")
    else
      $advancedForm.addClass("open")

class ListModule
  ###
    options:
      listContainer: list container
      requestUrl: search request url
      condition: defalut condition
      format: list item format
  ###
  constructor: (@options) ->
    @listContainer = @options.listContainer
    # TODO pass condition to search module
    @searchGroup = @listContainer.find("#search-group")
    @pageInfo = @listContainer.find("#page-info")
    @refreshLock = 0
    this.refresh(@options.condition)
    @searchModule = new SearchModule this

  refresh: (condition) ->
    if @refreshLock == 0
      @refreshLock = 1
      console.log(condition)
      @list = @listContainer.find("#list-container")
      # Clear first
      @list.empty()
      # Get list via requestUrl
      jsonPost(@options.requestUrl
        condition
        (datas) =>
          @pageInfo.empty().append(datas.pageInfo)
          datas.list.each((data) =>
            @list.append(@options.formatter data)
          )
          @refreshLock = 0
      )
    return
