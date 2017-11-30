package com.score.model.tablefootball;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.score.model.User;

@Entity
@Table(name = "table_football_user_point")
public class TableFootballUserPoint {

	private long userPointId;
	private User user;
	private int points;
	private TableFootballScore score;

	public TableFootballUserPoint() {
		super();
	}

	public TableFootballUserPoint(User user, int points, TableFootballScore score) {
		super();
		this.user = user;
		this.points = points;
		this.score = score;
	}

	public TableFootballUserPoint(long userPointId, User user, int points, TableFootballScore score) {
		super();
		this.userPointId = userPointId;
		this.user = user;
		this.points = points;
		this.score = score;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_point_id")
	public long getUserPointId() {
		return userPointId;
	}

	public void setUserPointId(long userPointId) {
		this.userPointId = userPointId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "score_id")
	public TableFootballScore getScore() {
		return score;
	}

	public void setScore(TableFootballScore score) {
		this.score = score;
	}

}
