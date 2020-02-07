package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreePath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onlinemusicstore.app.beans.CustomerOrderCountBean;
import com.onlinemusicstore.app.beans.OrderDetailBean;
import com.onlinemusicstore.app.models.Accessories;
import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.models.CartItem;
import com.onlinemusicstore.app.models.Category;
import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.models.CustomerOrder;
import com.onlinemusicstore.app.models.Price2;
import com.onlinemusicstore.app.models.Product;
import com.onlinemusicstore.app.models.ShippingAddress;

// for checkout the customer order this class is used
@Repository
public class CustomerOrderRepoJdbc {
	
	@Autowired
	JdbcTemplate jdbcTemplate;


	
	public List<CustomerOrder> customerOrderfindAll(){
		
		String sql = "select customer_order.customer_order_id,customer_order.cart_id, cart.grand_total, customer_order.customer_id, customer.customer_email, customer.customer_name, customer.customer_phone,shipping_address.address, shipping_address.shipping_address_id,shipping_address.address_type from customer_order inner join customer on customer_order.customer_id = customer.customer_id inner join shipping_address on shipping_address.customer_id = customer_order.customer_id  inner join cart on cart.cart_id = customer_order.cart_id inner join order_address on order_address.order_id = customer_order.customer_order_id AND shipping_address.shipping_address_id = order_address.address_id";

		String selectSql = "select customer_order.customer_order_id,customer_order.cart_id,customer_order.customer_id, customer.customer_email, customer.customer_name, customer.customer_phone,sh_ad.address as shipping_address , GROUP_CONCAT( `bl_ad`.`address` ) as \"billing_address\", cart.grand_total from customer_order left join order_address  on order_address.order_id = customer_order.customer_order_id left join customer on customer.customer_id = customer_order.customer_id left join shipping_address sh_ad on sh_ad.shipping_address_id = order_address.address_id AND sh_ad.address_type='shipping' left join shipping_address bl_ad on bl_ad.shipping_address_id = order_address.address_id AND bl_ad.address_type = 'billing' inner join cart on cart.cart_id = customer_order.cart_id  group by customer_order_id"; 
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		List<CustomerOrder> customerOrder = namedParameterJdbcTemplate.query(selectSql, new RowMapper<CustomerOrder>() {

			@Override
			public CustomerOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerOrder customerOrder  = new CustomerOrder();
				customerOrder.setCustomerOrderId(rs.getInt("customer_order_id"));
				
				
				Cart cart = new Cart();
				Customer customer = new Customer();
				ShippingAddress shippingAddress = new ShippingAddress();
				
	
//				cart.setCartId(rs.getInt("cart_id"));
				cart.setGrandTotal(rs.getDouble("grand_total"));
				
				
				
				customer.setCustomerId(rs.getInt("customer_id"));
				customer.setCustomerPhone(rs.getString("customer_phone"));
				customer.setCustomerEmail(rs.getString("customer_email"));
				customer.setCustomerName(rs.getString("customer_name"));
				
				
			//	shippingAddress.setShippingAddressId(rs.getInt("shipping_address_id"));
				shippingAddress.setBillingAddress(rs.getString("billing_address"));
				shippingAddress.setAddress(rs.getString("shipping_address"));
			//	shippingAddress.setAddressType(rs.getString("address_type"));
				

				customerOrder.setCart(cart);
				customerOrder.setShippingAddress(shippingAddress);
				customerOrder.setCustomer(customer);
				
				
				return customerOrder;
			}
		});
		return customerOrder;
		
		
	}

	public CustomerOrder save(CustomerOrder customerOrder) {
		KeyHolder holder = new GeneratedKeyHolder();
		String insertSql = "insert into customer_order ( cart_id, customer_id, to_date ) "
				+ "values ( :cartId, :customerId , now() )";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("cartId", customerOrder.getCart().getCartId());
		parameters.addValue("customerId", customerOrder.getCustomer().getCustomerId());
		
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(insertSql, parameters,holder);
		customerOrder.setCustomerOrderId(holder.getKey().intValue());
		System.out.println("done");
		
		return customerOrder;
	}
	
	public void savejoin(int orderId,int addressId) {
		
		StringBuilder insertSql  = new StringBuilder("insert into order_address (order_id, address_id)");
		insertSql.append("value (:orderId, :addressId)");
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("orderId", orderId);
		parameters.addValue("addressId", addressId);
		
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(insertSql.toString(), parameters);
	
		System.out.println("join are done");
		
	
	}
	
	public List<CustomerOrder> getOrderByProduct(int productId) {
		StringBuilder sql = new StringBuilder("select co.*, customer.customer_name,ci.total_price,p.product_name, p.product_id,p.category_id , category.category_name, price2.price , price2.price_type from  customer_order as co");
		sql.append(" inner join cart  as c on c.cart_id = co.cart_id ");
		sql.append(" inner join cart_item as ci on ci.cart_id = c.cart_id ");
		sql.append(" inner join product as p on p.product_id  = ci.product_id ");
		sql.append(" inner join category on category.id = p.category_id ");
		sql.append("inner join price2 on price2.product_id = p.product_id  ");
		sql.append(" inner join customer on customer.customer_id = co.customer_id where p.product_id = :id  group by co.customer_order_id ");
	
		
		System.out.println("the product id is "  + productId);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id",productId );
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		List<CustomerOrder> customerOrder = namedParameterJdbcTemplate.query(sql.toString(),parameters, new RowMapper<CustomerOrder>() {

			@Override
			public CustomerOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerOrder customerOrder  = new CustomerOrder();
				customerOrder.setCustomerOrderId(rs.getInt("customer_order_id"));
				
				
				
				Customer customer = new Customer();
				customer.setCustomerName(rs.getString("customer_name"));
				
				CartItem cartItem =new CartItem();
				List<CartItem> listCartItem  = new ArrayList<>();
				
				cartItem.setTotalPrice(rs.getDouble("total_price"));
				listCartItem.add(cartItem);
				
				
				Product product = new Product();
				product.setProductName(rs.getString("product_name"));
				product.setProductId(rs.getInt("product_id"));
				
				Price2 price = new Price2();
				price.setPrice(rs.getDouble("price"));
				price.setPriceType(rs.getString("price_type"));
				List<Price2> newPrice = new ArrayList<>();
				newPrice.add(price);
				
				product.setPrice2(newPrice);
				
				
				Category category = new Category();
				category.setCategoryName(rs.getString("category_name"));
				product.setCategory(category);
				
				cartItem.setProduct(product);
				
				
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				cart.setCartItems(listCartItem);
				
				
				
				customerOrder.setCart(cart);
				customerOrder.setCustomer(customer);
				
				
				return customerOrder;
			}
		});

		return customerOrder;
	}

	public List<CustomerOrder> getOrderByProduct() {
		StringBuilder sql = new StringBuilder("select co.*, customer.customer_name,ci.total_price,p.product_name, p.product_id,p.category_id, category.category_name, price2.price, price2.price_type from  customer_order as co");
		sql.append(" inner join cart  as c on c.cart_id = co.cart_id ");
		sql.append(" inner join cart_item as ci on ci.cart_id = c.cart_id ");
		sql.append(" inner join product as p on p.product_id  = ci.product_id ");
		sql.append(" inner join customer on customer.customer_id = co.customer_id ");
		sql.append(" inner join category on category.id = p.category_id ");
		sql.append("inner join price2 on price2.product_id = p.product_id group by co.customer_order_id ");
		
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		List<CustomerOrder> customerOrder = namedParameterJdbcTemplate.query(sql.toString(),parameters, new RowMapper<CustomerOrder>() {

			@Override
			public CustomerOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerOrder customerOrder  = new CustomerOrder();
				customerOrder.setCustomerOrderId(rs.getInt("customer_order_id"));
				
				
				
				Customer customer = new Customer();
				customer.setCustomerName(rs.getString("customer_name"));
				
				CartItem cartItem =new CartItem();
				List<CartItem> listCartItem  = new ArrayList<>();
				
				cartItem.setTotalPrice(rs.getDouble("total_price"));
				listCartItem.add(cartItem);
				
				
				Product product = new Product();
				product.setProductName(rs.getString("product_name"));
				product.setProductId(rs.getInt("product_id"));
				
				Price2 price = new Price2();
				price.setPrice(rs.getDouble("price"));
				price.setPriceType(rs.getString("price_type"));
				List<Price2> newPrice = new ArrayList<>();
				newPrice.add(price);
				
				product.setPrice2(newPrice);
				
				Category category = new Category();
				category.setCategoryName(rs.getString("category_name"));
				product.setCategory(category);
				
				cartItem.setProduct(product);
				
				
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				cart.setCartItems(listCartItem);
				
				
				
				customerOrder.setCart(cart);
				customerOrder.setCustomer(customer);
				
				
				return customerOrder;
			}
		});

		return customerOrder;
	}

	public List<CustomerOrder> getOrderByCategory(int categoryId) {
		StringBuilder sql = new StringBuilder("select co.*, customer.customer_name,ci.total_price,p.product_name, p.product_id,p.category_id , category.category_name , price2.price, price2.price_type from   customer_order as co");
		sql.append(" inner join cart  as c on c.cart_id = co.cart_id ");
		sql.append(" inner join cart_item as ci on ci.cart_id = c.cart_id ");
		sql.append(" inner join product as p on p.product_id  = ci.product_id ");
		sql.append(" inner join category on category.id = p.category_id ");
		sql.append(" inner join price2 on price2.product_id  = p.product_id ");
		sql.append(" inner join customer on customer.customer_id = co.customer_id where p.category_id = :id group by co.customer_order_id ");
	
		
		System.out.println("the product id is "  + categoryId);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", categoryId );
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		List<CustomerOrder> customerOrder = namedParameterJdbcTemplate.query(sql.toString(),parameters, new RowMapper<CustomerOrder>() {

			@Override
			public CustomerOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerOrder customerOrder  = new CustomerOrder();
				customerOrder.setCustomerOrderId(rs.getInt("customer_order_id"));
				
				
				
				Customer customer = new Customer();
				customer.setCustomerName(rs.getString("customer_name"));
				
				CartItem cartItem =new CartItem();
				List<CartItem> listCartItem  = new ArrayList<>();
				
				cartItem.setTotalPrice(rs.getDouble("total_price"));
				listCartItem.add(cartItem);
				
				
				Product product = new Product();
				product.setProductName(rs.getString("product_name"));
				product.setProductId(rs.getInt("product_id"));
				
				Category category = new Category();
				category.setCategoryName(rs.getString("category_name"));
				
				Price2 price = new Price2();
				price.setPrice(rs.getDouble("price"));
				price.setPriceType(rs.getString("price_type"));
				List<Price2> newPrice = new ArrayList<>();
				newPrice.add(price);
				
				product.setPrice2(newPrice);
			
				product.setCategory(category);
				
				cartItem.setProduct(product);
				
				
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				cart.setCartItems(listCartItem);
				
				
				
				customerOrder.setCart(cart);
				customerOrder.setCustomer(customer);
				
				
				return customerOrder;
			}
		});

		return customerOrder;
	}
	
	
	public List<CustomerOrderCountBean> getCountOfOrderByMonth() {
		
		String sql = "select  date_format( to_date, '%M')  AS Month ,count(to_date) as number_of_order from customer_order   group by Month ";
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		List<CustomerOrderCountBean> getOrders = namedParameterJdbcTemplate.query(sql,new RowMapper<CustomerOrderCountBean>() {

			@Override
			public CustomerOrderCountBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerOrderCountBean bean= new CustomerOrderCountBean();
				bean.setCountOrder(rs.getInt("number_of_order"));
				bean.setMonths(rs.getString("Month"));
				return bean;
				
			}
		});
			
		return getOrders;
	}
	
	public List<CustomerOrderCountBean> getRevenuePerMonth() {
		
		String sql = "select date_format( to_date, '%M-%Y')  AS Month, sum(grand_total) as revenue  from customer_order  inner join cart on cart.cart_id = customer_order.cart_id  group by Month ";
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		List<CustomerOrderCountBean> getOrders = namedParameterJdbcTemplate.query(sql,new RowMapper<CustomerOrderCountBean>() {

			@Override
			public CustomerOrderCountBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerOrderCountBean bean= new CustomerOrderCountBean();
				bean.setRevenuePerMonth(rs.getDouble("revenue"));
				bean.setMonths(rs.getString("Month"));
				return bean;
				
			}
		});
			
		return getOrders;
	}
	
	
	public List<CustomerOrderCountBean> getRevenuePerProductFromOrder() {
		
		String sql = "select  ci.total_price,p.product_name, p.product_id,sum(total_price) as total_revenue_from_product, count(p.product_id) as total_quantity from  customer_order as co  inner join cart  as c on c.cart_id = co.cart_id inner join cart_item as ci on ci.cart_id = c.cart_id  inner join product as p on p.product_id  = ci.product_id group by p.product_id ";
		
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		List<CustomerOrderCountBean> getOrders = namedParameterJdbcTemplate.query(sql,new RowMapper<CustomerOrderCountBean>() {

			@Override
			public CustomerOrderCountBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerOrderCountBean bean= new CustomerOrderCountBean();
				
				bean.setProductName(rs.getString("product_name"));
				bean.setQuantityOfProduct(rs.getInt("total_quantity"));
				bean.setTotalRevenue(rs.getDouble("total_revenue_from_product"));
				return bean;
			}
		});
			
		return getOrders;
	}

	public List<CustomerOrder> getOrdersByCustomerId(int id) {
		StringBuilder sql = new StringBuilder("select co.*, customer.customer_name,ci.total_price,c.grand_total ");
		sql.append(" from  customer_order as co  ");
		sql.append(" inner join cart  as c on c.cart_id = co.cart_id ");
		sql.append(" inner join cart_item as ci on ci.cart_id = c.cart_id  ");
		sql.append(" inner join customer on customer.customer_id = co.customer_id  ");
		sql.append(" where customer.customer_id = :id group by co.customer_order_id ");
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id );
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		List<CustomerOrder> customerOrder = namedParameterJdbcTemplate.query(sql.toString(),parameters, new RowMapper<CustomerOrder>() {

			@Override
			public CustomerOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerOrder customerOrder  = new CustomerOrder();
				customerOrder.setCustomerOrderId(rs.getInt("customer_order_id"));
				customerOrder.setTo_date(rs.getTimestamp("to_date"));
				
				Customer customer = new Customer();
				customer.setCustomerName(rs.getString("customer_name"));
				
				CartItem cartItem =new CartItem();
				List<CartItem> listCartItem  = new ArrayList<>();
				
				cartItem.setTotalPrice(rs.getDouble("total_price"));
				listCartItem.add(cartItem);
								

				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				cart.setGrandTotal(rs.getDouble("grand_total"));
				cart.setCartItems(listCartItem);
				
				
				
				customerOrder.setCart(cart);
				customerOrder.setCustomer(customer);
			
				return customerOrder;
			}
		});

		System.out.println("retuning the customer order by id ");
		return customerOrder;
		
	}

	public List<CustomerOrder> getOrderDetailById(int orderId) {
		
		StringBuilder sql = new StringBuilder(" select co.*,c.grand_total, ci.product_id,ci.total_price,ci.cart_item_id,ci.quantity,ci.accessory_id,p.product_name,p.product_manufacturer, pr.price ");
		sql.append(" from  customer_order as co  ");
		sql.append(" inner join cart  as c on c.cart_id = co.cart_id ");
		sql.append(" inner join cart_item as ci on ci.cart_id = c.cart_id  ");
		sql.append(" inner join product as p on p.product_id = ci.product_id  ");
		sql.append(" inner join price2 as pr on pr.product_id = p.product_id ");
		sql.append(" where co.customer_order_id = :id  group by pr.product_id ");
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", orderId );
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		List<CustomerOrder> customerOrder = namedParameterJdbcTemplate.query(sql.toString(),parameters, new RowMapper<CustomerOrder>() {

			@Override
			public CustomerOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerOrder customerOrder  = new CustomerOrder();
				customerOrder.setCustomerOrderId(rs.getInt("customer_order_id"));
				customerOrder.setTo_date(rs.getTimestamp("to_date"));
	
				

				Product product =  new Product();
				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setProductManufacturer(rs.getString("product_manufacturer"));
				
				Price2 price = new Price2();
				price.setPrice(rs.getDouble("price"));
				List<Price2> newPrice = new ArrayList<>();
				newPrice.add(price);
				product.setPrice2(newPrice);
				
				CartItem cartItem =new CartItem();
				List<CartItem> listCartItem  = new ArrayList<>();
				cartItem.setCartItemId(rs.getInt("cart_item_id"));
				cartItem.setTotalPrice(rs.getDouble("total_price"));
				cartItem.setQuantity(rs.getInt("quantity"));
				cartItem.setProduct(product);
				
				listCartItem.add(cartItem);
								

				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				cart.setGrandTotal(rs.getDouble("grand_total"));
				
				cart.setCartItems(listCartItem);
				customerOrder.setCart(cart);
		
				return customerOrder;
			}
		});

		return customerOrder;
	}
	
@Transactional
public List<OrderDetailBean> getOrderDetailById2(int orderId) {
		
		StringBuilder sql = new StringBuilder(" select DISTINCT co.to_date,c.cart_id,c.grand_total, ci.product_id,ci.total_price,ci.quantity,p.product_name,p.product_manufacturer,pr.price  ");
		sql.append(" from  customer_order as co  ");
		sql.append(" inner join cart  as c on c.cart_id = co.cart_id ");
		sql.append(" inner join cart_item as ci on ci.cart_id = c.cart_id  ");
		sql.append(" inner join product as p on p.product_id = ci.product_id  ");
		sql.append(" inner join price2 as pr on pr.product_id = ci.product_id  ");
		sql.append(" where co.customer_order_id = :id  group by p.product_id ");
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", orderId );
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		List<OrderDetailBean> customerOrder = namedParameterJdbcTemplate.query(sql.toString(),parameters, new RowMapper<OrderDetailBean>() {

			@Override
			public OrderDetailBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderDetailBean bean = new OrderDetailBean();
				
				bean.setProductId(rs.getInt("product_id"));
				bean.setToDate(rs.getString("to_date"));
				bean.setGrandTotal(rs.getDouble("grand_total"));
				bean.setTotalPrice(rs.getDouble("total_price"));
				bean.setProductName(rs.getString("product_name"));
				bean.setManufactratur(rs.getString("product_manufacturer"));
				bean.setQuantity(rs.getInt("quantity"));
				bean.setBasePrice(rs.getDouble("price"));
				bean.setCartId(rs.getInt("cart_id"));
				
				System.out.println(" get accessory name  " );
				OrderDetailBean accessoryNames = accessoryNameByProductIdAndCartId(bean.getProductId(), bean.getCartId());
				if(accessoryNames != null) {
					bean.setAccessoryName(accessoryNames.getAccessoryName());
					System.out.println("the accessory name is  " + accessoryNames.getAccessoryName());
				}
				
				return bean;
				
			}
		});

		return customerOrder;
	}

	public OrderDetailBean accessoryNameByProductIdAndCartId(int prodId, int cartId) {
		
		StringBuilder sql = new StringBuilder(" select   group_concat(ac.accessory_name) as accessory_name from cart_item as ci ");
		sql.append(" inner join accessories as ac on ci.accessory_id = ac.accessory_id ");
		sql.append(" inner join cart on cart.cart_id = ci.cart_id ");
		sql.append(" left join accessory_product as ap on ap.accessory_id = ci.accessory_id   ");
		sql.append(" where ap.product_id = :proId && cart.cart_id = :cartId group by ap.product_id   ");
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("proId", prodId );
		parameters.addValue("cartId", cartId );
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		OrderDetailBean customerOrder = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql.toString(),parameters, new RowMapper<OrderDetailBean>() {

			@Override
			public OrderDetailBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderDetailBean bean = new OrderDetailBean();
				
				
				bean.setAccessoryName(rs.getString("accessory_name"));
		
				
				return bean;
				
			}
		}));
		
		return customerOrder;

	}
	
	
}
