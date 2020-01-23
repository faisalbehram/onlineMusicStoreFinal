package com.onlinemusicstore.app.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;



public class Price2 implements Serializable {

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	
	private int thePrice;
	private double price;
		
	private double percentageDiscount;
	
	private String priceType;
	
	private Date fromDate;
	
	private Date toDate;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Accessories accessories;
	
	

	public Accessories getAccessories() {
		return accessories;
	}

	public void setAccessories(Accessories accessories) {
		this.accessories = accessories;
	}

	
	public Price2(Integer id, @Min(value = 0, message = "The product price must no be less then zero.") double price,
			double percentageDiscount, String priceType, Date fromDate, Date toDate, Product product) {
		super();
		this.id = id;
		this.price = price;
		this.percentageDiscount = percentageDiscount;
		this.priceType = priceType;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.product = product;
	}

	public Price2() {
		super();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public double getPercentageDiscount() {
		return percentageDiscount;
	}

	public void setPercentageDiscount(double percentageDiscount) {
		this.percentageDiscount = percentageDiscount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	
}
