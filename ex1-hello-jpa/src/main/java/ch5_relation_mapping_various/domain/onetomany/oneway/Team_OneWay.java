package ch5_relation_mapping_various.domain.onetomany.oneway;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 일대다(1:N) 단방향 : Team Entity
 * <pre>
 * - 이 관계는 Team에서 연관관계의 주인을 관리한다.
 * - 즉, Team에서 외래 키를 관리한다.
 * - @joinColumn(name = "${외래 키}")이 속성이 필수이다.
 * </pre> 
 */
//@Entity
@Table(name = "TEAM_ONE_WAY")
public class Team_OneWay {
	@Id @GeneratedValue 
	@Column(name = "TEAM_ID")
	private Long id;

	private String name;

	@OneToMany
	@JoinColumn(name = "TEAM_ID")		// 이 속성이 빠지면 안된다.
	List<Member_OneWay> members = new ArrayList<Member_OneWay>();		// MEMBER 테이블의 FK인 TEAM_ID를 관리하는 연관관계의 주인이다.

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

	public List<Member_OneWay> getMembers() {
		return members;
	}

	public void setMembers(List<Member_OneWay> members) {
		this.members = members;
	}
}
