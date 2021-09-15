package ch3_entitymapping.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 * 기본키 매핑 - GenerationType.TABLE 전략
 * <pre>
 *  - 이 전략의 특징은 특정 DB에 종속적이지 않는 방법으로 pk를 생성한다는 점이다.
 *  - Table 방식은 pk를 관리하는 table를 생성하고 그 값을 외래키로 Entity에 적용하는 방식이다.
 *  - 당연하게도 3개의 전략중 가장 느리지만 가장 높은 호환성을 가진다.
 *  - @TableGenerator() 사용법
 *  	1. name : TableGenerator이름 정의(필수)
 *  	2. table : 매핑또는 생성할 테이블 명 정의
 *  	3. pkColumnValue : pk의 값으로 사용될 시퀀스_명
 *  	4. allocationSize : 테이블 시퀀스 증가값 설정(성능 최적화에 사용)
 * </pre>
 */
//@Entity
@TableGenerator(
		name = "MEMBER_SEQ_GENERATOR",	// TableGenerator 이름
		table = "MY_SEQUENCES",			// DB 테이블 명
		pkColumnValue = "MEMBER_SEQ",	// pk값으로 사용될 시퀀스_명
		allocationSize = 1)				// 시퀀스 호출시 증가량
public class Member_Table {
	@Id
	@GeneratedValue(
			strategy = GenerationType.TABLE,
			generator = "MEMBER_SEQ_GENERATOR")  // generator를 사용하여 테이블 시퀀스에 매핑
	private Long id;
	@Column(nullable = false, name = "name")
	private String username;
	
	public Member_Table() {
		this("[TABLE] 기본 name");
	}
	public Member_Table(Long id, String username) {
		this(username); 	// Generation의 3개 전략 전부 id가 필요가 없음
	}
	public Member_Table(String username) {
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
