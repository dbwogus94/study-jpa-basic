package ch4_relation_mapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ch4_relation_mapping.domain.object_focus.Member_O;
import ch4_relation_mapping.domain.object_focus.Team_O;
import ch4_relation_mapping.domain.relation_focus.Member_R;
import ch4_relation_mapping.domain.relation_focus.Team_R;


/**
 * @Title 연관관계 매핑 기조 - 테이블 모델링과, 객체 모델링의 차이
 * <pre>
 * TEST-1) 테이블에 맞춘 모델링 : 저장, 조회
 * TEST-2) 객체 모델링 : 저장, 조회
 * </pre>
 */
public class JpaMain {
	public static void main(String[] args) {
		JpaMain t = new JpaMain();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//		t.relationPocus(emf);
//		t.objectPocus(emf);
//		t.objectPocusUpdate(emf);
		emf.close();
	}
	/**
	 * TEST-1) 테이블에 맞춘 모델링 : 저장, 조회
	 */
	void relationPocus(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			// 팀 저장 
			Team_R team = new Team_R();
			team.setName("TeamA");
			em.persist(team);					// GeneratedValue를 사용했기 때문에 이 시점에 시퀀스 값을 알 수 있음
			// 회원 저장
			Member_R member = new Member_R();
			member.setName("member1");
			member.setTeamId(team.getId());		// Team id를 직접 저장
			em.persist(member);
			tx.commit();
			
			/* 회원이 속한 팀을 가져온다면 */
			// 조회 : 회원을 먼저 조회
			Member_R findMember = em.find(Member_R.class, member.getId()); 
			// 연관관계가 없기 때문에 회원이 가진 FK를 가지고 다시 조회한다.
			Team_R findTeam = em.find(Team_R.class, team.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	/**
	 * TEST-2) 객체 모델링 : 저장 조회
	 */
	void objectPocus(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			// 팀 저장 
			Team_O team = new Team_O();
			team.setName("TeamB");
			em.persist(team);
			
			// 회원 저장
			Member_O member = new Member_O();
			member.setName("Member2");
			member.setTeam(team);
			em.persist(member);
			
			/* 변경된 값을 1차캐시가 아닌 DB에서 조회하고 싶을 때 */
			boolean doSync = true;
			if(doSync) {
				em.flush();     // 쓰기지연 SQL의 SQL을 모두 DB에 요청
				em.clear();		// 1차 캐시 비운다.
			}
			
			/* 위의 코드를 사용하지 않으면 1차 캐시에서 값을 가져온다. */
			// 회원 조회 : jpa가 MEMBER 테이블과 TEAM 테이블을 JOIN하여 Member에 매핑한다. 
			Member_O findMember = em.find(Member_O.class, member.getId());
			
			// 팀 조회 : findMember.team에 join team이 참조 되었기 때문에 바로 사용이 가능하다.
			Team_O findTeam = findMember.getTeam();
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	/**
	 * 
	 * @param emf
	 */
	void objectPocusUpdate(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			// id가 2인 회원의 팀을 "TeamB"로 변경
			Member_O member = em.find(Member_O.class, 2L);
			
			// 새로운 팀 TeamB 생성
			Team_O teamB = new Team_O();
			teamB.setName("TeamB");
			em.persist(teamB);
			
			member.setTeam(teamB);
			
			// commit()이 호출된다고 1차 캐시 비우지 않는다.
			tx.commit(); 		// DB에 MEMBER테이블의 FK인 TEAM_ID를 변경하는 SQL을 요청한다.
			
			// 커밋 이후 조회 : 1차 캐시에서 조회된다.
			Member_O findMember = em.find(Member_O.class, member.getId());
			System.out.println(findMember);
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
}