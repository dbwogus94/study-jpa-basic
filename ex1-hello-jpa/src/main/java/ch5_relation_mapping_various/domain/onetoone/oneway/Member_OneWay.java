package ch5_relation_mapping_various.domain.onetoone.oneway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
/**
 *	일대일 단방향 : 매핑 회원 Entity 
 */
//@Entity
@Table(name = "MEMBER_ONE_WAY", 
	uniqueConstraints = {
		@UniqueConstraint(
			name = "LOCKER_ID_UNIQUE",		// 일대일 매핑은 유니크 제약조건을 부여해야함.
			columnNames = {"LOCKER_ID"}
		)
	}
)
public class Member_OneWay {
	@Id @GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "LOCKER_ID")
	private Locker_OneWay locker;
	
	@Column(name = "USER_NAME")
	private String username;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Locker_OneWay getLocker() {
		return locker;
	}

	public void setLocker(Locker_OneWay locker) {
		this.locker = locker;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
