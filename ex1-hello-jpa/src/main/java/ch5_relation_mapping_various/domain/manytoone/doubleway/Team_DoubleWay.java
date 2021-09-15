package ch5_relation_mapping_various.domain.manytoone.doubleway;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//@Entity
@Table(name = "TEAM_DOUBLE_WAY")
public class Team_DoubleWay {
	@Id @GeneratedValue
	@Column(name = "TEAM_ID")
	private Long id;
	
	private String name;

	// 양방향 매핑
	@OneToMany(mappedBy = "team")  // mappedBy는 연관관계의 주인이 누구인지 JAP에게 알려주는 역할이다.
	private List<Member_DoubleWay> members = new ArrayList<Member_DoubleWay>();
	
	/**
	 * 양방향 연관관계 연관관계 편의 매서드<BR>
	 * - 팀에 회원을 넣는 것이 자연스럽기 때문에 Team에 연관관계 메서드 설정
	 */
	public void changeTeam(Member_DoubleWay member) {
		// 연관관계 주인 매핑
		member.setTeam(this);
		// 역방향 매핑
		this.members.add(member);
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

	public List<Member_DoubleWay> getMembers() {
		return members;
	}

	public void setMembers(List<Member_DoubleWay> members) {
		this.members = members;
	}
	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + "]";		
		// 양방향 매핑에서 참조된 객체끼리 호출하면 무한 루프에 빠지기 때문에 members는 호출하지 않는다.
	}
}
