<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Spring Boot Example</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.6.0/angular.min.js"></script>
<script src="/table-football-scores/js/angular.js"></script>
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" />
</head>
<body>
	<div class="container" ng-app="app" ng-controller="globalController"
		ng-init="init()">


		<div class="row" class="col-md-3" ng-init="getAllScores()">
			<h3>Table Football Scores</h3>

			<table class="table table-bordered table-striped">
				<tbody>
					<tr ng-repeat="(user,points) in allTableFootballScores.data">
						<td>{{user}}</td>
						<td ng-repeat="point in points">{{point}}</td>
					</tr>

				</tbody>
			</table>
			<p>{{getResultMessage}}</p>
		</div>

		<div class="row">
			<div class="col-md-6">
				<form name="scoreForm" ng-submit="submitForm()">
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">User 1: </label> <select
							ng-model="data.user1" class="col-sm-4">
							<option ng-repeat="user in users.data">{{user}}</option>
						</select> <label class="col-sm-2 col-form-label">Points: </label>
						<div class="col-sm-4">
							<input class="form-control input-sm" type="text" name="user1"
								class="form-control" ng-model="data.point1" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">User 2: </label> <select
							ng-model="data.user2" class="col-sm-4">
							<option ng-repeat="user in users.data">{{user}}</option>
						</select> <label class="col-sm-2 col-form-label">Points: </label>
						<div class="col-sm-4">
							<input class="form-control input-sm" type="text" name="user2"
								class="form-control" ng-model="data.point2" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">User 3: </label> <select
							ng-model="data.user3" class="col-sm-4">
							<option ng-repeat="user in users.data">{{user}}</option>
						</select> <label class="col-sm-2 col-form-label">Points: </label>
						<div class="col-sm-4">
							<input class="form-control input-sm" type="text" name="user3"
								class="form-control" ng-model="data.point3" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">User 4: </label> <select
							ng-model="data.user4" class="col-sm-4">
							<option ng-repeat="user in users.data">{{user}}</option>
						</select> <label class="col-sm-2 col-form-label">Points: </label>
						<div class="col-sm-4">
							<input type="number" class="form-control input-sm" type="text"
								name="user4" class="form-control" ng-model="data.point4" />
						</div>
					</div>

					<button type="submit" class="btn btn-primary">Submit</button>
				</form>
				<p>{{postResultMessage}}</p>
			</div>
		</div>

	</div>
</body>
</html>