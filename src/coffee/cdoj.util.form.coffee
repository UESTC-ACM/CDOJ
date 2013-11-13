$ = jQuery
$.fn.getFormValue = ->
  $el = $(this)
  isType = (_type) -> $el.attr("type") == _type || $el[0].localName == _type
  if isType "radio"
    result = $(":radio[name='#{$el.attr('name')}']:checked").val();
    result = undefined if result == "all"
    return result;
  else if isType "select"
    result = $el.val();
    result = undefined if result == -1
    return result
  else
    return $el.val()

$.fn.getFormData = ->
  result = {}
  this.each (id, el) =>
    ignoreList = ["submit", "reset", "button", "image", "file"];

    $el = $(el)
    $inputs = $el.find(":input")
    for input in $inputs
      $input = $(input)
      if ignoreList.none $input.attr("type")
        result[$input.attr("name")] = $input.getFormValue()

  return result

# Oops, I forgot write setFormData and resetFormData...

$.fn.formValidate = (userOptions) ->
  options =
    result:
      result: "success"
    onSuccess: ->
    onFail: ->
  $.extend(options, userOptions)
  result = options.result

  isSuccess = false
  if result.result == null
    alert "Unknown exception."
  else if result.result == "error"
    alert result.error_msg
  else if result.result == "success"
    options.onSuccess()
    isSuccess = true
  else if result.result == "field_error"
    $el = $(this)

    result.field.each (value)=>
      element = $el.find(":input[name='#{value.field}']")
      if element && element.length > 0
        # select first element
        element = $(element.first());
        controlGroup = element.closest("div.form-group");
        controlGroup.tooltip
          trigger: "click"
          placement: "bottom"
          title: value.defaultMessage
        .tooltip "show"
  else
    alert "Unknown exception."
  options.onFail() if !isSuccess
