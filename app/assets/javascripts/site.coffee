$ ->
  $('#button').button()
  $('.note').resizable()
  $('.note').draggable()
  $('div.editable').bind 'click', () ->
    editablediv this
    
    #$(newitem).value = $(newitem).value
    #$(this).replaceWith '<div id="' + this.id + '">$(this).text()</div>'
window.test = () ->
  alert "this is alert"
window.editablediv = (obj) ->
  $(obj).replaceWith('<input id="' + obj.id + '" type="text" value=""/>')
  newitem = $("#" + obj.id)
    
  $(newitem).focus()
  
  $(newitem).val $(obj).text()
  
  $(newitem).bind 'blur', () ->
    editableinput newitem

window.editableinput = (obj) ->
  $(obj).replaceWith '<div id="' + obj.id + '">' + $(obj).val() + '</div>'
  newitem = $("#" + obj.id)
  $(newitem).bind 'click', () ->
    editablediv newitem