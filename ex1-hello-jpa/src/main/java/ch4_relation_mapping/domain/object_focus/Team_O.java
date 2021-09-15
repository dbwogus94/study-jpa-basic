package ch4_relation_mapping.domain.object_focus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 객체 지향 모델링   
 */
//@Entity
@Table(name="TEAM")
public class Team_O {
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

	@Override
	public String toString() {
		return "Team_O [id=" + id + ", name=" + name + "]";
	}
	
}
