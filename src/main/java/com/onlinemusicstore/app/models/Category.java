package com.onlinemusicstore.app.models;

import javax.persistence.OneToOne;



public class Category extends AbstractEntity {
	


	
	private String categoryName;
	
//	@ManyToOne(cascade={CascadeType.ALL})
//	@JoinColumn(name="parent_id")
//	private Category category;
//
//	@OneToMany(mappedBy="category")
//	private Set<Category> subCategories = new HashSet<Category>();
	
	
	private int parentId;
	
	
	
	
	public int getParentId() {
		return parentId;
	}


	public void setParentId(int parentId) {
		this.parentId = parentId;
	}


	@OneToOne(mappedBy = "category", targetEntity = Product.class)
	private  Product product;
	
	@OneToOne(mappedBy = "category",targetEntity = Price2.class)
	private Price2 price;
	
	
	
//	public Category(String categoryName, Category category, Set<Category> subCategories) {
//		super();
//		this.categoryName = categoryName;
//		this.category = category;
//		this.subCategories = subCategories;
//	}
//

	public Price2 getPrice() {
		return price;
	}


	public void setPrice(Price2 price) {
		this.price = price;
	}


	public Category() {
		super();
	}


	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	
//	public Category getCategory() {
//		return category;
//	}
//
//
//	public void setCategory(Category category) {
//		this.category = category;
//	}
//
//
//	public Set<Category> getSubCategories() {
//		return subCategories;
//	}
//
//
//	public void setSubCategories(Set<Category> subCategories) {
//		this.subCategories = subCategories;
//	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
	

	 
	
	
	
	
	
	
	
}
