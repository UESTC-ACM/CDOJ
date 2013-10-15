avatar = (el, options) ->
  $el = $(el)
  emailAddress = $el.attr("email")
  url =
    "http://www.gravatar.com/avatar/
#{hex_md5(emailAddress)}.jpg?
#{if options.size then "s=" + options.size + "&" else ""}
#{if options.rating then "r=" + options.rating + "&" else ""}
#{if options.image then "d=" + encodeURIComponent(options.image) else ""}"

  $el.attr("src", url)
  return el

$ = jQuery
$.fn.setAvatar = (userOption) ->
  options =
    size: 40
    image: "http://www.acm.uestc.edu.cn/images/akari.jpg"
    rating: "pg"
  $.extend(options, userOption)
  this.each (id, el)=>
    avatar el, options