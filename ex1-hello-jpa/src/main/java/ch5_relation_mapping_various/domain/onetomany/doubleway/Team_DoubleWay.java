package ch5_relation_mapping_various.domain.onetomany.doubleway;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//@Entity
@Table(name = "TEAM_DOUBLE_WAY")
public class Team_DoubleWay {
	@Id @GeneratedValue
	@Column(name = "TEAM_ID")
	private Long id;
	
	private String name;
	
	@OneToMany
	@JoinColumn(name = "TEAM_ID")		// 연관관계의 주인 : MEMBER의 FK를 여기넣어 Team이 외래키를 관리함.
	private List<Member_DoubleWay> members = new ArrayList<Member_DoubleWay>();
	
	/**
	 * 연관관계 편의 매서드
	 */
	public void addMembers(Member_DoubleWay member) {
		// 연관관계 주인 매핑
		this.members.add(member);
		// 역방향 매핑
		member.setTeam(this);
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
		return "Team_DoubleWay [id=" + id + ", name=" + name + "]";
	}
}
