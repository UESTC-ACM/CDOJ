jsonPost = (url, data, callback) ->
  $.ajax
    type : "POST"
    url : url
    dataType : "json"
    contentType : "application/json"
    data : JSON.stringify(data)
    success : callback

jsonMerge = (jsonA, jsonB) ->
  Object.merge(jsonA, jsonB, false, (key, a, b) ->
    if a.constructor == Array
      a.add(b)
    else
      a = b
    return a
  )

openInNewTab = (url) ->
  win = window.open(url, "_blank")
  win.focus()