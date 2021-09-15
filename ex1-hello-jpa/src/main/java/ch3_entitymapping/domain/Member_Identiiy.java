package ch3_entitymapping.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 기본키 매핑 - IDENTITY 전략
 * <pre>
 * - 이 전략은 기본키 생성을 DB에게 위임하는 전략이다.
 * - 대표적으로 MySQL의 AUTO_INCREMENT 기능이 있다.
 * - 이 전략의 특징은 insert SQL에 pk값을 생성하지 않고 DB에 저장시 DB에서 pk를 만들어서 적용시킨다.
 * - JPA는 일반적으로 commit()시점에 플러시가 발생하고 SQL이 DB에 요청된다.
 * - 하지만 이 전략을 사용하면 em.persist()시점에 DB에 Insert SQL을 요청한다.   
 * </pre>
 */
//@Entity
public class Member_Identiiy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, name = "name")
	private String username;

	public Member_Identiiy() {
		this("[IDENTIIY] 기본 name");
	}
	public Member_Identiiy(Long id, String username) {
		this(username); // Generation의 3개 전략 전부 id가 필요가 없음
	}
	public Member_Identiiy(String username) {
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
