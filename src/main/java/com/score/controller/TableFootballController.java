package com.score.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.score.message.Response;
import com.score.model.User;
import com.score.model.tablefootball.TableFootballScore;
import com.score.model.tablefootball.TableFootballUserPoint;
import com.score.model.tablefootball.vo.WeekVO;
import com.score.repo.TableFootballRepository;
import com.score.repo.TableFootballUserPointRepository;
import com.score.repo.UserRepository;
import com.score.rest.TableFootballScoreAverageDataPointJsonVO;
import com.score.rest.TableFootballScoreJsonVO;

@RestController
public class TableFootballController {

	@Autowired
	TableFootballRepository tableFootballRepository;

	@Autowired
	TableFootballUserPointRepository tableFootballUserPointRepository;

	@Autowired
	UserRepository userRepository;

	@RequestMapping("/find/all/table/football/scores")
	public Response findAll() {

		Iterable<TableFootballScore> tableFootballScores = tableFootballRepository.findAll();
		Iterable<User> users = userRepository.findAll();
		Set<TableFootballScoreAverageDataPointJsonVO> tableFootballScoreAverageDataPointJsonVOs = new HashSet<>();
		Map<String, TableFootballScoreAverageDataPointJsonVO> map = new TreeMap<>();
		Timestamp currentDate = new Timestamp(System.currentTimeMillis());
		for (TableFootballScore tableFootballScore : tableFootballScores) {
			Timestamp date = tableFootballScore.getDate();
			if (date.getTime() + 1400000000l > currentDate.getTime()) {
				for (User user : users) {
					String userId = user.getUserId();
					TableFootballScoreAverageDataPointJsonVO tableFootballScoreAverageDataPointJsonVO = map.get(userId);
					Map<Timestamp, Integer> datePoint = null;
					if (tableFootballScoreAverageDataPointJsonVO == null) {
						tableFootballScoreAverageDataPointJsonVO = new TableFootballScoreAverageDataPointJsonVO();
						datePoint = tableFootballScoreAverageDataPointJsonVO.getDatePoints();
						datePoint = new TreeMap<>();
						map.put(userId, tableFootballScoreAverageDataPointJsonVO);
					}
					datePoint = tableFootballScoreAverageDataPointJsonVO.getDatePoints();
					datePoint.put(date, null);
					map.put(userId, tableFootballScoreAverageDataPointJsonVO);

				}
				Set<TableFootballUserPoint> tableFootballUserPoints = tableFootballScore.getUserPoints();
				for (TableFootballUserPoint tableFootballUserPoint : tableFootballUserPoints) {
					String userId = tableFootballUserPoint.getUser().getUserId();
					int points = tableFootballUserPoint.getPoints();
					TableFootballScoreAverageDataPointJsonVO tableFootballScoreAverageDataPointJsonVO = map.get(userId);
					Map<Timestamp, Integer> datePoint = tableFootballScoreAverageDataPointJsonVO.getDatePoints();
					datePoint.put(date, points);
				}
			}
		}

		for (TableFootballScoreAverageDataPointJsonVO tableFootballScoreAverageDataPointJsonVO : map.values()) {
			Map<Timestamp, Integer> datePoint = tableFootballScoreAverageDataPointJsonVO.getDatePoints();
			double points = 0;
			int count = 0;
			for (Integer point : datePoint.values()) {
				if (point != null) {
					count++;
					points += point;
				}
			}
			if (count > 0) {
				tableFootballScoreAverageDataPointJsonVO.setAverage(points / count);
				tableFootballScoreAverageDataPointJsonVO.setCount(count);
			}
		}

		for (Map.Entry<String, TableFootballScoreAverageDataPointJsonVO> entry : map.entrySet()) {
			TableFootballScoreAverageDataPointJsonVO tableFootballScoreAverageDataPointJsonVO = entry.getValue();
			tableFootballScoreAverageDataPointJsonVO.setUserId(entry.getKey());
			tableFootballScoreAverageDataPointJsonVOs.add(tableFootballScoreAverageDataPointJsonVO);
		}

		return new Response("Done", tableFootballScoreAverageDataPointJsonVOs);
	}

	@RequestMapping(value = "/add/table/football/score", method = RequestMethod.POST)
	public void addTableFootbalScore(@RequestBody TableFootballScoreJsonVO tableFootballScoreJsonVO) {
		Timestamp date = new Timestamp(System.currentTimeMillis());
		Set<TableFootballUserPoint> userPoints = new HashSet<>();
		TableFootballScore tableFootballScore = new TableFootballScore(date, userPoints);
		User user1 = userRepository.findOne(tableFootballScoreJsonVO.getUser1());
		TableFootballUserPoint tableFootballUserPoint1 = new TableFootballUserPoint(user1,
				tableFootballScoreJsonVO.getPoint1(), tableFootballScore);
		userPoints.add(tableFootballUserPoint1);
		User user2 = userRepository.findOne(tableFootballScoreJsonVO.getUser2());
		TableFootballUserPoint tableFootballUserPoint2 = new TableFootballUserPoint(user2,
				tableFootballScoreJsonVO.getPoint2(), tableFootballScore);
		userPoints.add(tableFootballUserPoint2);
		User user3 = userRepository.findOne(tableFootballScoreJsonVO.getUser3());
		TableFootballUserPoint tableFootballUserPoint3 = new TableFootballUserPoint(user3,
				tableFootballScoreJsonVO.getPoint3(), tableFootballScore);
		userPoints.add(tableFootballUserPoint3);
		User user4 = userRepository.findOne(tableFootballScoreJsonVO.getUser4());
		TableFootballUserPoint tableFootballUserPoint4 = new TableFootballUserPoint(user4,
				tableFootballScoreJsonVO.getPoint4(), tableFootballScore);
		userPoints.add(tableFootballUserPoint4);
		tableFootballRepository.save(tableFootballScore);
	}

	@RequestMapping("/find/all/table/football/average-score/per/week")
	public Response findAverageScorePerWeek() {

		Iterable<TableFootballScore> tableFootballScores = tableFootballRepository.findAll();
		Map<Integer, Map<String, WeekVO>> weekNumberUserWeekVOs = new TreeMap<>();
		Set<String> usersSet = new HashSet<>();
		Calendar calendar = Calendar.getInstance();
		for (TableFootballScore tableFootballScore : tableFootballScores) {
			Timestamp date = tableFootballScore.getDate();
			calendar.setTime(date);
			Integer weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
			Set<TableFootballUserPoint> tableFootballUserPoints = tableFootballScore.getUserPoints();
			for (TableFootballUserPoint tableFootballUserPoint : tableFootballUserPoints) {
				String user = tableFootballUserPoint.getUser().getUserId();
				usersSet.add(user);
				Map<String, WeekVO> userWeekVOs = weekNumberUserWeekVOs.get(weekNumber);
				if (userWeekVOs == null) {
					userWeekVOs = new TreeMap<>();
					weekNumberUserWeekVOs.put(weekNumber, userWeekVOs);
				}
				WeekVO weekVO = userWeekVOs.get(user);
				if (weekVO == null) {
					weekVO = new WeekVO();
					userWeekVOs.put(user, weekVO);
				}
				int games = weekVO.getGames();
				int points = weekVO.getPoints();
				weekVO.setGames(games + 1);
				weekVO.setPoints(points + tableFootballUserPoint.getPoints());
			}
		}
		Double[][] weeksTab = null;
		List<String> users = new ArrayList<>(usersSet);
		Collections.sort(users);
		int numberOfUser = users.size();
		int i = 0;
		for (Map.Entry<Integer, Map<String, WeekVO>> entry : weekNumberUserWeekVOs.entrySet()) {
			int numberOfWeeks = weekNumberUserWeekVOs.keySet().size();
			if (weeksTab == null)
				weeksTab = new Double[numberOfWeeks][];
			Double[] data = new Double[numberOfUser + 1];
			data[0] = new Double(entry.getKey());
			weeksTab[i] = data;
			for (Map.Entry<String, WeekVO> userWeekVO : entry.getValue().entrySet()) {
				String user = userWeekVO.getKey();
				int userIndex = users.indexOf(user) + 1;
				WeekVO weekVO = userWeekVO.getValue();
				int games = weekVO.getGames();
				int points = weekVO.getPoints();
				if (games != 0) {
					data[userIndex] = points * 1.0 / games;
				}
			}
			i++;
		}

		return new Response("Done", weeksTab);
	}

}
