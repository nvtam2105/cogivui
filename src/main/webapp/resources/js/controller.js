'use strict';

/**
 * EventController
 * 
 * @constructor
 */
var EventController = function($scope, $http, $filter, ngTableParams) {
	
	$scope.event = {};
	$scope.event.time={};
	$scope.event.place ={};
	$scope.event.place.cityId = 1;
	$scope.event.status = 0;
	
	$scope.event.categories =[];
	$scope.event.tags=[];
	
	$scope.eventEditId = '';
	$scope.events = {};
    $scope.editMode = false;
    $scope.predicate = 'id';
	
	$scope.hours = [];
	for(var i=0;i<13;i++) {
		$scope.hours.push(i);
	}
	$scope.mins = [0,15,30,45];
	//for(var i=0;i<60;i++) {
	//	$scope.mins.push(i);
	//}
	
	$scope.getCategories = function() {
        $http.get('category/list').success(function(categories){
            $scope.categories = categories;
        });
    };

	$scope.placeChanged = function() {
		$scope.event.place ={};
        $scope.place = this.getPlace();
        // console.log($scope.place);
        
        $scope.event.place.address= $scope.place.formatted_address;
        // $scope.event.place.name= $scope.place.formatted_address;
        $scope.event.place.latitude=$scope.place.geometry.location.lat();
        $scope.event.place.longitude=$scope.place.geometry.location.lng();
        
        // console.log($scope.event);
    }
	

    $scope.getEventList = function() {
        $http.get('event/list').success(function(data){
    	 $scope.tableParams = new ngTableParams({
    	        page: 1,            // show first page
    	        count: 10 ,          // count per page
    	        sorting: {
    	        	// startDate: 'asc'     // initial sorting
    	        }
    	    }, {
    	        total: data.length, // length of data
    	        getData: function ($defer, params) {
    	        	var orderedData = params.sorting() ? $filter('orderBy')(data, params.orderBy()) : data;
    	        	var filteredData = params.filter() ? $filter('filter')(orderedData, params.filter()) : orderedData;
    	        	
    	        	$scope.events = filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count());
    	        	$defer.resolve($scope.events);
    	        }
    	    })
        	 
        });
    };
    
    $scope.addEvent = function(event) {
        $scope.resetError();
        // console.log(event);
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
        delete event.startDate;
        delete event.placeAddress;
        delete event.cityName;
        
        $http.put('event/update/' + $scope.eventEditId, event).success(function() {
            //$scope.getEventList();
            $scope.editMode = false;
        }).error(function() {
            $scope.setError('Could not update the event');
        });
    };
    
    $scope.duplicateEvent = function(event) {
        $scope.resetError();
        delete event.id;
        delete event.distance;
        delete event.startDate;
        delete event.placeAddress;
        delete event.cityName;
        
        delete event.time.id;
        delete event.place.id;
        
        $http.post('event/create', event).success(function() {
        	$scope.resetEventForm();
            $scope.getEventList();
        }).error(function() {
            $scope.setError('Could not duplicate the event');
        });
    };
    
    $scope.uploadPoster=function () {
    	var file = document.getElementById('file2').files[0], r = new FileReader();
	    r.onloadend = function(e){
	      var data = e.target.result;
	      // send you binary data via $http or $resource or do anything else with it
	      var formData = new FormData();
		  formData.append("file",file);
		  $http.post('event/uploadPoster', formData, {
		    	 							transformRequest: angular.identity,
								        	headers: { 'Content-Type': undefined }})
			.success(function(data, status) {
				$scope.event.poster = data.message;
				//alert($scope.event.poster)
		        alert("Success ... " + status);
	        }).error(function(data, status) {
	            alert("Error ... " + status);
	        });
	    }
	    r.readAsBinaryString(file);
    };
    
    $scope.uploadFileEvent=function () {
    	var file = document.getElementById('file1').files[0];
    	var r = new FileReader();
	    r.onloadend = function(e){
	      var data = e.target.result;
	      // send you binary data via $http or $resource or do anything else with it
	      var formData = new FormData();
		    formData.append("file",file	);
		    $http.post('event/uploadFileEvent', formData, {
		    	 							transformRequest: angular.identity,
								        	headers: { 'Content-Type': undefined }})
			.success(function(data, status) {
				//$scope.event.poster = data.message;
		        alert("Success ... " + status);
	        }).error(function(data, status) {
	            alert("Error ... " + status);
	        });
	    }
	    r.readAsBinaryString(file);
    	    
    };
    
    $scope.editEvent = function(event) {
        $scope.resetError();
        //$scope.event.time= {}; 
        $scope.event = event;
        $scope.event.time.startDate = $scope.formatDate($scope.event.time.startDate);
        $scope.event.time.endDate = $scope.formatDate($scope.event.time.endDate);
        
        $scope.editMode = true;
        $scope.showModalAddEvent = !$scope.showModalAddEvent;
        $("#inputStartDate").datepicker({
            dateFormat : "yy-mm-dd"
        }).val()
        $("#inputEndDate").datepicker({
            dateFormat : "yy-mm-dd"
        }).val()
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
            // TODO
        	//$scope.getEventList();
        	for (var e in $scope.events) {
        		if (e.id == id) {
        			delete $scope.events.indexOf(e);
        			break;
        		}
        	}
        	$scope.showModalAddEvent = false;
        	$scope.getEventList();
        }).error(function() {
            $scope.setError('Could not remove event');
        });
    };

    $scope.master = {};
    $scope.resetEventForm = function() {
        $scope.resetError();
        angular.copy($scope.master, $scope.event);
        angular.copy($scope.master, $scope.place);
        $scope.event.posterUrl = '';
        //$scope.event = {};
        //$scope.place = {};
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
    
    $scope.getDatetime = function() {
    	  return new Date().getTime();
    };
    
    $scope.formatHottline = function(text) {
    	 return text.replace(/\s+/g, '').replace(/.+/g,'').replace(/.,/g,'');  
    };
};