package ch5_relation_mapping_various.domain.manytoone.doubleway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//@Entity
@Table(name = "MEMBER_DOUBLE_WAY")
public class Member_DoubleWay {
	@Id @GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;
	
	@Column(name = "USERNAME")
	private String username;
	
	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team_DoubleWay team;		// 연관관계의 주인

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Team_DoubleWay getTeam() {
		return team;
	}

	public void setTeam(Team_DoubleWay team) {
		this.team = team;
	}
	
	@Override
	public String toString() {
		return "Member [id=" + id + ", username=" + username + ", team=" + team + "]";
	}
}
