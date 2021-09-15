package ch4_relation_mapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ch4_relation_mapping.domain.double_mapping.Member;
import ch4_relation_mapping.domain.double_mapping.Team;


/**
 * 양방향 연관관계에서 가장 많이 하는 실수 유형
 * <pre> 
 * 1. 양방향 매핑시 연관관계의 주인에 값을 입력하지 않는다.
 * 	-> 순수한 객체 관계를 고려하면 항상 양쪽다 값을 입력해야 한다.
 * </pre>
 *
 */
public class JpaErrorCase {
	public static void main(String[] args) {
		JpaErrorCase t = new JpaErrorCase();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		t.case02(emf);
		
		emf.close();
	}
	
	/**
	 * 에러 유형 1.양방향 매핑시 연관관계의 주인에 값을 입력하지 않는다.
	 * <pre>
	 * - 순수한 객체 관계를 고려하면 항상 양쪽다 값을 입력해야 한다.
	 * </pre>
	 */
	void case01(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			Team team = new Team();
			team.setName("TeamA");
			em.persist(team);
			
			Member member = new Member();
			member.setName("member1");
			
//			member.setTeam(team);			// 연관관계의 주인에 값을 세팅해야 JPA가 FK를 연결하는 SQL을 만들어 요청한다.
			team.getMembers().add(member);	// 가짜 매핑으로 이 코드는 JPA 영향을 주지 못한다.
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
	 * 에러 유형 2.jpa만을 생각해서 순수한 객체 관계를 고려하지 않은 코드를 작성할 때.
	 * <pre>
	 * -  
	 * </pre>
	 */
	void case02(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			Team team = new Team();
			team.setName("TeamA");
			em.persist(team);
			
			Member member = new Member();
			member.setName("member1");
			
			member.changeTeam(team);			// 연관관계의 주인
			em.persist(member);
			
			System.out.println(member);
			
			// team을 다시 조회하여 1차 캐시에 담는다.
//			Team findTeam = em.find(Team.class, 1L);
			// 지연로딩 : 이 시점에 team에 속한 member을 조회하는 쿼리 요청
//			System.out.println(findTeam.getMembers());
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
}
