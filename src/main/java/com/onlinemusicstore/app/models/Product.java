package com.onlinemusicstore.app.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Le on 1/2/2016.
 */



public class Product {
	
	@Id
    private int productId;

    @NotNull (message = "The product name must not be null.")
    private String productName;
    
    
    private String productDescription;

	
    private String productCondition;
    
    private String productStatus;

    @Min(value = 0, message = "The product unit must not be less than zero.")
    private int unitInStock;
    
    private String productManufacturer;

    
    @OneToMany(targetEntity =Price2.class,mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price2> price2;
    	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="category_id")
   private Category category;
	
	
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name = "accessory_product",joinColumns = @JoinColumn(name= "product_id"),inverseJoinColumns = @JoinColumn(name = "accessory_id"))
	private Set<Accessories> accessories =  new HashSet<>();
	
	private boolean isTaxible;
	
	public boolean isTaxible() {
		return isTaxible;
	}

	public void setTaxible(boolean isTaxible) {
		this.isTaxible = isTaxible;
	}

	public Product(int productId, @NotNull(message = "The product name must not be null.") String productName,
			String productDescription, String productCondition, String productStatus,
			@Min(value = 0, message = "The product unit must not be less than zero.") int unitInStock,
			String productManufacturer, List<Price2> price2, Category category, Set<Accessories> accessories,
			MultipartFile productImage) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productCondition = productCondition;
		this.productStatus = productStatus;
		this.unitInStock = unitInStock;
		this.productManufacturer = productManufacturer;
		this.price2 = price2;
		this.category = category;
		this.accessories = accessories;
		this.productImage = productImage;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	public Product() {
		// TODO Auto-generated constructor stub
	}

	public List<Price2> getPrice2() {
		return price2;
	}

	public void setPrice2(List<Price2> price2) {
		this.price2 = price2;
	}

	@Transient
    private MultipartFile productImage;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

	
    public String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public int getUnitInStock() {
        return unitInStock;
    }

    public void setUnitInStock(int unitInStock) {
        this.unitInStock = unitInStock;
    }

    public String getProductManufacturer() {
        return productManufacturer;
    }

    public void setProductManufacturer(String productManufacturer) {
        this.productManufacturer = productManufacturer;
    }

    public MultipartFile getProductImage() {
        return productImage;
    }

    public void setProductImage(MultipartFile productImage) {
        this.productImage = productImage;
    }

	public Set<Accessories> getAccessories() {
		return accessories;
	}

	public void setAccessories(Set<Accessories> accessories) {
		this.accessories = accessories;
	}
    
    
}
