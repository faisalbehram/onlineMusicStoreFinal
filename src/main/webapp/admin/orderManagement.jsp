<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@include file="/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Order Management Page</h1>

            <p class="lead">This is the Order management page.</p>
        </div>

        <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
                <th>Customer Name</th>
                <th>Customer Email</th>
                <th>Customer Phone</th>
                <th>Shipping Address </th>
                <th>Billing Address</th>
               
                <th>Total Price</th>
             </tr>
             </thead>
              <c:forEach items="${orders}" var="order">
              	
              		 
              		 
              		
                <tr>
                    <td>${order.customer.customerName}</td>
                    <td>${order.customer.customerEmail}</td>
                    <td>${order.customer.customerPhone}</td>
                    <td>${order.shippingAddress.address}</td>
               		 <td>${order.shippingAddress.billingAddress}</td>
               		  <td>${order.cart.grandTotal}</td>
                  
                </tr>
               
            </c:forEach>
        </table>
        
        <h3>Show the Product history in order</h3>
	</div></div>
        <%@include file="/template/footer.jsp" %>
