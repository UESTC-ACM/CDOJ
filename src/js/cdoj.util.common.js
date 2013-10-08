/**
 * Common functions
 */

function mergeOptions(options,userOptions) {
  if (userOptions !== undefined) {
    $.each(userOptions,function(index,value) {
      options[index] = value;
    });
  }
  return options;
}

function jsonPost(url, data, callback) {
  $.ajax({
    type : 'POST',
    url : url,
    dataType : 'json',
    contentType : 'application/json',
    data : JSON.stringify(data),
    success : callback
  });
}
