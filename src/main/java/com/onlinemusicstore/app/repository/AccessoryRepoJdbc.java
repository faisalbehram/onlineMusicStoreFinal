package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Accessories;
import com.onlinemusicstore.app.models.CartItem;
import com.onlinemusicstore.app.models.Price2;
import com.onlinemusicstore.app.models.Product;

@Repository
public class AccessoryRepoJdbc {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public int saveAccessory(String accessoryName, int productId) {
		KeyHolder holder = new GeneratedKeyHolder();
		String insertSql = "insert into accessories (accessory_name) values (:accessoryName)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();	
		parameters.addValue("accessoryName", accessoryName);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(insertSql, parameters,holder);
		int accessoryId = holder.getKey().intValue();
		
		String insertToJoin = "insert into accessory_product (accessory_id, product_id) value (:accessoryId, :productId)";
		parameters.addValue("accessoryId", accessoryId);
		parameters.addValue("productId", productId);
		namedParameterJdbcTemplate.update(insertToJoin, parameters);
		System.out.println("the accessory are saved");
		return accessoryId;
		
	}
	
	public List<Accessories> accessoriesByProductId(int productId){
		String sql = "select accessories.accessory_id,accessory_name,accessory_product.product_id, price2.price from accessories inner join accessory_product on accessories.accessory_id = accessory_product.accessory_id inner join product on  product.product_id = accessory_product.product_id inner join price2 on price2.accessory_id = accessories.accessory_id where product.product_id = :productId";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();	
		parameters.addValue("productId", productId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		List<Accessories> accessories = namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Accessories>() {

			@Override
			public Accessories mapRow(ResultSet rs, int rowNum) throws SQLException {
					Accessories accessory = new Accessories();
					accessory.setAccessoryName(rs.getString("accessory_name"));
					accessory.setAccessoryId(rs.getInt("accessory_id"));
					
					Product product = new Product();
					Set<Product> pro = new HashSet<Product>();
					product.setProductId(rs.getInt("product_id"));
					pro.add(product);
					
					Price2 price2 = new Price2();
					price2.setPrice(rs.getDouble("price"));
					List<Price2> pric = new ArrayList<Price2>();
					pric.add(price2);
					
					accessory.setPrice2(pric);
					accessory.setProducts(pro);
					
				return accessory;
			}
		});
		System.out.println("the accessory size is " + accessories.size());
		return accessories;
	}
	
	
	public List<Accessories> allAccessories(){
		String sql = "select accessories.accessory_id,accessories.accessory_name,accessory_product.product_id from accessories inner join accessory_product on accessories.accessory_id = accessory_product.accessory_id inner join product on product.product_id = accessory_product.product_id";
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		List<Accessories> accessories = namedParameterJdbcTemplate.query(sql,new RowMapper<Accessories>() {

			@Override
			public Accessories mapRow(ResultSet rs, int rowNum) throws SQLException {
					Accessories accessory = new Accessories();
					accessory.setAccessoryName(rs.getString("accessory_name"));
					accessory.setAccessoryId(rs.getInt("accessory_id"));
					
					Product product = new Product();
					Set<Product> pro = new HashSet<Product>();
					product.setProductId(rs.getInt("product_id"));
				
					pro.add(product);
					accessory.setProducts(pro);
					
				return accessory;
			}
		});
		
		System.out.println("the all accessories the accessory size is " + accessories.size());
		return accessories;
	}

	public List<Accessories> getAccessoryByCartId(int cartId) {
		String sql = "select cart_item.cart_item_id,cart_item.product_id,cartitem_accessory.parent_cartitem_id,cartitem_accessory.child_accessory_id,accessories.accessory_name from cart_item inner join cartitem_accessory on cart_item.cart_item_id = cartitem_accessory.parent_cartitem_id  inner join cart_item acc_cart on acc_cart.cart_item_id = cartitem_accessory.child_accessory_id  inner join accessories  on accessories.accessory_id = acc_cart.accessory_id where cart_item.cart_id= :id";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		MapSqlParameterSource parameters = new MapSqlParameterSource();	
		parameters.addValue("id", cartId);
		
		List<Accessories> accessories = namedParameterJdbcTemplate.query(sql,parameters,new RowMapper<Accessories>() {

			@Override
			public Accessories mapRow(ResultSet rs, int rowNum) throws SQLException {
					Accessories accessory = new Accessories();
					accessory.setAccessoryName(rs.getString("accessory_name"));
					
					CartItem cartItem = new CartItem();
					cartItem.setCartItemId(rs.getInt("cart_item_id"));
					Set<CartItem> cartItm = new HashSet<CartItem>();
					cartItm.add(cartItem);
					
					Product product = new Product();
					Set<Product> pro = new HashSet<Product>();
					product.setProductId(rs.getInt("product_id"));
					pro.add(product);
					
					accessory.setParentId(  rs.getInt("parent_cartitem_id"));
					accessory.setChildId(rs.getInt("child_accessory_id"));
					
					
					
					accessory.setCartItem(cartItm);
					accessory.setProducts(pro);
			
					
				return accessory;
			}
		});
		
		System.out.println("the all accessories the accessory size is " + accessories.size());
		return accessories;
	}
	
	

}