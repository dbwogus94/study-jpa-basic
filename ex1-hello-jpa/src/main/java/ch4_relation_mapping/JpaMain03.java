package ch4_relation_mapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ch4_relation_mapping.domain.double_mapping.Member;
import ch4_relation_mapping.domain.double_mapping.Team;

/**
 * 회원의 FK를 변경하는 방법 예시
 */
public class JpaMain03 {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		JpaMain03 t = new JpaMain03();
//		t.creatTeam(emf);
//		t.creatMember(emf, 1L);
//		t.creatMember(emf, 2L);
//		t.creatMember(emf, 3L);
		
//		t.changeTeam_success(emf);
//		t.changeTeam_fall(emf);
		
		emf.close();
	}
	/**
	 * 경우1) 5번 회원의 팀을 변경 : 성공
	 * <pre>  
	 * - 새로운 팀을 생성하여 변경한다.
	 * </pre>
	 */
	void changeTeam_success(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Member member = em.find(Member.class, 5L);
			
			// 새로운 Team을 선언 -> id를 변경 -> setTeam() 
			Team team = new Team();
			team.setId(1L);
			member.setTeam(team);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	/**
	 * 경우1) 5번 회원의 팀을 변경 : 실패
	 * <pre>  
	 * - member에서 getTeam을 사용하여 참조된 Team을 가져와서 변경
	 * </pre>
	 */
	void changeTeam_fall(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Member member = em.find(Member.class, 5L);
			// 회원에서 연결된 팀을 가져와서 id변경  -> 영속상태의 Team을 가져오는것 그리고 영속상태의 엔티티의 식별자는 일반적인 방법으로는 변경할 수 없다.
			member.getTeam().setId(1L);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	/**
	 * Test용 팀 생성
	 */
	void creatTeam(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		try {
			Team a = new Team();
			a.setName("TeamA");
			Team b = new Team();
			b.setName("TeamB");
			Team c = new Team();
			c.setName("TeamC");
			em.persist(a);
			em.persist(b);
			em.persist(c);
			
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
	/**
	 * Test용 회원 생성
	 */
	void creatMember(EntityManagerFactory emf, Long teamId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		int len = (int) (Math.random() * 5) + 1;
		try {
			for(int i = 1; i<=len; i++) {
				Team team = em.find(Team.class, teamId);		// 한 번 조회한 이후 1차 캐시 캐싱하기 때문에 반복해도 상관없음
				Member member = new Member();
				member.setTeam(team);
				em.persist(member);								// 시퀀스값 여기서 가져옴
				member.setName(member.getId() + "_회원");	
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
}
