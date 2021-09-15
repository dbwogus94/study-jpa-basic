package ch4_relation_mapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ch4_relation_mapping.domain.double_mapping.Member;
import ch4_relation_mapping.domain.double_mapping.Team;

/**
 * @Title 양방향 연관관계에서 연관관계 설정 - 성공 vs 실패(FK 추가)
 * <pre>
 * - 연관관계의 주인만이 연관관계를 설정할 수 있다.
 * 1. test_success() : 연관관계의 주인을 사용하여 연관관계(fk)를 설정할 수 있다.
 * 2. test_fail : 연관관계의 주인인이 아닌 객체는 연관관계를 설정 할 수 없다.
 * </pre>
 */
public class jpaMain02 {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		test_success(emf);
		test_fail(emf);
		emf.close();
	}
	/**
	 * 연관관계 주인을 사용한 연관관계 설정 : 성공
	 */ 
	static void test_success(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			Team team = new Team();
			team.setName("1팀");
			em.persist(team);
			
			Member member = new Member();
			member.setName("1번 회원");
			em.persist(member);

			//연관관계 주인을 사용하여 설정 : 연관관계의 주인을 가진 객체에서만 연관관계를 설정할 수 있다.
			member.setTeam(team); 
			
			System.out.println("Member : " + member);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	/**
	 *  연관관계의 역방향에서 연관관계 설정 : 실패
	 */
	static void test_fail(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Member member = new Member();
			member.setName("2번 회원");
			em.persist(member);
			
			Team team = new Team();
			team.setName("2팀");
			em.persist(team);
			
			// 역방향에서 연관관계 설정 : 연관관계의 주인이 아닌 객체는 연관관계를 설정 할 수없다.
			team.getMembers().add(member);
			
			System.out.println("Member : " + member);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
}