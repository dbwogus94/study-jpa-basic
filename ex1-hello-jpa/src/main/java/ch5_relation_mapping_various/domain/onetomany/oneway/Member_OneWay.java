package ch5_relation_mapping_various.domain.onetomany.oneway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 일대다(1:N) 단방향 : Member Entity
 * <pre>
 * - Member에는 연관관계의 주인이 없다.
 * - 즉, Member Entity에서 MEMBER 테이블의 FK인 TEAM_ID을 관리하지 않는다.
 * - 단방향이기 때문에 Member에서는 Team을 찾을 수 없다.
 * </pre> 
 */
//@Entity
@Table(name = "MEMBER_ONE_WAY")
public class Member_OneWay {
	@Id @GeneratedValue 
	@Column(name = "MEMBER_ID")
	private Long id;
	
	@Column(name = "USERNAME")
	private String username;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
