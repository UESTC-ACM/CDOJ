/**
 * Common functions
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */

function mergeOptions(options,userOptions) {
  if (userOptions != undefined) {
    $.each(userOptions,function(index,value) {
      options[index] = value;
    });
  }
  return options;
}

function subSum(array, op, ed) {
  var res = 0;
  ed = Math.min(ed, array.length - 1);
  for (var i = op; i <= ed; i++)
    res += array[i];
  return res;
}

function jsonPost(url, data, callback) {
  $.ajax({
    type : "POST",
    url : url,
    dataType : "json",
    contentType : "application/json",
    data : JSON.stringify(data),
    success : callback
  });
}
