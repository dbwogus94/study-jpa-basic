package ch4_Examlpes_code02;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


import ch4_Examlpes_code02.domin.Item;
import ch4_Examlpes_code02.domin.Member;
import ch4_Examlpes_code02.domin.Order;
import ch4_Examlpes_code02.domin.OrderItem;
import ch4_Examlpes_code02.domin.OrderStatus;

public class JpaMain {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaShop");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		try {
			// 신규 회원 정의
			Member member = new Member();
			member.setName("회원_1");
			member.setCity("서울");
			member.setStreet("아무개길");
			member.setZipCode("123456");
			em.persist(member);
			
			// item 추가
			Item item_01 = new Item();
			item_01.setName("상품1");
			item_01.setPrice(3000);
			item_01.setStockQuantity(10);

			Item item_02 = new Item();
			item_02.setName("상품2");
			item_02.setPrice(1000);
			item_02.setStockQuantity(20);
			em.persist(item_01);
			em.persist(item_02);
			
			
			//1 번 회원 주문 하기 + 상품 주문 추가
			Member findMember = em.find(Member.class, 1L);
			Order order = new Order();
			order.changeMember(findMember);				// 회원을 주문에 매핑(양방향 매핑)
			order.setOrderDate(LocalDateTime.now());
			order.setStatus(OrderStatus.ORDER);
			
			for(Long i = 1L; i<=2L; i++) {
				OrderItem orderItem = new OrderItem();
				Item findItem = em.find(Item.class, i);
				orderItem.setItem(findItem);
				orderItem.setOrderPrice(findItem.getPrice());
				orderItem.setCount(Math.toIntExact(i));
				// 주문수량 재고에서 차감
				findItem.setStockQuantity(findItem.getStockQuantity() - Math.toIntExact(i));
				// 주문에 주문상품 매핑(양방향 매핑)
				order.addOrderItem(orderItem);
				// 영속상태로 변경  
				em.persist(orderItem);
			}
			em.persist(order);
			
			// commit()전 양방향 조회
			System.out.println(" =============================================== ");
			/* commit()전 이라 조회가 안될것 같지만 연관관계 편의 매서드에서 역방향 매핑을 했기 때문에 조회가 가능하다. */ 
			System.out.println("주문 : " + findMember.getOrders() + "\n");
			
			System.out.println("주문 리스트 : ");
			for(OrderItem orderItem : findMember.getOrders().get(1).getOrderItems()) {
				System.out.println(orderItem);
			}
			System.out.println(" =============================================== ");
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
	}
}