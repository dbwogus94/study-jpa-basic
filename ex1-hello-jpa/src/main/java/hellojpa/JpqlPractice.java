package hellojpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
/**
 * <PER>
 *	@Title <B> JPA Practice - JPQL Test  </B> 
 * </PER>
 * @author jae hyun
 */
public class JpqlPractice {
	// EntityManagerFactory는 하나의 인스턴스만 존재하면 된다.
	private EntityManagerFactory emf;
	
	public JpqlPractice(EntityManagerFactory emf) {
		this.emf = emf;
	}
	/**
	 * 	JPQL를 사용한 테이블 전체 Select All
	 */
	public List<Member> findAll() {
		List<Member> result = null;
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			String jpql = "select m from Member as m";	// m = Member, 객체로 취급한다.
			result = em.createQuery(jpql, Member.class).getResultList(); // jpql을 사용하여 쿼리 생성
			tx.commit(); 	// 조회여서 사실상 필요x
		} catch(Exception e) {
			tx.rollback();
		} finally {
			em.close();	
		}
		return result;
	}
	
	/**
	 * JPQL를 사용한 조건절 Select All
	 * @return 
	 */
	public List<Member> findAll_where(){
		List<Member> result = null;
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			String jpql = "select m from Member as m where id > 2";
			result = em.createQuery(jpql, Member.class).getResultList();	// jpql을 사용하여 쿼리 생성
			tx.commit();
		} catch(Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		return result;
	}
	
	/**
	 * JPQL를 사용한 조건절 페이징
	 * - paging을 하는 방법은 DB마다 차이가 있다.
	 * - 대표적으로 Oracle은 rownum을 사용하고, MySql은 limit를 사용한다.
	 * - 그래서 JPA는 persistence.xml에서 지정한 DB의 방언에 맞는 SQL을 생성한다.
	 */
	public List<Member> findAll_paging(){
		List<Member> result = null;
		
		EntityManager em = emf.createEntityManager();
		// 실제 작업 단위(트랜잭션)
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			// 코드는 같지만 JPA설정 파일에서 지정한 DB방언에 맞는 SQL을 생성한다.
			String jpql = "select m from Member as m";
			result = em.createQuery(jpql, Member.class)
					.setFirstResult(1)		// 페이징 시작
					.setMaxResults(3)		// 시작부터 몇개를 가져올지 지정
					.getResultList();
			tx.commit();
		} catch(Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		return result;
	}
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		JpqlPractice jpql = new JpqlPractice(emf);
		System.out.println(jpql.findAll());
		System.out.println(jpql.findAll_where());
		System.out.println(jpql.findAll_paging());
	}
}
