package ch2_persistence_context;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ch2_persistence_context.domain.Member;



/**
 * @Title 2강 1) 자바 ORM 표준 JPA 프로그래밍 기본 - 영속성 컨텍스트
 * <PRE>
 * 	- 영속성 컨텍스트의 생명주기 확인 : 비영속-영속-준영속-삭제
 * </PRE>
 */
public class JpaMain {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			//비영속 상태 : 객체를 생성한 상태
			Member member = new Member();
			member.setId(100L);
			member.setName("회원1");
			
			/* 영속 상태 : 객체를 영속 컨텍스트에 저장한 상태 */ 
//			em.persist(member);
			
			/* 준영속 상태 : member Entity를 영속성 컨텍스트에서 분리 - 영속 상태를 분리하면 DB에 저장X */
//			em.detach(member);
			
			/* 삭제 상태 : 영속상태인 Entity를 영속성 컨텍스트에서 삭제하고 DB에 삭제를 요청한 상태(삭제) */
//			em.remove(member);
			
			/** 
			 *  Q) DB에 없는 데이터를 select하고 Delete할 경우는?
			 *  A) 먼저 콘솔을 확인하면 DB에 데이터가 없어도 에러가 발생하지 않음 - Select 쿼리는 로그O, Delete 쿼리는 로그X
			 *  	1. find() : 데이터 조회 -> 영속성 컨텍스트의 1차 캐시에 캐싱
			 *  	2. remove() : 영속성 컨텍스트에 있는 데이터라면 delete SQL 쓰기지연 SQL 저장소에 저장, 없는 데이터라면 x
			 *  	2. commit()시점에 요청 : 쓰기지연 저장소 SQL에 SQL없음 아무 동작x
			 */
//			em.remove(em.find(Member.class, 100L));	
	
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();
	}
}
