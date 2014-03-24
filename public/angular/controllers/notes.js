var notesApp = angular.module("notesApp",[]);
notesApp.controller("NotesCtrl",function ($scope,$http){
	$http.get('/note/json').success(function(data){
		$scope.notes = data;
	});
	$scope.widgetHeight = function(note){
		if(note.height != null && note.height > 35){
			return note.height - 35;
		} else {
			return 35;
		}
	};
});