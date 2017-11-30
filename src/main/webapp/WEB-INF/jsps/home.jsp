<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Table Football Scores</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.6.0/angular.min.js"></script>
<script src="/table-football-scores/js/angular.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/table-football-scores/css/styles.css">
</head>
<body>
	<div class="container-fluid" ng-app="app" ng-controller="globalController"
		ng-init="init()">


		<div class="row" ng-init="getAllScores()">
			<div class="col-md-12">
				<h3>Table Football Scores</h3>
				<!-- <span ng-class="{'rank rank-lg rank-03-e2':d.average>1.4,'rank rank-lg rank-03-e4-1':d.average>2.0,'rank rank-lg rank-03-e5':d.average>2.4,'rank rank-lg rank-03-e6':d.average>3.0,'rank rank-lg rank-03-e7':d.average>3.13,'rank rank-lg rank-03-e8-2':d.average>3.4,'rank rank-lg rank-03-e9-1':d.average>3.6,'rank rank-lg rank-03-e9-2':d.average>3.8,'rank rank-lg rank-03-e9-3':d.average>4,}"></span> -->
				<table class="table table-bordered table-striped">
					<thead>
						<tr>
							<th>Position</th>
							<th>User</th>
							<th>#Games</th>
							<th>Average Points</th>
							<th
								ng-repeat="(k,v) in allTableFootballScores.data[0].datePoints"
								style="position: relative; max-width: 30px; height: 65px; top: 15px; left: -10px; -webkit-transform: rotate(-90deg); -moz-transform: rotate(-90deg); -ms-transform: rotate(-90deg); -o-transform: rotate(-90deg); white-space: nowrap;"><nobr>{{k|date:'dd-MM-yy'}}</nobr></th>

						</tr>
					</thead>
					<tbody>
						<tr
							ng-repeat="d in allTableFootballScores.data|orderBy:['-average','-count'] track by $index">
							<td>{{$index+1}}</td>
							<td>{{getTitle(d.average)}} {{d.userId}}</td>
							<td>{{d.count}}</td>
							<td>{{d.average|number:2}}</td>
							<td ng-repeat="point in d.datePoints"
								ng-class="{'bg-success':point>3,'bg-warning':point===3,'bg-danger':point!==null&&point<3}">{{point}}</td>
						</tr>

					</tbody>
				</table>
				<p>{{getResultMessage}}</p>
			</div>
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
							<input type="number" class="form-control input-sm" type="text"
								name="user1" class="form-control" ng-model="data.point1" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">User 2: </label> <select
							ng-model="data.user2" class="col-sm-4">
							<option ng-repeat="user in users.data">{{user}}</option>
						</select> <label class="col-sm-2 col-form-label">Points: </label>
						<div class="col-sm-4">
							<input type="number" class="form-control input-sm" type="text"
								name="user2" class="form-control" ng-model="data.point2" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">User 3: </label> <select
							ng-model="data.user3" class="col-sm-4">
							<option ng-repeat="user in users.data">{{user}}</option>
						</select> <label class="col-sm-2 col-form-label">Points: </label>
						<div class="col-sm-4">
							<input type="number" class="form-control input-sm" type="text"
								name="user3" class="form-control" ng-model="data.point3" />
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

					<button type="submit"
						ng-disabled="data.point1+data.point2+data.point3+data.point4!==12||data.user1===''||data.user2===''||data.user3===''||data.user4===''"
						class="btn btn-primary">Submit</button>
				</form>
				<p>{{postResultMessage}}</p>
			</div>
		</div>

	</div>
</body>
</html>