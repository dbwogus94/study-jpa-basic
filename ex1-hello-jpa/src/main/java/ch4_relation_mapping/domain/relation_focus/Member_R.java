package ch4_relation_mapping.domain.relation_focus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 객체를 테이블에 맞추어 모델링   
 */
//@Entity
public class Member_R {
	@Id @GeneratedValue @Column(name = "MEMBER_ID")
	private Long id;
	
	@Column(name = "USERNAME")
	private String name;
	
	@Column(name = "TEAM_ID")
	private Long teamId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
}
