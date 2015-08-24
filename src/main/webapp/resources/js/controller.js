'use strict';

/**
 * EventController
 * 
 * @constructor
 */
var EventController = function($scope, $http) {
	
	$scope.event = {};
	$scope.event.time={};
	$scope.event.place ={};
	$scope.event.place.cityId = 1;
	$scope.event.status = 0;
	
	$scope.event.categories =[];
	$scope.event.tags=[];
	
	$scope.eventEditId = '';
	
	$scope.hours = [];
	for(var i=0;i<13;i++) {
		$scope.hours.push(i);
	}
	$scope.mins = [];
	for(var i=0;i<60;i++) {
		$scope.mins.push(i);
	}
	
	$scope.getCategories = function() {
        $http.get('category/list').success(function(categories){
            $scope.categories = categories;
        });
    };

	$scope.placeChanged = function() {
        $scope.place = this.getPlace();
        console.log($scope.place);
        
        $scope.event.place.address= $scope.place.formatted_address;
        // $scope.event.place.name= $scope.place.formatted_address;
        $scope.event.place.latitude=$scope.place.geometry.location.lat();
        $scope.event.place.longitude=$scope.place.geometry.location.lng();
        
        // console.log($scope.event);
    }
	
	$scope.events = {};
    $scope.editMode = false;

    $scope.getEventList = function() {
        $http.get('event/list').success(function(events){
            $scope.events = events;
        });
    };

    $scope.addEvent = function(event) {
        $scope.resetError();
        console.log(event);
        $http.post('event/create', event).success(function() {
        	$scope.resetEventForm();
            $scope.getEventList();
        }).error(function() {
            $scope.setError('Could not add a new event');
        });
    };

    $scope.updateEvent = function(event) {
        $scope.resetError();
        $scope.eventEditId = event.id;
        delete event.id;
        delete event.distance;
        
        $http.put('event/update/' + $scope.eventEditId, event).success(function() {
            $scope.getEventList();
            $scope.editMode = false;
        }).error(function() {
            $scope.setError('Could not update the event');
        });
    };
    
    $scope.duplicateEvent = function(event) {
        $scope.resetError();
        delete event.id;
        delete event.distance;
        $http.post('event/create', event).success(function() {
        	$scope.resetEventForm();
            $scope.getEventList();
        }).error(function() {
            $scope.setError('Could not duplicate the event');
        });
    };
    
    $scope.uploadPoster=function () {
    	var file = $scope.myFile;
    	// file.name = "event_" + new Date().getTime();
    	console.log(file);
    	var formData = new FormData();
	    formData.append("file",file	);
	    $http.post('event/uploadPoster', formData, {
	    	 							transformRequest: angular.identity,
							        	headers: { 'Content-Type': undefined }})
		.success(function(data, status) {
			$scope.event.poster = data.message;
	        alert("Success ... " + status);
        }).error(function(data, status) {
            alert("Error ... " + status);
        });
    	    
    };
    
    $scope.uploadFileEvent=function () {
    	var file = $scope.myFile;
    	console.log(file);
    	var formData = new FormData();
	    formData.append("file",file	);
	    $http.post('event/uploadFileEvent', formData, {
	    	 							transformRequest: angular.identity,
							        	headers: { 'Content-Type': undefined }})
		.success(function(data, status) {
			$scope.event.poster = data.message;
	        alert("Success ... " + status);
        }).error(function(data, status) {
            alert("Error ... " + status);
        });
    	    
    };
    
    $scope.editEvent = function(event) {
        $scope.resetError();
        $scope.event = event;
        $scope.event.time.startDate = $scope.formatDate($scope.event.time.startDate);
        $scope.event.time.endDate = $scope.formatDate($scope.event.time.endDate);
        
        $scope.editMode = true;
        $scope.showModalAddEvent = !$scope.showModalAddEvent;
    };

    $scope.formatDate= function(dateNumber) {
        var d = new Date(dateNumber);
        var day = d.getDate();
        var month = d.getMonth() + 1;
        var year = d.getFullYear();
        if (day < 10) {
            day = "0" + day;
        }
        if (month < 10) {
            month = "0" + month;
        }
        var date =  year + "-" + month + "-" + day;
        return date;
    }; 
    
    $scope.removeEvent = function(id) {
        $scope.resetError();

        $http.delete('event/delete/' + id).success(function() {
            $scope.getEventList();
        }).error(function() {
            $scope.setError('Could not remove event');
        });
    };

    $scope.resetEventForm = function() {
        $scope.resetError();
        $scope.event = {};
        $scope.place = {};
        $scope.editMode = false;
    };

    $scope.resetError = function() {
        $scope.error = false;
        $scope.errorMessage = '';
    };

    $scope.setError = function(message) {
        $scope.error = true;
        $scope.errorMessage = message;
    };

    $scope.getEventList();
    
    $scope.getCategories();

    $scope.predicate = 'id';
    
    $scope.showModalAddEvent = false;
    $scope.toggleModalAddEvent = function(){
        $scope.showModalAddEvent = !$scope.showModalAddEvent;
        $scope.resetEventForm();
        $("#inputStartDate").datepicker({
            dateFormat : "yy-mm-dd"
        }).val()
        $("#inputEndDate").datepicker({
            dateFormat : "yy-mm-dd"
        }).val()
    };
    
    $scope.showModalUploadFileEvent = false;
    $scope.toggleModalUploadFileEvent = function(){
        $scope.showModalUploadFileEvent = !$scope.showModalUploadFileEvent;
    };
};