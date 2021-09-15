package ch3_entitymapping.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 기본키 매핑 - 기본값 GenerationType.AUTO 사용
 * <pre>
 * - 설정을 AUTO로 하게 되면 IDENTITY, SEQUENCE, TABLE중에 DB 특성에 맞는 전략으로 선택이 된다. 
 * </pre>
 */
//@Entity
public class Member_Auto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)  
	private Long id;
	private String username;

	// getter, setter
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

	@Override
	public String toString() {
		return "Member [id=" + id + ", username=" + username + "]";
	}
}
