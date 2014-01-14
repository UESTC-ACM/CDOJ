initUI = ->
  $('.dropdown-menu').find('form').click (e)=>
    e.stopPropagation()

  #Set user avatar on page
  $("img[type='avatar']").each (id, el) =>
    $el = $(el)
    $el.setAvatar
      #image: "http://www.acm.uestc.edu.cn/images/akari_small.jpg"
      size: $el.width() if $el.width()