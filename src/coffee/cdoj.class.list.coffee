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
      after: function will be called after datas apeended
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
      @list = @listContainer.find("#list-container")
      # Clear first
      @list.empty()
      # Get list via requestUrl
      jsonPost(@options.requestUrl
        condition
        (datas) =>
          # Add pagination
          @pageInfo.empty().append(datas.pageInfo)
          console.log(@pageInfo.find('a'))
          @pageInfo.find('a').click (e) =>
            $el = $(e.currentTarget)
            if $el.attr('href') == null
              return false
            condition.currentPage = $el.attr('href')
            @refresh(condition);
            return false

          datas.list.each((data) =>
            # TODO We can pass the status of prev list item to next list item
            @list.append(@options.formatter data)
          )
          if @options.after != undefined
            @options.after()
          @refreshLock = 0
      )
    return
