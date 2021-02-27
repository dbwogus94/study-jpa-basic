package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity		// jpa가 객체를 관리하게 만드는 어노테이션
//@Table(name = "${테이블명}") 	-> 	TABLE명과 class명이 같으면 @Entity만으로 매핑이 가능하다. 다른 경우 @Table를 사용한다.
public class Member {
	@Id		// jpa에게 PK를 알려주는것
	private Long id;
	//@Column(name = "${컬럼명}")		-> 테이블 컬럼명과 class 필드명이 다른경우 매핑하기 위해서 사용. 	
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
		return "Member [id=" + id + ", name=" + name + "]";
	}
}
