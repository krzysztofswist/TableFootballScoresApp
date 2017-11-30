package com.score.controller;

import java.sql.Timestamp;
import java.util.HashSet;
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

}
