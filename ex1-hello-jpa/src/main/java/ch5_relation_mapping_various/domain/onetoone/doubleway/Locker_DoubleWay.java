package ch5_relation_mapping_various.domain.onetoone.doubleway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 일대일 양방향 매핑 : 사물함 Entity
 */
@Entity
@Table(name = "LOCKER_DOUBLE_WAY")
public class Locker_DoubleWay {
	@Id @GeneratedValue
	@Column(name = "LOCKER_ID")
	private Long id;
	
	private String name;
	
	/* 일대일 양방향 매핑 */
	@OneToOne(mappedBy = "locker")		// 연관관계 주인을 JPA에게 알림
	private Member_DoubleWay member;

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

	public Member_DoubleWay getMember() {
		return member;
	}

	public void setMember(Member_DoubleWay member) {
		this.member = member;
	}

	@Override
	public String toString() {
		return "Locker_DoubleWay [id=" + id + ", name=" + name + "]";
	} 
}
