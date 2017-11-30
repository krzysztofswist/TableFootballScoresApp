var app = angular.module('app', []);

app.controller('globalController', function($scope, $http, $location) {
	$scope.users = [];
	$scope.init = function() {
		var url = $location.absUrl() + "find/all/users";

		var config = {
			headers : {
				'Content-Type' : 'application/json;charset=utf-8;'
			}
		}

		$http.get(url, config).then(function(response) {

			if (response.data.status == "Done") {
				$scope.users = response.data;
			} else {
				$scope.getResultMessage = "get All Users Data Error!";
			}

		}, function(response) {
			$scope.getResultMessage = "Fail!";
		});
	}

	$scope.data = {
		"user1" : "",
		"point1" : 3,
		"user2" : "",
		"point2" : 3,
		"user3" : "",
		"point3" : 3,
		"user4" : "",
		"point4" : 3
	}

	$scope.submitForm = function() {
		var url = $location.absUrl() + "add/table/football/score";

		var config = {
			headers : {
				'Content-Type' : 'application/json;charset=utf-8;'
			}
		}

		$http.post(url, $scope.data, config).then(function(response) {
			$scope.getAllScores();
		}, function(response) {
			$scope.postResultMessage = "Fail!";
		});

		$scope.firstname = "";
		$scope.lastname = "";
	}

	$scope.getAllScores = function() {
		var url = $location.absUrl() + "find/all/table/football/scores";

		var config = {
			headers : {
				'Content-Type' : 'application/json;charset=utf-8;'
			}
		}

		$http.get(url, config).then(function(response) {

			if (response.data.status == "Done") {
				$scope.allTableFootballScores = response.data;
			} else {
				$scope.getResultMessage = "get All Scores Data Error!";
			}

		}, function(response) {
			$scope.getResultMessage = "Fail!";
		});

	}
});
