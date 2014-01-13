initUI = ->
  $('.dropdown-menu').find('form').click (e)=>
    e.stopPropagation()