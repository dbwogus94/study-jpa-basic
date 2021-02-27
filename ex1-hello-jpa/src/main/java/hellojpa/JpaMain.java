package hellojpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
	public static void main(String[] args) {
		/*
		* EntityManagerFactory 
		* - Persistence class가 persistence.xml를 읽어서 생성 
		* - persistence-unit태그의 이름(name)이 "hello"인 property를 읽어서 EntitiyManagerFactory를 생성
		* - 에플리케이션 로딩 시점에 하나만 만들어야 한다. (이것만으로 DB와 연결)
		*/ 
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		
		/*
		* EntityManager
		* - 실제 작업 단위(트랜잭션)마다 생성하여 사용한다.
		* - 트랜잭션 단위( DB connection을 얻고 SQL요청하고 commit을 하는 단위)
		* - 가장 쉽게 설명하면 connection을 얻은 상태 - sqlSession을 얻은 상태라고 할 수 있을까?  
		*/
		EntityManager em = emf.createEntityManager();
		// 실제 작업 단위(트랜잭션)
		
		// jpa는 모든 작업단위마다 트랙잭션 시작과 끝을 정의해야함		-> 이 코드가 없어도 에러는 나지 않음 단, DB상 어떠한 변화도 일어나지 않는다.
		EntityTransaction tx = em.getTransaction();		// EntityManager에서 Transaction가져온다.
		tx.begin();										// 트랜재션 시작을 지정
		
		try {
			Member member = new Member();
			// JPA는 PK가 중요하다. 만약  정의하지 않으면 예외발생("ids for this class must be manually assigned")
			member.setId(1L);			
			member.setName("HelloA");
			// INSERT 쿼리 생성 : JPA 핵심  코드, 실제 SQL 실행은 commit 시점에 발생한다.
			em.persist(member);		 
			// commit(), rollback() : 트랜잭션 종료
			tx.commit();	
		} catch(Exception e) {
			tx.rollback();
		} finally {
			em.close();		// 모든 작업 단위마다 생성되는 EntityManager를 닫아야 한다.
		}
		
		/*
		*  JPSQL을 사용한 Select All - 객체지향적인 SQL을 사용하여 테이블 전체 조회
		*/ 
		em = emf.createEntityManager();
		String jpql = "select m from Member as m";	// 테이블을 객체로 취급하여 SQL을 작성한다. m == Member class
		
		// 쿼리 요청
		List<Member> result = em.createQuery(jpql, Member.class).getResultList();
		
		for(Member m : result) {
			System.out.println(m.getName());
		}
		
		emf.close(); 	// 에플리케이션 종료시 EntityManagerFactory를 닫아야 한다.
	}
}
