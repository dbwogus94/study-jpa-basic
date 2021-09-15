package ch5_relation_mapping_various;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ch5_relation_mapping_various.domain.onetomany.doubleway.Member_DoubleWay;
import ch5_relation_mapping_various.domain.onetomany.doubleway.Team_DoubleWay;
import ch5_relation_mapping_various.domain.onetomany.oneway.Member_OneWay;
import ch5_relation_mapping_various.domain.onetomany.oneway.Team_OneWay;

/**
 * 일대다(1:N) 관계 매핑 예시
 * <pre>
 * - 예시1) 단방향 매핑
 * - 예시2) 양방향 매핑
 * </pre>
 */
public class OneToMany {
	public static void main(String[] args) {
		OneToMany t = new OneToMany();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//		t.testCase01(emf);
		t.testCase02(emf);
		emf.close();
	}
	/**
	 *	단방향 매핑 - 회원과 팀 생성, 연관관계 생성 
	 */
	void testCase01(EntityManagerFactory emf) {
		System.out.println("\n\n[테스트 01 - OneToMany 단방향 매핑]");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		try {
			Member_OneWay member = new Member_OneWay();
			member.setUsername("[oneToMany 단방향] 1번 회원_xxx");
			em.persist(member);
			
			Team_OneWay team = new Team_OneWay();
			team.setName("[oneToMany 단방향] A팀");
			/* Team이 연관관계의 주인가지고 있다. 
			 * - 연관관계의 주인을 가진 Team을 통해 MEMBER 테이블의 FK인 TEAM_ID에 값을 추가 할 수 있다.
			 */ 
			team.getMembers().add(member);		// 이 코드는 MEMBER 테이블의 FK변경시키는 코드이다.
			em.persist(team);
			
			/* 일(1)이 연관관계의 주인을 가진, 일대다 매핑에서는 마지막에 UPDATE SQL이 추가된다.
				1. 연관관계의 주인이 Team에 있다. 
				2. MEMBER의 FK를 TEAM에서 관리하는 것이다.
				3. 그렇기 때문에 TEAM의 연관관계의 주인이 변경되었다면
				MEMBER의 FK를 수정하는 SQL을 수정이 필요하다.
			*/
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	/**
	 *	양방향 매핑 - 회원과 팀 생성, 연관관계 생성 
	 */
	void testCase02(EntityManagerFactory emf) {
		System.out.println("\n\n[테스트 02 - OneToMany 양방향 매핑]");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		try {
			Member_DoubleWay member = new Member_DoubleWay();
			member.setUsername("[oneToMany 양방향] 1번 회원_xxx");
			em.persist(member);
			
			Team_DoubleWay team = new Team_DoubleWay();
			team.setName("[oneToMany 양방향] A팀");
			team.addMembers(member);
			em.persist(team);
			
			// flush 시점에 SQL : insert MEMBER -> insert Team -> update Member(외래키 변경)
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
}
