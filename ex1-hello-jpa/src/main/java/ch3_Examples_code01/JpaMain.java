package ch3_Examples_code01;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ch3_Examples_code01.domain.Member;
import ch3_Examples_code01.domain.Order;

/**
 * 테이블 중심으로 객체를 모델링 예시 코드) 
 */
public class JpaMain {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaShop");
		EntityManager em = emf.createEntityManager();
		
		// DB 중심적 코드 : 테이블 처럼 객체도 id를 통하여 연관된 객체를 조회한다.
		// 1) 주문번호가 1번인 주문을 조회한다.
		Order order = em.find(Order.class, 1L);
		// 2) 조회한 주문에서 주문한 회원의 id를 가져온다.
		Long memberId = order.getMemberId();
		// 3) 주문한 회원의 id를 사용하여 DB에 select 요청을 한다.
		Member member = em.find(Member.class, memberId);  
		
		/* 객체중심 코드라면? : 연관된 객체간 자유로운 이동이 가능하다.
		 * 1) 주문번호가 1번인 주문을 조회한다.
		 * Order order = em.find(Order.class, 1L);
		 * 2) 조회한 주문에서 주문한 회원을 가져온다.
		 * Member member = order.getMember();  <- 객체간 자유로운 이동
		 */ 
		em.clear();
		emf.close();
	}
}
