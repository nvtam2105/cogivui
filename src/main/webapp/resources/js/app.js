'use strict';

var CogivuiApp = {};

var App = angular.module('CogivuiApp', [ 'CogivuiApp.filters',
		'CogivuiApp.services', 'CogivuiApp.directives', 'ngRoute',
		'textAngular', 'ngMap','checklist-model','ngTable' ]);

// Declare app level module which depends on filters, and services
App.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/admin', {
		templateUrl : 'template/eventLayout.html',
		controller : EventController
	});

	$routeProvider.otherwise({
		redirectTo : '/'
	});
} ]);
