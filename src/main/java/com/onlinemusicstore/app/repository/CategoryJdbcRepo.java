package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Category;
import com.onlinemusicstore.app.models.Price2;
import com.onlinemusicstore.app.models.Product;

@Repository
public class CategoryJdbcRepo {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Category> AllMainCategories(){
		
		String sql = "SELECT * FROM category left join price2 on price2.category_id = category.id  where category.parent_id is null";
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		List<Category> allMainCategory = namedParameterJdbcTemplate.query(sql, new RowMapper<Category>() {

			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				Category category = new Category();
				category.setId(rs.getLong("id"));
				category.setCategoryName(rs.getString("category_name"));
				
				Price2 price2 = new Price2();
				price2.setPercentageDiscount(rs.getDouble("percentage_discount"));
				category.setPrice(price2);
				return category;
			}
		});
		
		return allMainCategory;
		
	}
	
public List<Category> AllSubCategoires(){
		
		String sql = "SELECT * FROM category left join price2 on price2.category_id = category.id where category.parent_id is not null";
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		List<Category> allMainCategory = namedParameterJdbcTemplate.query(sql, new RowMapper<Category>() {

			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				Category category = new Category();
				category.setId(rs.getLong("id"));
				category.setCategoryName(rs.getString("category_name"));
				category.setParentId(rs.getInt("parent_id"));
				
				Price2 price2 = new Price2();
				price2.setPercentageDiscount(rs.getDouble("percentage_discount"));
				category.setPrice(price2);
				
				System.out.println("the set id is  " + category.getId() + "the parent id is" + category.getParentId());
				
				return category;
			}
		});
		
		return allMainCategory;
		
	}

	public Category findMainCategoryById(int categoryId) {
			String sql = "select * from category where id = :id";
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("id", categoryId);
			Category category = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters, new RowMapper<Category>() {
		
				@Override
				public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
					Category category = new Category();
					category.setId(rs.getLong("id"));
					category.setCategoryName(rs.getString("category_name"));
					return category;
				}
			}));
			
			
			return category;
		}

public void save(Category category) {
	String sql   = "insert into category (category_name, parent_id) values (:categoryName, :parentId)";
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("categoryName", category.getCategoryName());
	namedParameterJdbcTemplate.update(sql, parameters);
	System.out.println("the category are saved");
	
}

public void saveMainCategory(String mainCategory) {
String sql   = "insert into category (category_name, parent_id) values (:categoryName, :parentId)";
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("categoryName", mainCategory);
	parameters.addValue("parentId", null);
	
	namedParameterJdbcTemplate.update(sql, parameters);
	System.out.println("the category are saved");
	
}

public void saveSubCategory(int mainCategoryId, String subCategory) {
	
	String sql   = "insert into category (category_name, parent_id) values (:categoryName, :parentId)";
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("categoryName", subCategory);
		parameters.addValue("parentId", mainCategoryId);
		
		namedParameterJdbcTemplate.update(sql, parameters);
		System.out.println("the category are saved");
}

public List<Category> getSubCategoriesByParentId(int id) {
	
		String sql  = "select * from category inner join product on category.id = product.category_id where category.parent_id = :id ";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		List<Category> allSubCategory = namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Category>() {
	
			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				Category category = new Category();
				category.setId(rs.getLong("id"));
				category.setCategoryName(rs.getString("category_name"));
				
				Product product = new Product();
				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
			
				product.setProductCondition(rs.getString("product_condition"));
				product.setProductDescription(rs.getString("product_description"));
				product.setProductManufacturer(rs.getString("product_manufacturer"));
				product.setProductStatus(rs.getString("product_status"));
				product.setUnitInStock(rs.getInt("unit_in_stock"));
				category.setProduct(product);
				
				
				return category;
			}
		});
		
		return allSubCategory;
}

public Category getCategoryById(Long id, int productId) {
	System.out.println("the id is" + id);
	String sql  = "select * from category inner join product on product.product_id = :productId where id = :id";
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("id", id);
	parameters.addValue("productId", productId);
	
	Category category = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql,parameters, new RowMapper<Category>() {

		@Override
		public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
			Category category = new Category();
			category.setId(rs.getLong("id"));
			category.setCategoryName(rs.getString("category_name"));
			category.setParentId(rs.getInt("parent_id"));
			
			Product product = new Product();
			product.setProductId(rs.getInt("product_id"));
			
			category.setProduct(product);
			System.out.println("the set id is  " + category.getId() + "the parent id is" + category.getParentId());
			
			return category;
		}
	}));
	return category;
}

public Category getCategoryById(Long id) {
	System.out.println("the id is" + id);
	String sql  = "select * from category where id = :id";
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("id", id);
	
	
	Category category = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql,parameters, new RowMapper<Category>() {

		@Override
		public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
			Category category = new Category();
			category.setId(rs.getLong("id"));
			category.setCategoryName(rs.getString("category_name"));
			category.setParentId(rs.getInt("parent_id"));
			
			System.out.println("the set id is  " + category.getId() + "the parent id is" + category.getParentId());
			
			return category;
		}
	}));
	return category;
}


public Category getParentCategory(int parentId) {
	System.out.println("the id is" + parentId);
	String sql  = "select p.id,c.id as ct_id, p.category_name from category c inner join category p where c.parent_id = :id AND c.parent_id = p.id group by category_name";
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("id", parentId);
	Category category = DataAccessUtils.singleResult( namedParameterJdbcTemplate.query(sql,parameters, new RowMapper<Category>() {

		@Override
		public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
			Category category = new Category();
			category.setId(rs.getLong("id"));
			category.setCategoryName(rs.getString("category_name"));
			category.setParentId(rs.getInt("ct_id"));
			
			System.out.println("the set id is  " + category.getId() + "the parent id is");
			
			return category;
		}
	}));
	return category;
}

public Category findByCategoryName(String mainCategory) {
	String sql = "select * from category where category_name = :name";
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("name", mainCategory);
	Category category = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters, new RowMapper<Category>() {

		@Override
		public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
			Category category = new Category();
			category.setId(rs.getLong("id"));
			category.setCategoryName(rs.getString("category_name"));
			return category;
		}
	}));
	
	
	return category;
}

}
