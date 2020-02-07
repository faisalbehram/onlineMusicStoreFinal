package com.onlinemusicstore.app.beans;

public class CustomerOrderCountBean {
	
	private int countOrder;
	private String Months;
	private double revenuePerMonth;
	private int quantityOfProduct;
	private double totalRevenue;
	private String productName;
	
	
	
	
	public int getQuantityOfProduct() {
		return quantityOfProduct;
	}
	public void setQuantityOfProduct(int quantityOfProduct) {
		this.quantityOfProduct = quantityOfProduct;
	}
	public double getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getRevenuePerMonth() {
		return revenuePerMonth;
	}
	public void setRevenuePerMonth(double revenuePerMonth) {
		this.revenuePerMonth = revenuePerMonth;
	}
	public int getCountOrder() {
		return countOrder;
	}
	public void setCountOrder(int countOrder) {
		this.countOrder = countOrder;
	}
	public String getMonths() {
		return Months;
	}
	public void setMonths(String months) {
		Months = months;
	}
	
	
}
