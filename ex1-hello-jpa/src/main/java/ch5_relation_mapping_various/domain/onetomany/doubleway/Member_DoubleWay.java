package ch5_relation_mapping_various.domain.onetomany.doubleway;

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
	
	@Column(name = "USER_NAME")
	private String username;
	
	@ManyToOne
	@JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
	private Team_DoubleWay team;
	/*
	 * 일대다 관계에서 양방향 매핑은 JPA에서 지원하지 않는다. 그렇기 때문에 우회하는 방법으로 강제 구현이 가능하다.
	 * 1. 다대일 처럼 관계를 맺는다.
	 * 2. 연관관계의 주인이 아닌 쪽은 입력, 수정이 불가능함.
	 * -> 그렇기 때문에 insert와 update를 못하게 설정한다.  
	 */
	

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
		return "Member_DoubleWay [id=" + id + ", username=" + username + ", team=" + team + "]";
	}
	
}

