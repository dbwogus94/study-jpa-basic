package ch4_relation_mapping.domain.double_mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

//@Entity
@SequenceGenerator(
		name = "MEMBER_SEQ_GENERATOR",
		sequenceName = "MEMBER_SEQ",
		initialValue = 1,
		allocationSize = 5
		)
public class Member {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR") 
	@Column(name = "MEMBER_ID")
	private Long id;
	
	@Column(name = "USERNAME")
	private String name;
	
	/**
	 * 연관관계의 주인 
	 * <pre>
	 *  - 객체간 양방향 연관관계를 구현하는 경우 연관관계의 주인을 설정해야 한다.
	 *  - 연관관계의 주인을 가지는 객체에서만 외래 키를 등록, 수정 할 수 있다.
	 * </pre>
	 * 연관 관계의 주인은 누구로 하는가?
	 * <pre>
	 *  - 외래 키를 가지는 쪽이 연관관계의 주인이 된다.
	 *  - 즉, N : 1관계에서는 항상 N인 쪽이 연관관계의 주인이 된다.
	 * </pre>
	 */
	@ManyToOne						// N:1 - 여러회원(N)은 하나의 팀(1)을 가진다.
	@JoinColumn(name = "TEAM_ID")	// join에 필요한 외래 키 정의
	private Team team;

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

	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	/**
	 * 연관관계 편의 메서드  - 연관관계 주인인 team 매핑과, 역방향 매핑이 같이 되도록 메서드 정의
	 * <pre>
	 * - 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자.
	 * - 연관관계를 만들때 실수하지 않게 편의 메서드를 설정.
	 * 
	 * -> setTeam()에 역방향 코드를 구현하지 않는 이유는 
	 * setter는 관례 때문에 생각없이 사용하기 쉽기 때문이다. 
	 * 그래서 명시적으로 다른 메서드를 작성하여 사용하는 것이 좋다. 
	 * </pre>
	 */
	public void changeTeam(Team team) {
		/* 연관관계 주인 매핑 */
		setTeam(team);
		/* 역방향 매핑 */
		team.getMembers().add(this);
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", team=" + team + "]";
	}
}