package ch2_persistence_context.domain;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <H1> 요구사항을 추가한 엔티티 Member </H1>
 * <PRE>
 * - 회원은 일반 회원과 관리자로 구분해야 한다.
 * - 회원은 가입일과 수정일이 있어야 한다.
 * - 회원은 설명할 수 있는 필드가 있어야 한다. 이 필드는 길이 제한이 없다.
 * </PRE>
 */
//@Entity(name = "Member_part2")			// "데이터 베이스 스키마 자동생성"을 사용하면 Table명을 name의 값으로 하여 테이블이 생성된다.
public class Update_Member {
	@Id									// pk
	private Long id;
	@Column(name = "name") 				// 필드명과 컬럼명이 다른 경우 : DB 컬럼명은 NAME
	private String username;
	
	private Integer age;				// Integer과 가장 흡사한 타입으로 컬럼 타입 생성한다.
	
	@Enumerated(EnumType.STRING)		// DB에는 EnumType타입이 없다. 그런경우 @Enumerated를 사용한다. STRING로 변경하여 사용!
	private RoleType roleType;
	
	/**
	* <h1>@Temporal 생략 불가</h1>
	* java의 Date는 날짜 + 시간을 모두 가지고 있다.<br>
	* 반면 DB의 날짜 타입은 날짜, 시간, 날짜+시간 세가지로 나눠져 있다.<br>
	* 그렇기 때문에 JPA를 사용하면 <b>java날짜 객체와 DB간의 매핑정보를 명시해 줘야 한다.</b>
	* <pre>
	* - DB 날짜 타입 매핑 : TemporalType.DATE
	* - DB 시간 타입 매핑 : TemporalType.TIME
	* - DB 시간+날짜 매핑: TemporalType.TIMESTAMP
	* </pre>
	*/
	@Temporal(TemporalType.TIMESTAMP)	
	private Date createdDate;
	
	/**
	* <h1>@Temporal 생략 가능</h1> 
	* java 1.8부터는 @Temporal을 생략할 수 있는 객체를 제공한다.
	* <pre>
	* - private LocalTime time : 시간 컬럼 타입과 매핑
	* - private LocalDate date : 날짜 컬럼 타입과 매핑
	* - private LocalDateTime dateTime : 시간 + 날짜 컬럼과 매핑
	* </pre>
	*/
	private LocalDateTime lastModifiedDate;
	
	@Lob							// 큰 데이터 타입을 DB와 매핑할 때 사용한다.(문자타입 CLOB을 사용, 나머지는 BLOB를 사용한다)
	private String description;
	
	// 생성자 
	// Getter, Setter…

}
