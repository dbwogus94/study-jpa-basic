package ch4_relation_mapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ch4_relation_mapping.domain.test.Book;
import ch4_relation_mapping.domain.test.Person;

public class TestCode {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//		test01(emf);
		test02(emf);
		emf.close();
	}
	// 양방향 연관관계 : 잘못된 코드
	static void test01(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			Person person = new Person();
			person.setName( "John Doe" );

			Book book = new Book();
			person.getBooks().add( book );

			System.out.println(book.getAuthor().getName());
			
			em.persist(person);
			em.persist(book);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	// 양방향 연관관계 : 잘된 코드
	static void test02(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Person person = new Person();
			person.setName( "John Doe" );

			Book book = new Book();
			person.getBooks().add( book );
			
			book.setAuthor( person ); // 연관관계를 가진 주인에게 추가

			System.out.println(book.getAuthor().getName());
			
			em.persist(person);
			em.persist(book);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
}

