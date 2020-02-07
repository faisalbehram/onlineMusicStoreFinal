package com.onlinemusicstore.app.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onlinemusicstore.app.beans.OrderDetailBean;
import com.onlinemusicstore.app.models.Category;
import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.models.CustomerOrder;
import com.onlinemusicstore.app.models.GenericRestResponse;
import com.onlinemusicstore.app.models.Product;
import com.onlinemusicstore.app.service.AccessoryService;
import com.onlinemusicstore.app.service.CategoryService;
import com.onlinemusicstore.app.service.CustomerService;
import com.onlinemusicstore.app.service.OrderService;
import com.onlinemusicstore.app.service.Price2Service;
import com.onlinemusicstore.app.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {
	

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderService customerOrderService;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService proService;


	
	
	
	@RequestMapping("/order2")
	public String OrderManagment2(Model model) {
		System.out.println("in the Order manangement ");
		List<Customer> listOfCustomer = customerService.getAllCustomers();
		model.addAttribute("orders", listOfCustomer);
		return "orderManagement2.jsp";
		
	}
	
	@RequestMapping("/showOrdersOfCustomer/{id}")
	public String showCustomerOrder (@PathVariable("id") int id, Model model) {
		
		System.out.println("the id is " + id);
		List<CustomerOrder> customerOrders = customerOrderService.getOrdersByCustomerId(id);
		
		if(customerOrders.size() == 0) {
			return "../showOrderOfCustomer.jsp";
		}
		int totalOrders = customerOrders.size();
		model.addAttribute("totalOrders", totalOrders);
		model.addAttribute("customerOrders", customerOrders);
		return "../showOrderOfCustomer.jsp";
		
	}
	
	@RequestMapping("/customerOrderDetailByOrderId/{orderId}")
	public String customerOrderDetailByOrderId(@PathVariable("orderId") int orderId, Model model) {
		List<OrderDetailBean> orderDetails = customerOrderService.getOrderDetailById(orderId);
		model.addAttribute("orderDetails", orderDetails);
	
		System.out.println("showing orders details");		
		return "../orderDetailPage.jsp";
	}
	
	
	@RequestMapping("/orderViewByProductById")
	public  String  OrderViewByProductId(@RequestParam("productId") int productId,Model model) {
		
		List<CustomerOrder> listOfOrder = customerOrderService.listOfOrderByProduct(productId);
		List<Product> getallPro = proService.getAllPro();
		List<Category> allCate = categoryService.allMainCategories();
		model.addAttribute("mainCategories", allCate);
		GenericRestResponse response = new GenericRestResponse();
		
		response.setData(listOfOrder);
		
		model.addAttribute("orders", listOfOrder);
		model.addAttribute("products", getallPro);
		System.out.println("the customer order by product is done");

		return  "orderViewByProduct.jsp";
		
	}

	@RequestMapping("/orderViewByProduct")
	
	public  String  OrderViewByProduct(Model model) {
		
		List<CustomerOrder> listOfOrder = customerOrderService.listOfOrderByProduct();
		List<Product> getallPro = proService.getAllPro();
	
		GenericRestResponse response = new GenericRestResponse();
		List<Category> allCate = categoryService.allSubcategoies();
		model.addAttribute("mainCategories", allCate);
		response.setData(listOfOrder);
		
		model.addAttribute("orders", listOfOrder);
		model.addAttribute("products", getallPro);
		System.out.println("the customer order by product is done");

		return  "orderViewByProduct.jsp";
		
	}
	
	@RequestMapping("/orderViewCategoryById")
	public  String  OrderViewByCategoryId(@RequestParam("categoryId") int categoryId,Model model) {
		
		List<CustomerOrder> listOfOrder = customerOrderService.listOfOrderByCategoryId(categoryId);
		List<Product> getallPro = proService.getAllPro();
		List<Category> allCate = categoryService.allSubcategoies();
		model.addAttribute("mainCategories", allCate);
		GenericRestResponse response = new GenericRestResponse();
		
		response.setData(listOfOrder);
		
		model.addAttribute("orders", listOfOrder);
		model.addAttribute("products", getallPro);
		System.out.println("the customer order by product is done");

		return  "orderViewByProduct.jsp";
		
	}
	
}
