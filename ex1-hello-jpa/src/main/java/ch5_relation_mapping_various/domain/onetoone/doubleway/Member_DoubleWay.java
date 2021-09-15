package ch5_relation_mapping_various.domain.onetoone.doubleway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 일대일 양방향 매핑 : 회원 Entity 
 */
@Entity
@Table(
	name = "MEMBER_DOUBLE_WAY",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "LOCKER_ID_UNIQUE",
			columnNames = {"LOCKER_ID"} 	// 일대일 매핑은 유니크 제약조건을 부여해야함.
		)
	}
)
public class Member_DoubleWay {
	@Id @GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	@OneToOne
	@JoinColumn(name = "LOCKER_ID")		// 연관관계의 주인
	private Locker_DoubleWay locker;
	
	@Column(name = "USER_NAME")
	private String username;
	
	/**
	 *  연관관계 편의 메서드 - 회원에서 사물함 추가
	 */
	public void changeLocker(Locker_DoubleWay locker) {
		// 연관관계 주인 매핑
			this.setLocker(locker);
			// 역방향 매핑
			locker.setMember(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Locker_DoubleWay getLocker() {
		return locker;
	}

	public void setLocker(Locker_DoubleWay locker) {
		this.locker = locker;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Member_DoubleWay [id=" + id + ", locker=" + locker + ", username=" + username + "]";
	}
}

