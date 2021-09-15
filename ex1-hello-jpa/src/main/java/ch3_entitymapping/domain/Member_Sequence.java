package ch3_entitymapping.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 기본키 매핑 - SEQUENCE 전략 사용
 * <pre>
 *	- DB에서 제공하는 시퀀스를 사용하는 방법으로 대표적으로 오라클에서 이 방법을 사용한다.
 *	- SEQUENCE 전략은 2가지 방법이 있다.
 *  	1. 하이버네이트에서 만들어주는 시퀀스를 사용
 *  	2. 시퀀스를 생성하여 사용(create sequence 시퀀스_명) 
 *	- 여기서는 하이버네이트에서 제공하는 기본 시퀀스를 사용하는 방법을 사용한다.
 * </pre>
 */
//@Entity
public class Member_Sequence {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false, name = "name")
	private String username;

	public Member_Sequence() {
		this("[SEQUENCE] 기본 name");
	}
	public Member_Sequence(Long id, String username) {
		this(username); // Generation의 3개 전략 전부 id가 필요가 없음
	}
	public Member_Sequence(String username) {
		this.username = username;
	}
	
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
