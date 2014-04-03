notesApp = angular.module "notesApp",[]
notesApp.directive 'note', ($http,$interval) ->
	restrict: 'E',templateUrl: '/assets/angular/views/note.html', link : (scope, element, attrs) ->
		
		element.find('input[name = "title"]').first().bind 'change', () ->
			$http({method: 'POST', url: '/note/save', data:{id: element.attr('id'),title: element.find('input[name = "title"]').first().val()}}).success (data, status, headers, config) ->
				scope.note = data
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
					.error (data, status, headers, config) ->
						scope.stopTimer()
						element.fadeOut 500
						scope.$destroy()
						true
			, 5000, 1
			true
		scope.stopTimer = () ->
			$interval.cancel scope.stop
		
		ta = element.find('textarea.editable').first()
		ta.bind 'focus', () ->
			ta.val('')
		ta.bind 'blur', () ->
			ta.val('Enter')
		ta.bind 'change', () ->
			$http({method: 'POST', url: '/note/saveitem/' + ta.attr('note-id'),data:{notetext: ta.val(), itemid: ta.attr('item-id')}}).success (data, status, headers, config) ->
				ta.val('Enter')
				scope.note = data
				true
			true
		
		scope.startTimer()
		
		scope.$on '$destroy', () ->
			scope.stopTimer()
		true

notesApp.directive 'noteitem', ($http) ->
	restrict: 'E',templateUrl: '/assets/angular/views/noteitem.html', link: (scope, element, attrs) ->
		ta = element.find('textarea').first()
		ta.bind 'change', () ->
			$http({method: 'POST', url: '/note/saveitem/' + ta.attr('note-id'),data:{notetext: ta.val(), itemid: ta.attr('item-id')}}).success (data, status, headers, config) ->
				scope.$parent.note = data
				true
			true
		ta.bind 'focusin', () ->
			scope.$parent.stopTimer()
		ta.bind 'blur', () ->
			scope.$parent.startTimer()
		ta.bind 'keyup', () ->
			resizetextarea ta
				
notesApp.controller "NotesCtrl", ($scope,$http,$interval) ->
	$http.get('/note/json').success (data) ->
		$scope.notes = data
	
	$scope.widgetHeight = (note) ->
		if(note.height != null && note.height > 35)
			return note.height - 35
		else
			return 35;
			
	stopTimer = () ->
		$interval.cancel $scope.stop
	
$('textarea.editable').each (index, ta) ->
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