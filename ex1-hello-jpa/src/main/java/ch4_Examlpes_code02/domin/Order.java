package ch4_Examlpes_code02.domin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//@Entity
@SequenceGenerator(
		name = "ORDERS_SEQ_GENERATOR",
		sequenceName = "ORDERS_SEQ",
		initialValue = 1,
		allocationSize = 1
		)
@Table(name = "ORDERS")
public class Order {
	@Id
	@Column(name = "ORDER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQ_GENERATOR")
	private Long id;
	
	@ManyToOne							// 여러개의 주문은 하나의 고객이 가질 수 있다.
	@JoinColumn(name = "MEMBER_ID")		// 연관관계의 주인
	private Member member;
	
	@Column(name = "ORDERDATE")
	private LocalDateTime orderDate;	// 날짜  + 시간
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	/* 양방향 매핑 */
	@OneToMany(mappedBy = "order")		// 연관관계의 주인을 가리킨다.
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();

	// 연관관계 편의 메서드 정의
	/** 
	* 양방향 연관관계 - 연관관계 편의 메서드<BR> 
	*	- 주문이 가진 주문 상품리스트에 새로운 주문상품을 넣는것이 자연스럽다.<BR> 
	*	- 때문에 Order에 연관관계 편의 메서드를 정의함.
	*/
	public void addOrderItem(OrderItem orderItem) {
		// 연관관계 주인에게 매핑
		orderItem.setOrder(this);
		// 역방향 매핑
		this.orderItems.add(orderItem);
	}
	/**
	 * 양방향 연관관계 - 연관관계 편의 메서드<BR>
	 * 	- 주문시 주문을 한 회원의 주문리스트에 해당 주문을 추가 
	 */
	public void changeMember(Member member) {
		// 연관관계 주인에게 매핑
		this.setMember(member);
		// 역방향 매핑
		member.getOrders().add(this);
	}
	
	// getter && setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
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
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", member=" + member + ", orderDate=" + orderDate + ", status=" + status + "]";
	}
	
	
}