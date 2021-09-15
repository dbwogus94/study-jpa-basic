package ch5_relation_mapping_various;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ch5_relation_mapping_various.domain.manytoone.oneway.Member_OneWay;
import ch5_relation_mapping_various.domain.manytoone.oneway.Team_OneWay;
import ch5_relation_mapping_various.domain.manytoone.doubleway.Member_DoubleWay;
import ch5_relation_mapping_various.domain.manytoone.doubleway.Team_DoubleWay;


/**
 * 다대일(N:1) 관계 매핑 예시
 * <pre>
 * - 예시1) 단방향 매핑
 * - 예시2) 양방향 매핑
 * </pre>
 */
public class ManyToOne {
	public static void main(String[] args) {
		ManyToOne t = new ManyToOne();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		t.testCase01(emf);
		t.testCase02(emf);
		emf.close();
	}
	/**
	 *	단방향 매핑 - 회원과 팀 생성, 연관관계 생성 
	 */
	void testCase01(EntityManagerFactory emf) {
		System.out.println("\n\n[테스트 01 - 단방향 매핑]");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		try {
			Team_OneWay team = new Team_OneWay();
			team.setName("A팀");
			em.persist(team);
			
			Member_OneWay member = new Member_OneWay();
			member.setUsername("1번 회원_xxx");
			member.setTeam(team);
			em.persist(member);
			
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
		System.out.println("\n\n[테스트 02 - 양방향 매핑]");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		try {
			Member_DoubleWay member = new Member_DoubleWay();
			member.setUsername("1번 회원_xxx");
			
			Team_DoubleWay team = new Team_DoubleWay();
			team.setName("A팀");

			// 연관관계 편의 매서드 : 양방향, 역방향 동시 매핑
			team.changeTeam(member);
			
			/* JPA는 persist()로직의 순서도 중요하다. 
			 	Q) member을 먼저 영속화하고 team이 영속화가 하면? 
			 	A) 쿼리가 3번 나간다.  
			 		=> SQL은 INSERT MEMBER -> INSERT TEAM -> UPDATE MEMBER
			 	Q) team을 먼저 영속화 하고 member이 영속화가 하면? 
			 	A) 쿼리가 2번 나간다.
			 		=> SQL은 INSERT TEAM -> INSERT MEMBER
			 */
			em.persist(team);
			em.persist(member);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
}
