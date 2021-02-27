package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * <PER>
 *	@Title <B> JPA Practice01 - JPA Member CRUD  </B> 
 * </PER>
 * @author jae hyun
 */
public class JpaPractice {
	// EntityManagerFactory는 하나의 인스턴스만 존재하면 된다.		=> 	MyBatis의 Sqlsessionfactory와 비슷하다.
	private EntityManagerFactory emf;
	
	public JpaPractice(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	/**
	 * JPA를 사용한 Member Insert
	 */
	public void insertMember(Long memberId, String memberName) {
		// 작업단위 마다 EntityManager를 받아야함.		=> MyBatis에서 매번 sqlSession을 받는 것과 비슷
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		// 트랜잭션의 시작을 알림
		tx.begin();
		try {
			Member member = new Member();
			member.setId(memberId);
			member.setName(memberName);
			// insert SQL 생성
			em.persist(member);
			// commit 시점에 SQL 실행
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	/**
	 * JPA를 이용한 Member Select one : 단순 조회용
	 */
	public Member findMember(Long memberId) {
		EntityManager em = emf.createEntityManager();
		Member result = em.find(Member.class, memberId);
		em.close();
		return result;
	}
	/**
	 * JPA를 이용한 Member Select one : 트랜잭션 단위에서 사용되는 조회
	 * - 같은 트랜잭션 단위에서는 같은 EntityManager를 사용해야 동일한 Entity를 바라보고 작업(crud)울 할 수 있다.
	 */
	public Member findMember(EntityManager em, Long memberId) {
		return em.find(Member.class, memberId);
	}
	
	/**
	 * JPA를 이용한 Member Update
	 */
	public void updateMember(Long memberId, String memberName) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Member member = findMember(em, memberId);
			member.setName(memberName);
			// commit 시점에 update SQL 실행  
			// update소스가 따로 없는 이유는? commit 시점에 JPA가 조회한 Entity를 분석해서 변경사항이 있으면 Update SQL을 실행한다.
			tx.commit();
		} catch (Exception e) {
			System.out.println("Update : 알 수 없는 오류");
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	/**
	 * JPA를 이용한 Member Delete
	 */
	public void deleteMember(Long memberId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Member member = findMember(em, memberId);
			// 조회한 Entity를 삭제하는 SQL 생성
			em.remove(member);
			// delete SQL 실행
			tx.commit();
		} catch(Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	public static void main(String[] args) {
		// EntityManagerFactory : Persistence class가 persistence.xml의 unit정보를 읽어서 생성한다.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		
		JpaPractice jpa = new JpaPractice(emf);
		
//		jpa.insertMember(2L, "member02-Insert");	// Insert
//		System.out.println(jpa.findMember(2L));		// Select
		jpa.updateMember(2L, "member02-Update");	// Update
//		jpa.deleteMember(2L);						// delete
		
//		jpa.updateMember(1L, "HelloJAP");
		
//		for(Long i = 5L; i < 10; i++) {
//			jpa.insertMember(i, "JPA_test0" + i);
//		}
		
		emf.close();	// 어플리케이션 종료시 EntityManagerFactory를 닫아야한다.
	}
}
