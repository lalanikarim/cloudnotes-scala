$ ->
  $('.note').resizable({
    stop: () ->
    	$("#notedimensionid").val $(this).attr "id"
    	$("#notedimensionheight").val $(this).height()
    	$("#notedimensionwidth").val $(this).width()
    	$("#notedimension").submit()
  })
  $('.note').draggable({
    cancel: ".note-body",
    stop: () ->
    	$("#notepositionid").val $(this).attr "id"
    	$("#notepositiontop").val $(this).position().top
    	$("#notepositionleft").val $(this).position().left
    	$("#noteposition").submit()
  })
  $('.hoverable').bind 'mouseenter', () ->
    $(this).addClass('ui-state-hover')
  $('.hoverable').bind 'mouseleave', () ->
    $(this).removeClass('ui-state-hover')
  $('.delete-note').bind 'click', () ->
    $(this).parent().submit()
  
  $('div.editable').bind 'click', () ->
    editablediv this
    
window.test = () ->
  alert "this is alert"
  
window.editablediv = (obj) ->
  $(obj).replaceWith('<textarea id="' + $(obj).attr("id") + '" name="'+ $(obj).attr("name") + '"></textarea>')
  newitem = $("#" + $(obj).attr("id"))
    
  $(newitem).focus()  
  $(newitem).html $(obj).text().replace("<br />","\r\n")  
  $(newitem).bind 'change', () ->
    $(newitem).parent().append '<input  name="' + $(obj).attr("name") + '" type="hidden" value="' + $(newitem).val() + '"/>'
    $(newitem).parent().submit()
  
  $(newitem).bind 'blur', () ->
    editableinput newitem

window.editableinput = (obj) ->
  $(obj).replaceWith '<div id="' + $(obj).attr("id") + '" name="' + $(obj).attr("name") + '">' + $(obj).val() + '</div>'
  newitem = $("#" + $(obj).attr("id"))
  $(newitem).bind 'click', () ->
    editablediv newitem