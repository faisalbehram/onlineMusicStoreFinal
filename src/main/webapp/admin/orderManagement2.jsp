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
             </tr>
             </thead>
              <c:forEach items="${orders}" var="order">
              	
              		 
              		 
              		
                <tr>
                    <td><a href="/admin/showOrdersOfCustomer/${order.customerId }">${order.customerName}</a></td>
                    <td>${order.customerEmail}</td>
                    <td>${order.customerPhone}</td>
 
                  
                </tr>
               
            </c:forEach>
        </table>

	</div></div>
        <%@include file="/template/footer.jsp" %>
