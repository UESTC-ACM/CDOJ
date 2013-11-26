getParam = ->
  params = location.search
  params = params.substr(params.indexOf("?") + 1)
  params = params.split("&")
  result = {}
  params.each((value, id) ->
    value = value.split("=")
    if value.length == 2
      result[value[0]] = value[1]
  )
  return result