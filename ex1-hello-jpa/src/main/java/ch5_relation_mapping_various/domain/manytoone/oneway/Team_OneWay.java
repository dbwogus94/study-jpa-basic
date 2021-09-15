package ch5_relation_mapping_various.domain.manytoone.oneway;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
@Table(name = "TEAM_ONE_WAY")
public class Team_OneWay {
	@Id @GeneratedValue
	@Column(name = "TEAM_ID")
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
		return "Team [id=" + id + ", name=" + name + "]";
	}
}
