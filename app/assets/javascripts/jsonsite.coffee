notesApp = angular.module "notesApp",[]
notesApp.directive 'note', ($http,$interval) ->
	restrict: 'E',templateUrl: '/assets/angular/views/note.html', link : (scope, element, attrs) ->
		
		element.draggable({
			start: () ->
				scope.stopTimer()
			stop: () ->
				$http({method: 'POST', url: '/note/saveposition', data:{id: element.attr('id'),top: element.position().top, left: element.position().left}}).success (data, status, headers, config) ->
					scope.note = data
					scope.startTimer()
		})
		element.resizable({
			stop: () ->
				$http({method: 'POST', url: '/note/savedimension', data:{id: element.attr('id'),height: element.height(), width: element.width()}}).success (data, status, headers, config) ->
					scope.note = data
					scope.startTimer()
		})
		element.find('.delete-note').first().bind 'click', () ->
			$http({method: 'DELETE', url: '/note/' + scope.note.Id}).success () ->
				scope.stopTimer()
				element.fadeOut 500
				scope.$destroy()
			
		
		scope.startTimer = () ->
			scope.stop = $interval () ->
				$http({method: 'GET', url: '/note/' + scope.note.Id}).success (data, status, headers, config) ->
						scope.note = data
						scope.startTimer()
						true
			, 5000, 1
			true
		scope.stopTimer = () ->
			$interval.cancel scope.stop
		scope.startTimer()
		
		scope.$on '$destroy', () ->
			scope.stopTimer()
		true
		
notesApp.controller "NotesCtrl", ($scope,$http) ->
	$http.get('/note/json').success (data) ->
		$scope.notes = data;
	$scope.widgetHeight = (note) ->
		if(note.height != null && note.height > 35)
			return note.height - 35
		else
			return 35;

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