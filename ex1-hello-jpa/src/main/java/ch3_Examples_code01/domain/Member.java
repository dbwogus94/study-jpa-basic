package ch3_Examples_code01.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
public class Member {
	@Id @GeneratedValue(strategy = GenerationType.AUTO) // 기본값 안써도 무관
	@Column(name = "MEMBER_ID")	// 소문자 대문자 상관없음
	private Long id;
	
	private String name;
	
	private String city;		// 도시(주소)
	
	private String street;		// 도로명(주소)
	
	@Column(length = 6)
	private String zipCode;		// 우편번호
	
	
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
}
