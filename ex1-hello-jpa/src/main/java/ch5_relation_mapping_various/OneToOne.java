package ch5_relation_mapping_various;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ch5_relation_mapping_various.domain.onetoone.doubleway.Locker_DoubleWay;
import ch5_relation_mapping_various.domain.onetoone.doubleway.Member_DoubleWay;
import ch5_relation_mapping_various.domain.onetoone.oneway.Locker_OneWay;
import ch5_relation_mapping_various.domain.onetoone.oneway.Member_OneWay;

/**
 * 일대일(1:1) 연관관계 매핑 예시
 * <pre>
 *  - 예시1) 일대일 단방향     -> 다대일 단방향과 유사하다. 
 *  - 예시2) 일대일 양방향     -> 다대일 양방향과 유사하다.
 * </pre>
 *
 */
public class OneToOne {
	public static void main(String[] args) {
		OneToOne t = new OneToOne();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//		t.testCase01(emf);
		t.testCase02(emf);
		
		emf.close();
	}
	/**
	 * 단방향 매핑
	 */
	void testCase01(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Locker_OneWay locker = new Locker_OneWay();
			locker.setName("[단방향]1번 사물함");
			em.persist(locker);
			
			Member_OneWay member = new Member_OneWay();
			member.setUsername("[단방향]1_회원");
			member.setLocker(locker);
			
			em.persist(member);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
	/**
	 * 양방향 매핑 
	 */
	void testCase02(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Locker_DoubleWay locker = new Locker_DoubleWay();
			locker.setName("[양방향]1번 사물함");
			em.persist(locker);
			
			Member_DoubleWay member = new Member_DoubleWay();
			member.setUsername("[양방향]1_회원");
			member.changeLocker(locker);  // 연관관계 주인, 역방향 매핑
			em.persist(member);
			
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
}