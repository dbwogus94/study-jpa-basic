package ch5_relation_mapping_various.domain.onetoone.oneway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *	일대일 단방향 매핑 : 사물함 Entity 
 */
//@Entity
@Table(name = "LOCKER_ONE_WAY")
public class Locker_OneWay {
	@Id @GeneratedValue
	@Column(name = "LOCKER_ID")
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
