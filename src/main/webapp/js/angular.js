var app = angular.module('app', []);

app
		.controller(
				'globalController',
				function($scope, $http, $location) {
					$scope.users = [];
					$scope.init = function() {
						var url = $location.absUrl() + "find/all/users";

						var config = {
							headers : {
								'Content-Type' : 'application/json;charset=utf-8;'
							}
						}

						$http
								.get(url, config)
								.then(
										function(response) {

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
						var url = $location.absUrl()
								+ "add/table/football/score";

						var config = {
							headers : {
								'Content-Type' : 'application/json;charset=utf-8;'
							}
						}

						$http.post(url, $scope.data, config).then(
								function(response) {
									$scope.getAllScores();
								}, function(response) {
									$scope.postResultMessage = "Fail!";
								});

						$scope.firstname = "";
						$scope.lastname = "";
					}

					$scope.getAllScores = function() {
						var url = $location.absUrl()
								+ "find/all/table/football/scores";

						var config = {
							headers : {
								'Content-Type' : 'application/json;charset=utf-8;'
							}
						}

						$http
								.get(url, config)
								.then(
										function(response) {

											if (response.data.status == "Done") {
												$scope.allTableFootballScores = response.data;
											} else {
												$scope.getResultMessage = "get All Scores Data Error!";
											}

										}, function(response) {
											$scope.getResultMessage = "Fail!";
										});

					}

					$scope.getAllAverageScorePerWeek = function() {

					}

					$scope.scoreAverage = function(user) {
						var userPointsPerDay = $scope.allTableFootballScores.data[user];
						var total = 0;
						var count = 0;
						angular.forEach(userPointsPerDay, function(v, k) {
							if (v !== null) {
								count = count + 1;
								total = total + v;
							}
						});
						if (count === 0) {
							return 0;
						}
						return total / count;
					}

					$scope.getDate = function(timestamp) {
						return new Date(timestamp);
					}

					$scope.getTitle = function(average) {
						var title = 'Szeregowy';
						switch (true) {
						case (average > 3.8):
							title = 'Generał';
							break;
						case (average > 3.7):
							title = 'Pułkownik';
							break;
						case (average > 3.6):
							title = 'Podpułkownik';
							break;
						case (average > 3.55):
							title = 'Major';
							break;
						case (average > 3.45):
							title = 'Kapitan';
							break;
						case (average > 3.15):
							title = 'Porucznik';
							break;
						case (average > 3.0):
							title = 'Podporucznik';
							break;
						case (average > 2.75):
							title = 'Chorąży';
							break;
						case (average > 2.45):
							title = 'Sierżant';
							break;
						case (average > 2.2):
							title = 'Plutonowy';
							break;
						case (average > 2.0):
							title = 'Kapral';
							break;
						}
						return title;
					}

					$scope.prepareChart = function() {
						google.charts.load('current', {
							packages : [ 'corechart', 'line' ]
						});
						google.charts.setOnLoadCallback(drawCurveTypes);

						function drawCurveTypes() {
							var data = new google.visualization.DataTable();
							data.addColumn('number', 'Week');

							angular.forEach($scope.users.data, function(u) {

								data.addColumn('number', u);
								console.log(u);
							})

							var url = $location.absUrl()
									+ "find/all/table/football/average-score/per/week";

							var config = {
								headers : {
									'Content-Type' : 'application/json;charset=utf-8;'
								}
							}

							$http
									.get(url, config)
									.then(
											function(response) {

												if (response.data.status == "Done") {
													var respData = response.data.data
													console.log(respData)

													data.addRows(respData);
													// Set chart options

													var options = {
														hAxis : {
															title : 'Week'
														},
														vAxis : {
															title : 'Average Points'
														},
														series : {
															1 : {
																curveType : 'function'
															}
														},
														'width' : 800,
														'height' : 500
													};

													var chart = new google.visualization.LineChart(
															document
																	.getElementById('chart_div'));
													chart.draw(data, options);
													;
												} else {
													$scope.getResultMessage = "get All Scores Data Error!";
												}

											},
											function(response) {
												$scope.getResultMessage = "Fail!";
											});

						}
					}

				});
