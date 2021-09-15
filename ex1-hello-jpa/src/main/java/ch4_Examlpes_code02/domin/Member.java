package ch4_Examlpes_code02.domin;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

//@Entity
@SequenceGenerator(
		name = "MEMBER_SEQ_GENERATOR",
		sequenceName = "MEMBER_SEQ",
		initialValue = 1,
		allocationSize = 1
		)
public class Member {
	@Id
	@Column(name = "MEMBER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
	private Long id;
	
	private String name;
	
	private String city;		// (주소)도시
	
	private String street;		// (주소)나머지
	
	@Column(length = 6)
	private String zipCode;		// 우편 번호
	
	/* 양방향 매핑 */
	@OneToMany(mappedBy = "member")		// 연관관계의 주인을 가리킨다.
	private List<Order> orders = new ArrayList<Order>();
	// 구조상으로 회원에서 회원이 주문한 주문리스트를 가져오는것은 오류가 있다. 하지만 연습예제이기 떄문에 허용한다.

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", city=" + city + ", street=" + street + ", zipCode=" + zipCode + "]";
	}
}
