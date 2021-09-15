package ch4_relation_mapping.domain.object_focus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 객체 지향 모델링    
 */
//@Entity
@Table(name="MEMBER")
public class Member_O {
	@Id 
	@GeneratedValue 
	@Column(name = "MEMBER_ID")
	private Long id;
	
	@Column(name = "USERNAME")
	private String name;
	
	/* 팀 객체를 참조하기 위해서는 2개의 어노테이션을 사용해야함 
	 * 1. 참조객체와 연관관계를 알려주는 어노테이션 : @ManyToOne, @OneToMany, @ManyToMany 
	 * 2. 테이블 기준으로 조인할 컬럼 : @JoinColumn
	 */
	@ManyToOne						// N:1 - 여러회원(N)은 하나의 팀(1)을 가진다.
	@JoinColumn(name = "TEAM_ID")
	private Team_O team;

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

	public Team_O getTeam() {
		return team;
	}

	public void setTeam(Team_O team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return "Member_O [id=" + id + ", name=" + name + ", team=" + team + "]";
	}
	
	
}
