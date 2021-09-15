package ch4_Examlpes_code02.domin;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

//@Entity
@SequenceGenerator(
		name = "ORDER_ITEM_SEQ_GENERATOR",
		sequenceName = "ORDER_ITEM_SEQ",
		initialValue = 1,
		allocationSize = 1
		)
public class OrderItem {
	@Id
	@Column(name = "ORDER_ITEM_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_ITEM_SEQ_GENERATOR")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ORDER_ID")  	// 연관관계의 주인
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "ITEM_ID")		// 연관관계의 주인
	private Item item;
	
	@Column(name = "ORDER_PRICE")
	private int orderPrice;  			// 주문가격
	
	private int count;		 			// 주문 상품 개수
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", order=" + order + ", item=" + item + ", orderPrice=" + orderPrice + ", count="
				+ count + "]";
	}
}