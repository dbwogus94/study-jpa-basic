package ch4_relation_mapping.domain.double_mapping;

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
		name = "TEAM_SEQ_GENERATOR",
		sequenceName = "TEAM_SEQ",
		initialValue = 1,
		allocationSize = 1
		)
public class Team {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_SEQ_GENERATOR") 
	@Column(name = "TEAM_ID")
	private Long id;

	private String name;

	/**
	 * 연관관계의 주인이 <b>아닌</b>쪽은 mappedBy를 사용한다.
	 * <pre>
	 * - 연관관계를 가지지 않는 쪽은 조회만 가능하다
	 * - 즉, 외래 키를 변경하지 못한다.
	 * - mappedBy는 jpa에게 연관관계의 주인이 누구인지 알려주는 속성이다.
	 * - mappedBy의 값에는 연관관계의 주인을 가진 변수명이 들어간다.
	 * </pre>
	 */
	@OneToMany(					// 1(팀) : N(회원)
			mappedBy = "team")	// 연관관계의 주인인 변수명을 입력 즉, team에 의해 관리된다는 것을 명시 하는 것이다.
	List<Member> members = new ArrayList<Member>();
	// -> NullPointErr 방지를 위해 관례적으로 인스턴스 생성

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

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + "]";
	}
	
}
