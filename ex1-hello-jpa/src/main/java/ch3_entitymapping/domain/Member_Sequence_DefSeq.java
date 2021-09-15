package ch3_entitymapping.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * 기본키 매핑 - SEQUENCE 전략 사용, 시퀀스 정의
 * <pre>
 *	- DB에서 제공하는 시퀀스를 사용하는 방법으로 대표적으로 오라클에서 이 방법을 사용한다.
 *	- SEQUENCE 전략은 2가지 방법이 있다.
 *  	1. 하이버네이트에서 만들어주는 시퀀스를 사용
 *  	2. 시퀀스를 생성하여 사용(create sequence 시퀀스_명) 
 *		-> 시퀀스를 생성하는 방법은  @SequenceGenerator()를 사용한다.
 *	- @SequenceGenerator() 사용법
 *   	1. SequenceGenerator의 이름을 name를 사용하여 정의(필수)
 *    	2. DB에 생성될 시퀀스명을 sequenceName를 사용하여 정의
 *    	3. initialValue을 사용하여 시퀀스 시작값 설정
 *    	4. allocationSize를 사용하여 시퀀스 증가량 설정(기본값 : 50, 성능 최적화에 사용)
 * </pre>
 */
//@Entity
/* 시퀀스 생성자 정의 */ 
@SequenceGenerator(
		 name = "MEMBER_SEQ_GENERATOR",			// SequenceGenerator의 이름을 정의 	 				
		 sequenceName = "MEMBER_SEQ", 			// 매핑할 데이터베이스 시퀀스 이름 -> DDL자동생성을 사용하는 경우 해당 이름으로 생성 (create sequence MEMBER_SEQ)
		 initialValue = 1, 						// 시퀀스 초기값 정의
		 allocationSize = 100) 					// 시퀀스 증가값 정의
public class Member_Sequence_DefSeq {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = "MEMBER_SEQ_GENERATOR")	// generator에 SequenceGenerator의 이름을 사용하여 PK에 시퀀스 매핑  
	private Long id;
//	@Column(nullable = false, name = "name")
	private String username;

	public Member_Sequence_DefSeq() {
		this("[Def_SEQUENCE] 기본 name");
	}
	public Member_Sequence_DefSeq(Long id, String username) {
		this(username); 	// Generation의 3개 전략 전부 id가 필요가 없음
	}
	public Member_Sequence_DefSeq(String username) {
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
