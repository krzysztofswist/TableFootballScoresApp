package com.score.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.score.model.tablefootball.TableFootballUserPoint;

@Entity
@Table(name="app_user")
public class User {
	private String userId;
	private Set<TableFootballUserPoint> userPoints;

	@Id
	@Column(name="user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<TableFootballUserPoint> getUserPoints() {
		return userPoints;
	}

	public void setUserPoints(Set<TableFootballUserPoint> userPoints) {
		this.userPoints = userPoints;
	}

}
