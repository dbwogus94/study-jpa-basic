package ch3_Examples_code01.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//특정 DB는 Order by 때문에 Order가 명령어로 잡혀있는 경우가 있음. 그래서 테이블명은 Orders로 한다.
@Table(name = "ORDERS")  
public class Order {
	@Id @GeneratedValue
	@Column(name = "ORDER_ID")
	private Long id;
	
	@Column(name = "MEMBER_ID")
	private Long memberId;
	
	// LocalDateTime: 날짜 + 시간으로 하이버네이트가 자동 매핑한다.
	@Column(name = "ORDER_DATE")
	private LocalDateTime orderDate;	 
	
	// EnumType은 무조건 STRING로 바꿔야함. ORDINAL로 하면 DB 꼬일 수 있음
	@Enumerated(EnumType.STRING)  
	private OrderStatus status;		// 주문상태

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
