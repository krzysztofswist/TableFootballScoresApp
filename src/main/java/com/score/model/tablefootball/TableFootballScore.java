package com.score.model.tablefootball;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;



@Entity
@Table(name = "table_football_score")
public class TableFootballScore {
	private long scoreId;
	private Timestamp date;
	private Set<TableFootballUserPoint> userPoints;

	public TableFootballScore() {
		super();
	}

	public TableFootballScore(Timestamp date, Set<TableFootballUserPoint> userPoints) {
		super();
		this.date = date;
		this.userPoints = userPoints;
	}

	public TableFootballScore(long scoreId, Timestamp date, Set<TableFootballUserPoint> userPoints) {
		super();
		this.scoreId = scoreId;
		this.date = date;
		this.userPoints = userPoints;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "score_id")
	public long getScoreId() {
		return scoreId;
	}

	public void setScoreId(long scoreId) {
		this.scoreId = scoreId;
	}

	@Column(name = "date")
	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "score",cascade=CascadeType.ALL)
	public Set<TableFootballUserPoint> getUserPoints() {
		return userPoints;
	}

	public void setUserPoints(Set<TableFootballUserPoint> userPoints) {
		this.userPoints = userPoints;
	}

}
