package com.onlinemusicstore.app.beans;

public class OrderDetailBean {

	private int orderId;
	private int productId;
	private int cartItemId;
	private int cartId;
	private String productName;
	private String manufactratur;
	private int quantity;
	private int joinProductId;
	private int joinAccessoryId;
	private String accessoryName;
	private int accessoryId;
	private String toDate;
	private double grandTotal;
	private double totalPrice;
	private double basePrice;
	
	
	
	
	
	
	
	
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	public double getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(int cartItemId) {
		this.cartItemId = cartItemId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getManufactratur() {
		return manufactratur;
	}
	public void setManufactratur(String manufactratur) {
		this.manufactratur = manufactratur;
	}
	public int getJoinProductId() {
		return joinProductId;
	}
	public void setJoinProductId(int joinProductId) {
		this.joinProductId = joinProductId;
	}
	public int getJoinAccessoryId() {
		return joinAccessoryId;
	}
	public void setJoinAccessoryId(int joinAccessoryId) {
		this.joinAccessoryId = joinAccessoryId;
	}
	public String getAccessoryName() {
		return accessoryName;
	}
	public void setAccessoryName(String accessoryName) {
		this.accessoryName = accessoryName;
	}
	public int getAccessoryId() {
		return accessoryId;
	}
	public void setAccessoryId(int accessoryId) {
		this.accessoryId = accessoryId;
	}
	
	
}
