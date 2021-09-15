package ch4_relation_mapping.domain.relation_focus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 객체를 테이블에 맞추어 모델링   
 */
//@Entity
public class Team_R {
	@Id @GeneratedValue @Column(name = "TEAM_ID")
	private Long id;

	private String name;

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
}
