notesApp = angular.module "notesApp",[]
notesApp.controller "NotesCtrl", ($scope,$http) ->
	$http.get('/note/json').success (data) ->
		$scope.notes = data;
	$scope.widgetHeight = (note) ->
		if(note.height != null && note.height > 35)
			return note.height - 35
		else
			return 35;
$('.note').resizable(
#	{
#    stop: () ->
#      $("#notedimensionid").val $(this).attr "id"
#      $("#notedimensionheight").val $(this).height()
#      $("#notedimensionwidth").val $(this).width()
#      $("#notedimension").submit()
#	}
)
$('.note').draggable(
#	{
#    cancel: ".editable, .note-body",
#    stop: () ->
#      $("#notepositionid").val $(this).attr "id"
#      $("#notepositiontop").val $(this).position().top
#      $("#notepositionleft").val $(this).position().left
#      $("#noteposition").submit()
#	}
)
$('textarea.editable').each () ->
	resizetextarea this
   
$('.hoverable').bind 'mouseenter', () ->
	$(this).addClass('ui-state-hover')
$('.hoverable').bind 'mouseleave', () ->
	$(this).removeClass('ui-state-hover')
$('.delete-note').bind 'click', () ->
	$(this).parent().submit()

$('.editable').bind 'change', () ->
	$(this).parent().submit()
$('textarea.editable').bind 'keyup', () ->
	resizetextarea this 

window.resizetextarea = (ta) ->
	txt = ta.value
	arraytxt = txt.split('\n')
	rows = arraytxt.length
	ta.rows = rows
	$(ta).height rows * 14
	true