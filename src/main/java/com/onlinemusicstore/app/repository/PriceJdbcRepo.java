package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Category;
import com.onlinemusicstore.app.models.Price2;
import com.onlinemusicstore.app.models.Product;

@Repository
public class PriceJdbcRepo {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void savePrice(Price2 price) {
		String sql = "INSERT  INTO  price2 (from_date,percentage_discount,price,price_type,to_date,product_id,category_id,accessory_id)values(:fromDate,:percentageDiscount,:price,:priceType,:toDate,:productId,:categoryId,:accessoryId)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("fromDate", price.getFromDate());
		
		parameters.addValue("percentageDiscount", price.getPercentageDiscount());
		System.out.println("the pricing to be saving1");
		
		parameters.addValue("price", price.getPrice());
		System.out.println("the pricing to be saving2");
		
		parameters.addValue("priceType", price.getPriceType());
		System.out.println("the pricing to be saving3");
		
		parameters.addValue("toDate", price.getToDate());
		System.out.println("the pricing to be saving4");
		
		if (price.getProduct() == null) {
			parameters.addValue("productId", price.getProduct());
			System.out.println("the pricing to be saving5 pro null");
		}else {
			parameters.addValue("productId", price.getProduct().getProductId());
			System.out.println("the pricing to be saving5 pro not null");
		}
		
		
		if(price.getCategory() == null) {
			System.out.println("the pricing to be category is null");
			parameters.addValue("categoryId", price.getCategory());
			System.out.println("the pricing to be saving6");
		}else {
			System.out.println("the pricing to be category");
			parameters.addValue("categoryId", price.getCategory().getId());
			System.out.println("the pricing to be saving6");
		}
		
		if(price.getAccessories() == null) {
			parameters.addValue("accessoryId", price.getAccessories());
		}else {
			parameters.addValue("accessoryId", price.getAccessories().getAccessoryId());
		}
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(sql, parameters);
		System.out.println("price are saved");
		
	}
	
	public void saveDiscountForCategory(Price2 price) {
		
		String sql = "INSERT  INTO  price2 (from_date,percentage_discount,price,price_type,to_date,category_id) values(:fromDate,:percentageDiscount,:price,:priceType,:toDate,:categoryId)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("fromDate", price.getFromDate());
		parameters.addValue("percentageDiscount", price.getPercentageDiscount());
		System.out.println("the pricing to be saving1");
		parameters.addValue("price", price.getPrice());
		System.out.println("the pricing to be saving2");
		parameters.addValue("priceType", price.getPriceType());
		System.out.println("the pricing to be saving3");
		parameters.addValue("toDate", price.getToDate());
		
		System.out.println("the pricing to be saving5");
		parameters.addValue("categoryId", price.getCategory().getId());
		System.out.println("the pricing to be saving");
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(sql, parameters);
		System.out.println("price are saved");
		
	}
	
public void updateDiscountForCategory(Price2 price) {
	
	String sql = "update price2 SET from_date = :fromDate ,percentage_discount = :percentageDiscount,to_date = :toDate  where id = :id";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("id", price.getId());
	parameters.addValue("fromDate", price.getFromDate());
	parameters.addValue("percentageDiscount", price.getPercentageDiscount());
	
	
	System.out.println("the pricing to be saving3");
	parameters.addValue("toDate", price.getToDate());
	
	
	System.out.println("the pricing to be saving");
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	namedParameterJdbcTemplate.update(sql, parameters);
	System.out.println("price are saved");
	
}

	
	
	// return a list 
	public List<Price2> findPriceByProductId(int i) {
		
		String sql = "select * from price2 where product_id = :productId";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("productId", i);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		List<Price2> pricesList =namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

			@Override
			public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				Price2 price = new Price2();
				price.setId(rs.getInt("id"));;
				price.setPrice(rs.getDouble("price"));
				price.setPercentageDiscount(rs.getDouble("percentage_discount"));
				price.setPriceType(rs.getString("price_type"));
				price.setFromDate(rs.getDate("from_date"));
				price.setToDate(rs.getDate("to_date"));
				
				Product pro = new Product();
				pro.setProductId(rs.getInt("product_id"));
				price.setProduct(pro);
				
				System.out.println("the product id in jdbc is " + price.getProduct().getProductId());
				return price;
			}
		});
		return pricesList;
		
	}
	
	// its optional
public Price2 findPriceByProductIdOptiona(int productId) {
		
		String sql = "select * from price2 where product_id = :productId";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("productId", productId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		Price2 pricesList = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

			@Override
			public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				Price2 price = new Price2();
				price.setId(rs.getInt("id"));;
				price.setPrice(rs.getDouble("price"));
				price.setPercentageDiscount(rs.getDouble("percentage_discount"));
				price.setPriceType(rs.getString("price_type"));
				price.setFromDate(rs.getDate("from_date"));
				price.setToDate(rs.getDate("to_date"));
				
				Product pro = new Product();
				pro.setProductId(rs.getInt("product_id"));
				price.setProduct(pro);
				
				System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
				return price;
			}
		}));
		
		return pricesList;
		
	}
	
	
public List<Price2> allBasePrice() {
		
		String sql = "select price2.id,parent_id,from_date,percentage_discount,price_type,price,to_date, product.product_id, product.category_id from price2 right outer join product on price2.product_id = product.product_id  inner join category on category.id = product.category_id where price2.price_type = 'Basic' AND price2.product_id IS NOT null AND price2.from_date IS NOT null";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("basePrice", "Basic");
		System.out.println("in all base price");
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		List<Price2> pricesList =(List<Price2>) namedParameterJdbcTemplate.query(sql, parameters, new RowMapper<Price2>() {

			@Override
			public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				Price2 price = new Price2();
				price.setId(rs.getInt("id"));
				price.setPrice(rs.getDouble("price"));
				price.setPercentageDiscount(rs.getDouble("percentage_discount"));
				price.setPriceType(rs.getString("price_type"));
				price.setFromDate(rs.getDate("from_date"));
				price.setToDate(rs.getDate("to_date"));
				
				Product pro = new Product();
				pro.setProductId(rs.getInt("product_id"));
				
				Category category = new Category();
				category.setId(rs.getLong("category_id"));
				category.setParentId(rs.getInt("parent_id"));
				
				price.setCategory(category);
				
				price.setProduct(pro);
				
				System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
				return price;
			}
		});
		return pricesList;
		
	}
	
// this for generic discount
public Price2 findGenericPrice() {
	
	String sql = "select * from price2 where price_type = :discount AND from_date IS NOT NULL AND product_id IS null AND category_id IS null";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("discount", "Discount");
	System.out.println("in all base price");
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
	Price2 pricesList = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

		@Override
		public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price2 price = new Price2();
			price.setId(rs.getInt("id"));;
			price.setPrice(rs.getDouble("price"));
			price.setPercentageDiscount(rs.getDouble("percentage_discount"));
			price.setPriceType(rs.getString("price_type"));
			price.setFromDate(rs.getDate("from_date"));
			price.setToDate(rs.getDate("to_date"));
			
			Product pro = new Product();
			pro.setProductId(rs.getInt("product_id"));
			price.setProduct(pro);
			
			System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
			return price;
		}
	}));
	return pricesList;
	
}


public Price2 findTaxPrice() {
	
	String sql = "select * from price2 where price_type = :tax AND from_date IS NOT NULL AND product_id IS null AND category_id IS null";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("tax", "Tax");
	System.out.println("in all base price");
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
	Price2 pricesList = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

		@Override
		public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price2 price = new Price2();
			price.setId(rs.getInt("id"));;
			price.setPrice(rs.getDouble("price"));
			price.setPercentageDiscount(rs.getDouble("percentage_discount"));
			price.setPriceType(rs.getString("price_type"));
			price.setFromDate(rs.getDate("from_date"));
			price.setToDate(rs.getDate("to_date"));
			return price;
		}
	}));
	return pricesList;
	
}
// price of base price by product Id
public Price2 findByProductGetBasePrice(int productId) {
	
	String sql = "select * from price2 where product_id = :productId AND from_date IS NOT null AND price_type = 'Basic' ";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("productId", productId);
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
	Price2 pricesList = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

		@Override
		public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price2 price = new Price2();
			price.setId(rs.getInt("id"));;
			price.setPrice(rs.getDouble("price"));
			price.setPercentageDiscount(rs.getDouble("percentage_discount"));
			price.setPriceType(rs.getString("price_type"));
			price.setFromDate(rs.getDate("from_date"));
			price.setToDate(rs.getDate("to_date"));
			
			Product pro = new Product();
			pro.setProductId(rs.getInt("product_id"));
			price.setProduct(pro);
			
			System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
			return price;
		}
	}));
	
	return pricesList;
	
	
}

// list price by id
public Price2 findByPriceId(int  priceId) {
	String sql = "select * from price2 where id = :priceId  ";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("priceId", priceId);
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
	Price2 pricesList = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

		@Override
		public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price2 price = new Price2();
			price.setId(rs.getInt("id"));;
			price.setPrice(rs.getDouble("price"));
			price.setPercentageDiscount(rs.getDouble("percentage_discount"));
			price.setPriceType(rs.getString("price_type"));
			price.setFromDate(rs.getDate("from_date"));
			price.setToDate(rs.getDate("to_date"));
			
			Product pro = new Product();
			pro.setProductId(rs.getInt("product_id"));
			price.setProduct(pro);
			
			System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
			return price;
		}
	}));
	
	return pricesList;
	
	
}

public List<Price2> findByProductForViewProductDiscount(int  productId) throws IncorrectResultSetColumnCountException
 {
	String sql = "select * from price2 where product_id = :productId AND price_type = :discount AND from_date IS NOT null";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("productId", productId);
	parameters.addValue("discount", "Discount");
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	List<Price2> pricesList = namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

		@Override
		public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price2 price = new Price2();
			price.setId(rs.getInt("id"));;
			price.setPrice(rs.getDouble("price"));
			price.setPercentageDiscount(rs.getDouble("percentage_discount"));
			price.setPriceType(rs.getString("price_type"));
			price.setFromDate(rs.getDate("from_date"));
			price.setToDate(rs.getDate("to_date"));
			
			Product pro = new Product();
			pro.setProductId(rs.getInt("product_id"));
			price.setProduct(pro);
			
			System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
			return price;
		}
	});
	return pricesList;
	
	
}


public List<Price2> allDiscountPrice() {
	
	String sql = "select id,from_date,percentage_discount,price_type,price,to_date, product.product_id, product.category_id from price2 right outer join product on price2.product_id = product.product_id where price2.price_type = 'Discount' AND price2.product_id IS NOT null AND price2.from_date IS NOT null";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("discount", "Discount");
	System.out.println("in all base price");
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
	
	List<Price2> pricesList =(List<Price2>) namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

		@Override
		public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price2 price = new Price2();
			price.setId(rs.getInt("id"));;
			price.setPrice(rs.getDouble("price"));
			price.setPercentageDiscount(rs.getDouble("percentage_discount"));
			price.setPriceType(rs.getString("price_type"));
			price.setFromDate(rs.getDate("from_date"));
			price.setToDate(rs.getDate("to_date"));
			
			Product pro = new Product();
			pro.setProductId(rs.getInt("product_id"));
			price.setProduct(pro);
			
			Category category = new Category();
			category.setId(rs.getLong("category_id"));
			price.setCategory(category);
			
			System.out.println("the product it in jdbc is " + price.getCategory().getId() + "the cateogy wise is" + category.getId());
			return price;
		}
	});
	
	
	
	return pricesList;
	
}

public List<Price2> allCategoryDiscounts() {
	
	String sql = "select * from price2 where price_type = :discount AND category_id IS not null AND from_date IS NOT null";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("discount", "Discount");
	System.out.println("in all category price");
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
	
	List<Price2> pricesList =(List<Price2>) namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

		@Override
		public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price2 price = new Price2();
			price.setId(rs.getInt("id"));;
			price.setPrice(rs.getDouble("price"));
			price.setPercentageDiscount(rs.getDouble("percentage_discount"));
			price.setPriceType(rs.getString("price_type"));
			price.setFromDate(rs.getDate("from_date"));
			price.setToDate(rs.getDate("to_date"));
			
			System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
			return price;
		}
	});
	
	
	
	return pricesList;
	
}


// find price for update related to product Id 

	public List<Price2> findProductPriceForUpdate(int productId) {
			
			String sql = "select * from price2 where product_id = :productId AND price_type =:discount";
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("productId", productId);
			parameters.addValue("discount", "Discount");
			System.out.println("in all findProductPriceForUpdate price");
	
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
	
	List<Price2> pricesList =(List<Price2>) namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

		@Override
		public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price2 price = new Price2();
			price.setId(rs.getInt("id"));;
			price.setPrice(rs.getDouble("price"));
			price.setPercentageDiscount(rs.getDouble("percentage_discount"));
			price.setPriceType(rs.getString("price_type"));
			price.setFromDate(rs.getDate("from_date"));
			price.setToDate(rs.getDate("to_date"));
			
			Product pro = new Product();
			pro.setProductId(rs.getInt("product_id"));
			price.setProduct(pro);
			
			System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
			return price;
		}
	});
	
	return pricesList;
	
}

	public Price2 findCategoryDiscount(Long categoryId) {
		String sql = "select * from price2 where category_id = :categoryId ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("categoryId", categoryId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		Price2 pricesList = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

			@Override
			public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				Price2 price = new Price2();
				price.setId(rs.getInt("id"));;
				price.setPrice(rs.getDouble("price"));
				price.setPercentageDiscount(rs.getDouble("percentage_discount"));
				price.setPriceType(rs.getString("price_type"));
				price.setFromDate(rs.getDate("from_date"));
				price.setToDate(rs.getDate("to_date"));
				
				
				System.out.println("the product it in jdbc is " );
				return price;
			}
		}));
		System.out.println("the price list is" + pricesList);
		return pricesList;
		
	}

	public List<Price2> findAllCategoryDiscount() {
		String sql = "select * from price2 inner join product on product.category_id = price2.category_id where price2.price_type = 'Discount' AND price2.category_id IS not null AND from_date IS NOT null";
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		List<Price2> pricesList= namedParameterJdbcTemplate.query(sql,new RowMapper<Price2>() {

			@Override
			public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				Price2 price = new Price2();
				price.setId(rs.getInt("id"));;
				price.setPrice(rs.getDouble("price"));
				price.setPercentageDiscount(rs.getDouble("percentage_discount"));
				price.setPriceType(rs.getString("price_type"));
				price.setFromDate(rs.getDate("from_date"));
				price.setToDate(rs.getDate("to_date"));
	
				Category category = new Category();
				category.setId(rs.getLong("category_id"));
				price.setCategory(category);

				
				System.out.println("the product it in jdbc is " );
				return price;
			}
		});
		System.out.println("the price list is" + pricesList);
		return pricesList;
	}

	public Price2 getDiscountForProduct(int productId, Long id, int parentId) {
		String sql  = " select pr.*, (case when p.product_id is not null then 1 else (case when sc.id is not null then (case when sc.id = :id then 2 else 3 end) else 4 end) end) as priority from price2 pr left outer join product p on p.product_id = pr.product_id left outer join category sc on sc.id = pr.category_id where pr.price_type = 'Discount' and (p.product_id is null or p.product_id = :productId) and (sc.id is null or sc.id = :id or sc.id = :parentId) order by priority limit 1"; 
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("productId", productId);
		parameters.addValue("id", id);
		parameters.addValue("parentId", parentId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		Price2 pricesList = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

			@Override
			public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				Price2 price = new Price2();
				price.setId(rs.getInt("id"));;
				price.setPrice(rs.getDouble("price"));
				price.setPercentageDiscount(rs.getDouble("percentage_discount"));
				price.setPriceType(rs.getString("price_type"));
				price.setFromDate(rs.getDate("from_date"));
				price.setToDate(rs.getDate("to_date"));
				
				
				System.out.println("thegetDiscountForProduct  it in jdbc is " );
				return price;
			}
		}));
		System.out.println("the price list is" + pricesList);
		return pricesList;
	}

	public void updatePrice(Price2 price) {
		
		String sql = "update price2 SET from_date = :fromDate ,percentage_discount = :percentageDiscount,to_date = :toDate  where id = :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		
		parameters.addValue("fromDate", price.getFromDate());
		parameters.addValue("percentageDiscount", price.getPercentageDiscount());
		
		System.out.println("the pricing to be saving3");
		parameters.addValue("toDate", price.getToDate());
		
		System.out.println("the pricing to be saving4");
		
		parameters.addValue("id", price.getId());
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(sql, parameters);
		System.out.println("price are saved");
	}

	public Price2 getAccessoryPrice(Integer id) {		
		String sql = "select * from price2 where accessory_id = :id";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		Price2 pricesList = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

			@Override
			public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				Price2 price = new Price2();
				price.setId(rs.getInt("id"));;
				price.setPrice(rs.getDouble("price"));
				price.setPercentageDiscount(rs.getDouble("percentage_discount"));
				price.setPriceType(rs.getString("price_type"));
				price.setFromDate(rs.getDate("from_date"));
				price.setToDate(rs.getDate("to_date"));
				
				
				System.out.println("the product it in jdbc is for accessory prices is " );
				return price;
			}
		}));
		System.out.println("the price list is" + pricesList);
		return pricesList;
	}
}
