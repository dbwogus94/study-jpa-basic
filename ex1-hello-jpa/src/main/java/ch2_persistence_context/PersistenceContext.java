package ch2_persistence_context;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import ch2_persistence_context.domain.Member;


/**
 * @Title 2강 2) 영속성 컨텍스트의 특징 확인 : JPA Persistence Context Log Test
 * <PRE>
 *  TEST-1 : 1차 캐시에 캐싱된 Entity를 조회시 캐싱된 데이터를 가져오는지 확인
 *  TEST-2 : 영속성 엔티티의 동일성 보장 확인
 *  TEST-3 : 엔티티 등록 : 트랜잭션을 지원하는 쓰기 지연
 *  TEST-4 : 엔티티 수정 : 변경 감지(Dirty Checking)
 *  TEST-5 : [insert -> select -> update -> select] 코드의 로그를 확인하여 영속성 컨텍스트 동작과정 확인
 *  TEST-6 : [select -> update -> select] 코드의 로그를 확인하여 영속성 컨텍스트 동작과정 확인
 * </PRE>
 */
public class PersistenceContext {
	// EntityManagerFactory는 하나의 인스턴스만 존재하면 된다.
	private EntityManagerFactory emf;
	private final String MAX_JPQL = "SELECT MAX(m.id) FROM Member m";
	
	public PersistenceContext(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		PersistenceContext t = new PersistenceContext(emf);
		
		t.test01();		// 1차 캐시 초회
		t.test02();		// 동일성 보장
		t.test03();		// 트랜잭션을 지원하는 쓰기 지연
		t.test04();		// 변경감지
		t.test05();		// 추가 테스트
		t.test06();		// 추가 테스트
		System.out.println(t.selectAll().size());
		
		emf.close();
	}
	
	
	/**
	 * TEST-1 : 1차 캐시에 캐싱된 Entity를 조회시 캐싱된 데이터를 가져오는지 확인
	 */
	public void test01() {
		System.out.println("\n\nTEST - 1 : =================== 1차 캐시에 캐싱된 Entity를 조회시 캐싱된 데이터를 가져오는지 확인 ===================");
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		// max id 
		Long maxId = findMaxId();
		maxId = (maxId == null)? 1L: maxId + 1L;
		
		tx.begin();
		
		try {
			/* 비영속 */ 
			Member member = new Member(maxId, maxId + "번 회원_TEST-1");
			
			/* 영속 : 1차 캐시에 캐싱 -> INSERT SQL 생성 -> 쓰기 지연   SQL 저장소에 SQL 저장 */
			System.out.println("===================> 1. INSERT : 신규 Member 영속상태로 변환 ");
			em.persist(member);
			System.out.println(member);
			
			System.out.println("===================> 2. SELECT : 캐싱된 Member 조회 요청 -> 캐싱된 Entity를 가져온다  => *조회 SQL이 로그에 찍히지 않는다.");
			/* find()로 조회 요청시에 1차 캐시에 요청한 PK(id)와 동일한 id가 있으면 캐싱된 Entity를 가져온다. -> 조회 SQL이 로그에 나오지 않는다. */
			System.out.println(em.find(Member.class, maxId));
			
			/* commit() 호출시 내부에서 flush()호출 쓰기 지연 SQL 저장소의 SQL을 모두 DB에 보낸다. */
			System.out.println("===================> 3. commit : INSERT SQL DB에 요청");
			tx.commit();
		} catch(Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	/**
	 * TEST-2 : 영속성 엔티티의 동일성 보장 확인
	 */
	public void test02() {
		System.out.println("\n\nTEST - 2 : =================== 영속성 엔티티의 동일성 보장 확인 ===================");
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		// max id 
		Long maxId = findMaxId();
		maxId = (maxId == null)? 1L: maxId;
		
		System.out.println("===================> 1. SELECT : 캐싱되지 않은 Entity 조회  -> SELECT SQL 생성 -> SQL 요청 ");
		/* find() 캐싱되어 있는 Entity인지 확인 
		   -> 없는 Entity라면? 조회 SQL 생성 및 즉시 조회 -> 조회된 Entity 영속성 컨텍스트의 1차 캐시에 캐싱  */
		Member m1 = em.find(Member.class, maxId);
		System.out.println(m1);
		
		System.out.println("===================> 2. SELECT : 캐싱된 Member 조회 요청 -> 캐싱된 Entity를 가져온다. => *조회 SQL이 로그에 찍히지 않는다. ");
		/* find()로 조회 요청시에 1차 캐시에 요청한 PK(id)와 동일한 id가 있으면 캐싱된 Entity를 가져온다. -> 조회 SQL이 로그에 나오지 않는다. */
		Member m2 = em.find(Member.class, maxId);
		System.out.println(m2);
		
		System.out.println("===================> 3. m1과 m2 객체가 같은 객체인지 검사 => 영속성 엔티티의 동일성");
		System.out.println(m1 == m2);
		
		em.close();
	}
	/**
	 * TEST-3 : 엔티티 등록 : 트랜잭션을 지원하는 쓰기 지연
	 */
	public void test03() {
		System.out.println("\n\nTEST - 3 : =================== 엔티티 등록 : 트랜잭션을 지원하는 쓰기 지연 ===================");
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		// max id 
		Long maxId = findMaxId();
		maxId = (maxId == null)? 1L: maxId + 1L;
		
		tx.begin();
		
		try {
			/* 비영속 */ 
			Member m1 = new Member(maxId, maxId + "번 회원_TEST-3");
			Member m2 = new Member(maxId + 1L, maxId + 1L + "번 회원_TEST-3");
			
			/* 영속 : 1차 캐시에 캐싱 -> INSERT SQL 생성 -> 쓰기 지연   SQL 저장소에 SQL 저장 */
			System.out.println("===================> 1. INSERT : 신규 " + maxId + "번 Member 영속상태로 변환 ");
			em.persist(m1);
			System.out.println(m1);
			
			/* 영속 : 1차 캐시에 캐싱 -> INSERT SQL 생성 -> 쓰기 지연   SQL 저장소에 SQL 저장 */
			System.out.println("===================> 2. INSERT : 신규 " + (maxId + 1L) + "번 Member 영속상태로 변환 ");
			em.persist(m2);
			System.out.println(m2);
			
			/* commit() 호출시 내부에서 flush()호출 쓰기 지연 SQL 저장소의 SQL을 모두 DB에 보낸다. == 버퍼 기능  == jdbc batch*/
			System.out.println("===================> 3. commit : 쓰기 지연 SQL 저장소의 INSERT 2개 SQL DB에 요청");
			tx.commit();
		} catch(Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
	}
	/**
	 * TEST-4 : 엔티티 수정 : 변경 감지(Dirty Checking)
	 */
	public void test04() {
		System.out.println("\n\nTEST - 4 : =================== 엔티티 수정 : 변경 감지 ===================");
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		// max id 
		Long maxId = findMaxId();
		maxId = (maxId == null)? 1L: maxId;
		
		tx.begin();
		
		try {
			System.out.println("===================> 1. SELECT : 캐싱되지 않은 Entity 조회  -> SELECT SQL 생성 -> SQL 요청 ");
			/* 영속 : 조회 SQL 생성 및 즉시 조회 -> 조회된 Entity 영속성 컨텍스트의 1차 캐시에 캐싱  -> 초기의 상태(값)을 따로 저장(=스냅샷을 저장) */ 
			Member m1 = em.find(Member.class, maxId);
			System.out.println(m1);
			
			System.out.println("===================> 2. UPDATE : 영속상태인 Member 수정 -> (이전과 비교해서 다르면)UPDATE SQL 생성 -> 쓰기 지연 SQL 저장소에 저장");
			m1.setName(maxId + "번 회원 수정_TEST-4");
//			m1.setName(maxId + "번 회원_TEST-3");
			System.out.println(m1);
			
			/* commit() 시점 */
			/* 1) 1차캐시에 영속상태인 객체가 초기값(스냅샷)과 다르다면 UPDETE 쿼리는 생성하여 쓰기 지연 SQL저장소에 저장한다.	 
			/* 2) 이후 내부에서 flush()호출, 쓰기 지연 SQL 저장소의 SQL을 DB에 보낸다.*/
			System.out.println("===================> 3. commit : 쓰기 지연 SQL 저장소의 UPDATE SQL DB에 요청");
			tx.commit();
		} catch(Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
	}
	/**
	 * TEST-5 : [insert -> select -> update -> select] 코드의 로그를 확인하여 영속성 컨텍스트 동작과정 확인
	 */
	public void test05() {
		System.out.println("\n\nTEST - 5 : =================== [insert -> select -> update -> select] ===================");
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		// max id 
		Long maxId = findMaxId();
		maxId = (maxId == null)? 1L: maxId + 1L;
		
		tx.begin();
		try {
			System.out.println("===================> 1. INSERT : 신규 Member 영속상태로 변환 ");
			Member m1 = new Member();
			m1.setId(maxId);
			m1.setName(maxId + "번 회원_TEST-5");
			
			/* m1 영속상태 : 영속성 컨텍스트의 1차 캐시에 케싱 -> INSERT SQL 쓰기 지연 SQL 저장소에 저장 */
			em.persist(m1);		
			System.out.println(m1);
			
			System.out.println("===================> 2. SELECT : 같은 PK Member 조회 ");
			/* 같은 PK를 가진 Entity를 조회하는 경우 영속성 컨텍스트의 1차 캐시에서 Entity를 가져온다. */
			Member m2 = em.find(Member.class, maxId);		
			System.out.print("m1 == m2 동일성 테스트 : ");
			System.out.println(m1 == m2);
					
			System.out.println("===================> 3. UPDATE : 조회한 Member 수정 -> UPDATE SQL 생성 -> 쓰기 지연 SQL 저장소 저장 ");
			/* 영속성 컨텍스트의 1차 캐시의 Entity를 변경 -> UPDATE SQL 쓰기 지연 SQL 저장소에 저장 */
			m2.setName(maxId + "번 회원 수정_TEST-5");						
			System.out.println(m2);
			
			System.out.println("===================> 4. SELECT - 2 : 수정된 Member 조회 ");
			/* 영속성 컨텍스트에 1차 캐시에서 수정된 Entity를 가져온다. */
			System.out.println(em.find(Member.class, maxId));	
			
			System.out.println("===================> 5. commit : INSERT, UPDATE SQL DB에 요청");
			/* commit시점에 내부적으로 flush()호출 쓰기 지연 SQL 저장소에 모아둔 SQL을 전부 DB에 요청한다. */
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
	}
	/**
	 * TEST-6 : [select -> update -> select] 코드의 로그를 확인하여 영속성 컨텍스트 동작과정 확인
	 */ 
	public void test06() {
		System.out.println("\n\nTEST - 6 : =================== [select -> update -> select] ===================");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Long maxId = findMaxId();
		maxId = (maxId == null)? 1L: maxId;
		
		tx.begin();
		try {
			System.out.println("===================> 1. SELECT : " + maxId + "번 Member 조회");
			/* find() 캐싱되어 있는 Entity인지 확인 
			   -> 없는 Entity라면? 조회 SQL 생성 및 즉시 조회 -> 조회된 Entity 영속성 컨텍스트의 1차 캐시에 캐싱  */
			Member m1 = em.find(Member.class, maxId);
			
			System.out.println(m1);
					
			System.out.println("===================> 2. UPDATE : 조회한 Member 수정 -> UPDATE SQL 생성 -> 쓰기 지연 SQL 저장소 저장");
			/* 영속성 컨텍스트의 1차 캐시 캐싱된 Entity를 변경, UPDATE SQL 쓰기 지연 SQL 저장소에 저장 */
			m1.setName(maxId + "번 회원 수정_TEST-6");	
			System.out.println(m1);
			
			System.out.println("===================> 3. SELECT - 2 : 수정된 Member 다시 조회");
			/* 영속성 컨텍스트에 1차 캐시에서 수정된 Entity를 가져온다. */
			System.out.println(em.find(Member.class, maxId));
			System.out.println(m1);
			
			System.out.println("===================> 4. commit : UPDATE SQL DB에 요청");
			/* commit시점에 flush()호출 쓰기지연 SQL에 모아둔 SQL을 전부 DB에 요청한다. */
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
	}
	/**
	 * SQL : SELECT MAX(id) FROM MEMBER m;
	 * @return Long
	 */
	public Long findMaxId() {
		EntityManager em = emf.createEntityManager();
		// max id 조회용 JPQL 생성
		TypedQuery<Long> query = em.createQuery(MAX_JPQL, Long.class);
		// JPQL 조회 실행
		List<Long> result = query.getResultList();	// .getResultList() 사용시 조회
		em.close();
		System.out.println("maxId : ====> " + result.get(0));
		return result.get(0);
	}
	/**
	 * SQL : SELECT * FROM MEMBER;
	 * @return List<Member>
	 */
	public List<Member> selectAll() {
		List<Member> result = null;
		EntityManager em = emf.createEntityManager();
		String jpql = "select m from Member as m";						// m = Member, 객체로 취급한다.
		result = em.createQuery(jpql, Member.class).getResultList(); 	// jpql을 사용하여 쿼리 생성
		em.close();	
		return result;
	}
}
