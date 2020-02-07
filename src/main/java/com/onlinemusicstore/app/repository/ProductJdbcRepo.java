package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Category;
import com.onlinemusicstore.app.models.Price2;
import com.onlinemusicstore.app.models.Product;

@Repository
public class ProductJdbcRepo {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Product> allProducts() {
		System.out.println("in jdbc Template");
		List<Product> product = jdbcTemplate.query("select * from product ",new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product product = new Product();
				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				
				product.setProductCondition(rs.getString("product_condition"));
				product.setProductDescription(rs.getString("product_description"));
				product.setProductManufacturer(rs.getString("product_manufacturer"));
				product.setProductStatus(rs.getString("product_status"));
				product.setUnitInStock(rs.getInt("unit_in_stock"));
				Category category = new Category();
				category.setId(rs.getLong("category_id"));
				product.setCategory(category);
				
				return product;
			}
		});
		System.out.println("the all product are  "  + product);
		
		return product;
	}
	
	public Product getProductById(int productId) {
		
		Product product = jdbcTemplate.queryForObject("select product.product_id, product.product_condition, product.product_name, product.product_description, product.product_manufacturer, product.product_status,product.unit_in_stock,category.id,category.parent_id, price2.price, CASE product.is_taxible  when 1  then  (select price2.percentage_discount from price2 where price2.price_type = 'Tax' ) end AS tax  from product  inner join category on category.id = product.category_id inner join price2 on price2.product_id = product.product_id AND price2.price_type = 'Basic' where product.product_id = ?", new Object[] {productId}, new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product product = new Product();
				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				
				product.setProductCondition(rs.getString("product_condition"));
				product.setProductDescription(rs.getString("product_description"));
				product.setProductManufacturer(rs.getString("product_manufacturer"));
				product.setProductStatus(rs.getString("product_status"));
				product.setUnitInStock(rs.getInt("unit_in_stock"));
				
				Category category = new Category();
				category.setId(rs.getLong("id"));
				category.setParentId(rs.getInt("parent_id"));
				
				Price2 price = new Price2();
				price.setPrice(rs.getDouble("price"));
				price.setPercentageDiscount(rs.getInt("tax"));
				
				List<Price2> price2 = new ArrayList<Price2>();
				price2.add(price);
				
				product.setPrice2(price2);
				product.setCategory(category);
				
				return product;
			}
		});
		return product;
	}
	
	
	// searching 
	
	public List<Product> findProductByName(String productName) {
		String sql = "select * from product where product_name LIKE :productName ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("productName",productName+ "%");
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		List<Product> productList =namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product product = new Product();
				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				
				product.setProductCondition(rs.getString("product_condition"));
				product.setProductDescription(rs.getString("product_description"));
				product.setProductManufacturer(rs.getString("product_manufacturer"));
				product.setProductStatus(rs.getString("product_status"));
				product.setUnitInStock(rs.getInt("unit_in_stock"));
				return product;
			}
		});
		return productList;
	}

	@Transactional
	@Modifying
	public void deleteProduct(String id) {
		String sql = "delete  from product where product_id =  :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		namedParameterJdbcTemplate.update(sql, parameters);

		
	}
	
	public Product save(Product product) {
		KeyHolder holder = new GeneratedKeyHolder();
		System.out.println("the product is saving");
		String insertSql = "insert into product (product_condition,product_description,product_manufacturer,product_name,product_status,unit_in_stock,category_id,is_taxible) values(:product_condition,:product_description,:product_manufacturer,:product_name, :product_status, :unit_in_stock, :category_id, :is_taxible)";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		
		parameters.addValue("product_condition", product.getProductCondition());
		System.out.println("the product is saving2");
		parameters.addValue("product_description", product.getProductDescription());
		System.out.println("the product is saving3");
		parameters.addValue("product_manufacturer", product.getProductManufacturer());
		System.out.println("the product is saving4");
		parameters.addValue("product_name", product.getProductName());
		System.out.println("the product is saving5");
		parameters.addValue("product_status", product.getProductStatus());
		System.out.println("the product is saving6");
		parameters.addValue("unit_in_stock", product.getUnitInStock());
		System.out.println("the product is saving7");
		parameters.addValue("category_id", product.getCategory().getId());
		System.out.println("the product is saving8");
		parameters.addValue("is_taxible", product.isTaxible());
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		namedParameterJdbcTemplate.update(insertSql.toString(), parameters,holder);
		System.out.println("the product is saving9");
		
		 product.setProductId(holder.getKey().intValue());
		return  product;
		
	}
	
}
