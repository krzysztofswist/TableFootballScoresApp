package com.score.rest;

import java.sql.Timestamp;
import java.util.Map;
import java.util.TreeMap;

public class TableFootballScoreAverageDataPointJsonVO implements Comparable<TableFootballScoreAverageDataPointJsonVO> {
	private double average;
	private int count;
	private String userId;
	private Map<Timestamp, Integer> datePoints = new TreeMap<>();

	public TableFootballScoreAverageDataPointJsonVO(double average, int count, String userId,
			Map<Timestamp, Integer> datePoints) {
		super();
		this.average = average;
		this.count = count;
		this.userId = userId;
		this.datePoints = datePoints;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public TableFootballScoreAverageDataPointJsonVO() {
		super();
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public Map<Timestamp, Integer> getDatePoints() {
		return datePoints;
	}

	public void setDatePoints(Map<Timestamp, Integer> datePoints) {
		this.datePoints = datePoints;
	}

	@Override
	public int compareTo(TableFootballScoreAverageDataPointJsonVO o) {
		return (average > o.average) ? -1 : ((average == o.average) ? 0 : 1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableFootballScoreAverageDataPointJsonVO other = (TableFootballScoreAverageDataPointJsonVO) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
