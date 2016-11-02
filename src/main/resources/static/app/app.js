angular.module('Lottery', ['ngResource','smart-table'])

.factory('TicketService', ['$resource', function($resource) {
	return $resource('/tickets/:id', {id:'@id'});
}])

.factory('LotteryService', ['$resource', function($resource) {
	return $resource('/lottery/:resourcePath',
			{resourcePath:'@path'},
			{start: {method:'GET'}});
}])

.config(['$resourceProvider', function($resourceProvider) {
	// Don't strip trailing slashes from calculated URLs
	$resourceProvider.defaults.stripTrailingSlashes = false;
}])

.controller('LotteryCtrl', ['$scope', 'TicketService', 'LotteryService',
    function($scope, TicketService, LotteryService) {

		/* Saves a new ticket */
		$scope.buyTicket = function(name) {
			TicketService.save({firstName:name}, function(data) {
				console.log(data);
				$scope.tickets.push(data);
			});
		};

		/* Loads ticket by ID */
		$scope.getById = function(ticketId) {
			TicketService.get({id:ticketId}, function(data) {
				console.log(data);
				$scope.ticket = data;
				
			});
		};
		
		/* Loads all purchased tickets */
		$scope.loadTickets = function() {
			TicketService.query(function(data) {
				console.log(data);
				$scope.tickets = data;
			});
		};

		/* Starts the draw */
		$scope.startDraw = function() {
			LotteryService.query({resourcePath:'draw'}, function(data) {
				console.log(data);
				$scope.selectedBalls = data;
			});
		};

		/* Retrieves draw winners */
		$scope.loadWinners = function() {
			LotteryService.query({resourcePath:'winners'}, function(data) {
				console.log(data);
				$scope.winners = data;
			});
		};
		
		// data holders
		$scope.tickets = $scope.loadTickets();
		$scope.selectedBalls = [];
		$scope.winners = $scope.loadWinners();
	}
]);