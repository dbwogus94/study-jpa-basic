package ch3_entitymapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ch3_entitymapping.domain.Member_Identiiy;
import ch3_entitymapping.domain.Member_Sequence;
import ch3_entitymapping.domain.Member_Sequence_DefSeq;
import ch3_entitymapping.domain.Member_Table;


public class JpaMain {
	private EntityManagerFactory emf;
	
	JpaMain(EntityManagerFactory emf){
		this.emf = emf;
	}
	EntityManager createEntityManger() {
		return emf.createEntityManager(); 
	}
	void close() {
		emf.close();
	}
	
	public static void main(String[] args) {
		JpaMain t = new JpaMain(Persistence.createEntityManagerFactory("hello"));
		t.testCode_IDENTITY();
		t.testCode_SEQUNCE();
		t.testCode_Def_SEQUNCE();
		t.testCode_TABLE();
		
//		t.testCode_SEQUNCE_multiInsert(10);
		
		t.close();
	}
	/** 
	 * IDENTITY 전략을 사용한 Entity
	 * <pre>
	 * - 이 전략은 저장시점에 DB에서 pk값을 만든다 그렇기 때문에 저장이 완료가 되어야 pk값을 알 수 있다. 
	 * - 이러한 이유로 다른 전략과 다르게 persist시점에 insert SQL을 요청하고 저장된 id값을 리턴받는다.
	 * - 이 덕분에 코드에서 바로 pk값을 알 수 있다.
	 * - 단점으로는 persist시점에 insert를 요청하기 때문에 쓰기지연 SQL 저장소를 사용하지 못한다.  
	 * </pre> 
	 */
	void testCode_IDENTITY() {
		System.out.println("\n\n\n\t\t[ IDENTITY 전략을 사용한 Entity ]\n");
		EntityManager em = createEntityManger();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Member_Identiiy member = new Member_Identiiy("IDENTITY 전략");  	// id는 전략에 따라 DB또는 jpa가 생성한다.
			
			System.out.println("--------------------- [persist() 이전] ---------------------");
			em.persist(member);												// IDENTITY 전략을 사용하면 이 시점에 insert SQL을 요청하고 저장된 데이터를 리턴받는다. 그리고 리턴받은 데이터를 Entity에 매핑한다.
			// insert SQL flush
			System.out.println("--------------------- [persist() 이후] ---------------------");
			
			Long id = member.getId();										// Q) pk값을 알수 있을까? - IDENTITY전략은 DB에 저장된 시점에 pk를 알 수 있는데 이시점에 알 수 있을까?   
			System.out.println("\n[persist 이후] id 확인 : " + id + "\n");		// A) persist 시점에 insert가 실행되고 저장된 결과를 Entity에 매핑했기 때문에 id값을 알 수 있다.  
			
			// 저장된 ID를 사용하여 회원 name 변경
			member.setUsername(id + "_회원");
			
			System.out.println("===================== [commit() 이전] =====================");
			tx.commit();													// 이 전략에서는 persist()시점에 insert SQL을 요청하기 때문에 commit() 시점에서는 SQL을 날리지 않는다.
			// update 쿼리 flush : 더티체킹										// 즉, 영속성 컨텍스트의 버퍼 쓰기 기능을 사용할 수 없다.
			System.out.println("===================== [commit() 이후] =====================");
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	/** 
	 * SEQUNCE 전략을 사용한 Entity 
	 * <pre>
	 * 	- pk값을 JPA가 요청하여 매핑해준다. 
	 *	- perist()시점에 시퀀스 값을 올리는 쿼리가 요청되기 때문에 저장 실패시 rollback를 해줘야 안전하다.
	 * </pre>
	 */
	void testCode_SEQUNCE() {
		System.out.println("\n\n\n\t\t[ SEQUNCE 전략을 사용한 Entity ]\n");
		EntityManager em = createEntityManger();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Member_Sequence member = new Member_Sequence("SEQUNCE 전략");
			System.out.println("--------------------- [persist() 이전] ---------------------");
			em.persist(member);											     			// SEQUNCE 전략에서는 persist 시점에 시퀀스 값을 올리고 그 값을 리턴받는 SQL을 요청한다.
			System.out.println("--------------------- [persist() 이후] ---------------------");
			
			Long id = member.getId();										 			// Q) pk값을 알 수 있을까?	
			System.out.println("\n[persist 이후] id 확인(조회한 시퀀스의 값) : " + id + "\n");	// A) persist 시점에 리턴 받은 시퀀스 값을 Entity에 매핑했기 때문에 알 수 있다.
			
			// 저장된 ID를 사용하여 회원 name 변경
			member.setUsername(id+"_회원");
				
			System.out.println("===================== [commit() 이전] =====================");
			tx.commit();																// commit() 시점에 쓰기지연 SQL 저장소에서 플러시 발생 -> insert SQL 요청
			/* flush */ 
			// insert 쿼리 : em.persist()으로 insert SQL생성
			// update 쿼리 : 영속상태 엔티티 name변경 -> 더티체킹으로 update SQL 생성
			System.out.println("===================== [commit() 이후] =====================");
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	/** 
	 * 시퀀스를 정의하여 SEQUNCE 전략을 사용한 Entity
	 * <pre>
	 * 	- pk값을 JPA가 요청하여 매핑해준다. 
	 *	- perist()시점에 시퀀스 값을 올리는 쿼리가 요청되기 때문에 저장 실패시 rollback를 해줘야 안전하다.
	 * </pre>
	 */
	void testCode_Def_SEQUNCE() {
		System.out.println("\n\n\n\t[ 시퀀스를 정의하여 SEQUNCE 전략을 사용한 Entity ]\n");
		EntityManager em = createEntityManger();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			Member_Sequence_DefSeq member = new Member_Sequence_DefSeq("Def_SEQUNCE 전략");
			System.out.println("--------------------- [persist() 이전] ---------------------");
			em.persist(member);															// SEQUNCE 전략에서는 persist 시점에 시퀀스 값을 올리고 그 값을 리턴받는 SQL을 요청한다.
			System.out.println("--------------------- [persist() 이후] ---------------------");
			
			Long id = member.getId();													// Q) pk값을 알 수 있을까?
			System.out.println("\n[persist 이후] id 확인(조회한 시퀀스의 값) : " + id + "\n");	// A) persist 시점에 리턴 받은 시퀀스 값을 Entity에 매핑했기 때문에 알 수 있다.
			
			// 저장된 ID를 사용하여 회원 name 변경
			member.setUsername(id+"_회원");
			
			System.out.println("===================== [commit() 이전] =====================");
			tx.commit();																// commit() 시점에 쓰기지연 SQL 저장소에서 플러시 발생 -> insert SQL 요청
			/* flush */ 
			// insert 쿼리 : em.persist()으로 insert SQL생성
			// update 쿼리 : 영속상태 엔티티 name변경 -> 더티체킹으로 update SQL 생성
			System.out.println("===================== [commit() 이후] =====================");
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	/**
	 * TABLE 전략을 사용한 Entity 
	 * <pre>
	 * - TABLE 전략은 테이블로 시퀀스를 사용하는 효과를 만드는 것이기 때문에 SEQUNCE 전략과 같은 동작을 보인다.
	 * </pre>
	 */
	void testCode_TABLE() {
		System.out.println("\n\n\n\t\t[ TABLE 전략을 사용한 Entity ]\n");
		EntityManager em = createEntityManger();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Member_Table member = new Member_Table("TABLE 전략");
			System.out.println("--------------------- [persist() 이전] ---------------------");
			em.persist(member);															// TABLE 전략에서는  persist 시점에 시퀀스 테이블의 값을 update(올린다)하고 그 결과를 select한다. 
																						// 조회한 값을 Entity에 매핑한다.
			System.out.println("--------------------- [persist() 이후] ---------------------");
			
			Long id = member.getId();													// Q) pk값을 알 수 있을까?
			System.out.println("\n[persist 이후] id 확인(조회한 시퀀스의 값) : " + id + "\n");	// A) persist 시점에 리턴 받은 시퀀스 테이블 값을 Entity에 매핑했기 때문에 알 수 있다.
			
			// 저장된 ID를 사용하여 회원 name 변경
			member.setUsername(id+"_회원");
			
			System.out.println("===================== [commit() 이전]  =====================");
			tx.commit();																// commit() 시점에 쓰기지연 SQL 저장소에서 플러시 발생 -> insert SQL 요청
			/* flush SQL */ 
			// insert SQL : em.persist()로 인해 생성 
			// update SQL : persist()이후 회원의 이름을 변경 -> em.persist()에서 저장한 스냅샷과 데이터가 달라졌기 떄문에 생성 == 더티체킹  
			System.out.println("===================== [commit() 이후]  =====================");
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	/**
	 * SEQUNCE 전략에서 allocationSize 옵션을 사용한 연속 저장
	 * <pre>
	 * - Member_Sequence_DefSeq 클래스는 SEQUNCE 전략을 사용한다.
	 *   (strategy = GenerationType.SEQUENCE)
	 * - 성능 최적화를 위해 allocationSize의 값을 10으로 설정하였다.
	 *   (allocationSize = 1)
	 * </pre>
	 * @param 테스트로 저장할 Entity 개수
	 */
	void testCode_SEQUNCE_multiInsert(int len) {
		System.out.println("\n\n\n\t[ SEQUNCE 전략에서 allocationSize옵션을 사용한 버퍼 쓰기 ]\n");
		EntityManager em = createEntityManger();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			for(int i = 1; i<=len; i++) {
				Member_Sequence_DefSeq member = new Member_Sequence_DefSeq();
				System.out.println("--------------------- [persist() 이전] ---------------------");
				em.persist(member);
				/* 이 시점에 내부로직
				 * 
				 * # i=1 일 때
				 * 1. 시퀀스 증가 SQL 요청 : DB에 SQL을 요청하여 시퀀스 값을 -11에서 10이 증가하여 시작값인 1로 맞춘다.
				 * 2. 시퀀스 증가 SQL 요청 : DB에 다시한번 SQL을 요청하여 시퀀스 값을 증가 시킨다. 1 -> 11
				 * 3. 메모리에 시퀀스 캐싱 : 메모리에 1 ~ 11까지 10개를 캐싱한다.
				 * 4. Entity에 pk 매핑 : member.setId(캐싱한 값 1을 매핑);
				 * 
				 * # i=2 일 때
				 * 1. Entity에 pk 매핑 : member.setId(캐싱한 값 2를 매핑);
				 * 
				 * # i=3 일 때
				 * 1. Entity에 pk 매핑 : member.setId(캐싱한 값 3를 매핑);
				 * 
				 * # i=4 일 때
				 * 1. Entity에 pk 매핑 : member.setId(캐싱한 값 4를 매핑);
				 * 
				 * ...
				 * 
				 * # i = 12일 때 
				 * 2. 시퀀스 증가 SQL 요청 : DB에 SQL을 요청하여 시퀀스 값을 증가 시킨다. 11 -> 21
				 * 3. 메모리에 시퀀스 캐싱 : 메모리에 12 ~ 21까지 10개를 캐싱한다.
				 * 4. Entity에 pk 매핑 : member.setId(캐싱한 값 12를 매핑);
				 * 
				 */
				member.setUsername("Def_SEQUNCE : 연속 저장_" + member.getId());	 // em.persist시점에 Entity에 id가 매핑되기 때문에 이 시점에 변경이 가능하다. 
//				System.out.println("[id 확인] " + member.getId());
				System.out.println("--------------------- [persist() 이후] ---------------------\n\n");
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
}
