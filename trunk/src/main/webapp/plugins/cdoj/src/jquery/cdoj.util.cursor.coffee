# Textarea cursor tools
# From http://www.jquery4u.com/snippets/6-jquery-cursor-functions/
$ = jQuery
$.fn.getCursorPosition = ->
  if this.lengh == 0 then return -1
  return $(this).getSelectionStart()

$.fn.setCursorPosition = (position) ->
  if this.lengh == 0 then return this
  return $(this).setSelection(position, position)

$.fn.getSelection = ->
  if this.lengh == 0 then return -1
  s = $(this).getSelectionStart()
  e = $(this).getSelectionEnd()
  return this[0].value.substring(s,e)

$.fn.getSelectionStart = ->
  if this.lengh == 0 then return -1
  input = this[0]

  pos = input.value.length
  if input.createTextRange
    r = document.selection.createRange().duplicate()
    r.moveEnd('character', input.value.length)
    if r.text == ''
      pos = input.value.length
    pos = input.value.lastIndexOf(r.text)
  else if typeof(input.selectionStart) != "undefined"
    pos = input.selectionStart
  return pos

$.fn.getSelectionEnd = ->
  if this.lengh == 0 then return -1
  input = this[0]

  pos = input.value.length
  if input.createTextRange
    r = document.selection.createRange().duplicate()
    r.moveStart('character', -input.value.length)
    if (r.text == '')
      pos = input.value.length
    pos = input.value.lastIndexOf(r.text)
  else if(typeof(input.selectionEnd)!="undefined")
    pos = input.selectionEnd
  return pos

$.fn.setSelection = (selectionStart, selectionEnd) ->
  if this.lengh == 0 then return this
  input = this[0]

  if input.createTextRange
    range = input.createTextRange()
    range.collapse(true)
    range.moveEnd('character', selectionEnd)
    range.moveStart('character', selectionStart)
    range.select()
  else if input.setSelectionRange
    input.focus()
    input.setSelectionRange(selectionStart, selectionEnd)
  return this