$ ->
  $('#button').button()
  $('.note').resizable()
  $('.note').draggable({
    stop: () ->
    	$("#notepositionid").val $(this).attr "id"
    	$("#notepositiontop").val $(this).position().top
    	$("#notepositionleft").val $(this).position().left
    	$("#noteposition").submit()
  })
  $('div.editable').bind 'click', () ->
    editablediv this
    
window.test = () ->
  alert "this is alert"
window.editablediv = (obj) ->
  $(obj).replaceWith('<input id="' + obj.id + '" name="'+ obj.name + '" type="text" value=""/>')
  newitem = $("#" + obj.id)
    
  $(newitem).focus()
  
  $(newitem).val $(obj).text()
  
  $(newitem).bind 'blur', () ->
    
    $(newitem).parent().append '<input name="' + $(obj).attr("name") + '" type="hidden" value="' + $(newitem).val() + '"/>'
    $(newitem).parent().submit()
    #editableinput newitem

window.editableinput = (obj) ->
  $(obj).replaceWith '<div id="' + obj.id + '">' + $(obj).val() + '</div>'
  newitem = $("#" + obj.id)
  $(newitem).bind 'click', () ->
    editablediv newitem