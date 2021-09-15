package ch1_hellojpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

//@Entity
//@Table(name = "${테이블명}") 																					// TABLE명과 class명이 같으면 @Entity만으로 매핑이 가능하다. 다른 경우 @Table를 사용한다.
//@Table(uniqueConstraints = { @UniqueConstraint( name = "NAME_AGE_UNIQUE", columnNames = {"NAME", "AGE"} ) }) 	// 스키마 자동 생성 기능에서 DDL 생성기능 : 2가지 이상 컬럼에 유니크 제약 조건 추가할때 주로 사용된다. 
public class Member {
	@Id																											// jpa에게 PK를 알려주는것  - *필수 : 어노테이션중 어떤것을 사용할지 애매하면 javax.persistence.*에서  찾는다.
	private Long id;
	
	//@Column(name = "${컬럼명}")																					// 테이블 컬럼명과 class 필드명이 다른경우 매핑하기 위해서 사용.
	@Column(unique = true, length = 30, nullable = false)		// 유니크, 길이 10으로 제한, not null
	private String name;
	
	public Member() {
		// JPA는 기본 생성자 필수(파라미터가 없는 public 또는 protected 생성자가 있어야 한다.)
	}
	public Member(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
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
		return "Member [id=" + id + ", name=" + name + "]";
	}
}
