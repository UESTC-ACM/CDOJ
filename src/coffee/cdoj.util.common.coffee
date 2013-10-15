jsonPost = (url, data, callback) ->
  $.ajax
    type : "POST"
    url : url
    dataType : "json"
    contentType : "application/json"
    data : JSON.stringify(data)
    success : callback