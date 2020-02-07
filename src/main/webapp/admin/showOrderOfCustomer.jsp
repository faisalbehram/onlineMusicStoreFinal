<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Customer Management Page</h1>

            <p class="lead">This is the customer Order management page.</p>
            <p>The Customer Name is : <strong> ${customerOrders.get(0).customer.customerName} </strong>  </p>
            <p>The total Order is : <strong>${totalOrders} </strong>  </p>
        </div>

        <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
              <th>Order Id</th>
              <th>UserName</th>
                <th>Order Date</th>
                <th>total Price</th>
                <th>Details</th>
              
                
            </tr>
            </thead>
            <c:forEach items="${customerOrders}" var="customerOrder">
                <tr>
                <td>${customerOrder.customerOrderId}</td>
                 <td>${customerOrder.customer.customerName}</td>
					
                  <td>${customerOrder.to_date}</td>
                  <td>${customerOrder.cart.grandTotal}</td>
                  <td><a href="/admin/customerOrderDetailByOrderId/${customerOrder.customerOrderId}"> <span class="glyphicon glyphicon-info-sign"></span></a></td>
                </tr>
            </c:forEach>
          
        </table>
  <a href="/admin/order2">Back</a>
        <%@include file="/template/footer.jsp" %>
