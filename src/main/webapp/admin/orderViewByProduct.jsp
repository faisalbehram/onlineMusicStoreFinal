<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@include file="/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Order Management Page</h1>
            
              <form action="/admin/orderViewByProductById">
            	<select name="productId">
            		  <c:forEach items="${products}" var="product" >	 
				
				  <option value="${product.productId}"> ${product.productName} </option>
				   	
				   	 </c:forEach> 
				</select>
				   <input type="submit" value="Search" />
			</form>
			
			<form action="orderViewCategoryById">
            	<select name="categoryId">
            		  <c:forEach items="${mainCategories}" var="mainCategory" >	 
				
				  <option value="${mainCategory.id}"> ${mainCategory.categoryName} </option>
				   	
				   	 </c:forEach> 
				</select>
				   <input type="submit" value="Search" />
			</form>

            <p class="lead">This is the Order management page.</p>
        </div>

        <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
                <th>Customer Name</th>
                <th>Product name</th>
                <th>Product Basic Price</th>
                <th>Price Paid</th>
                <th>Category Name</th>
                
          
             </tr>
             </thead>
              <c:forEach items="${orders}" var="order">
              	
              		 
              		 
              		
                <tr>
                    <td>${order.customer.customerName}</td>
                    <td>${order.cart.cartItems.get(0).product.productName}</td>
                     <td>${order.cart.cartItems.get(0).product.price2.get(0).price}</td>
                    <td>${order.cart.cartItems.get(0).totalPrice}</td>
                    <td>${order.cart.cartItems.get(0).product.category.categoryName}</td>
                    
                  
                </tr>
               
            </c:forEach>
        </table>
	</div></div>
        <%@include file="/template/footer.jsp" %>
