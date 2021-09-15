package ch4_Examlpes_code02.domin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

//@Entity
@SequenceGenerator(
		name = "ITEM_SEQ_GENERATOR",
		sequenceName = "ITEM_SEQ",
		initialValue = 1,
		allocationSize = 1
		)
public class Item {
	@Id 
	@Column(name = "ITME_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ_GENERATOR")
	private Long id;
	
	private String name;
	
	private int price;
	
	@Column(name = "STOCK_QUANTITY")
	private int stockQuantity;		// 재고수량
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", price=" + price + ", stockQuantity=" + stockQuantity + "]";
	}
}
