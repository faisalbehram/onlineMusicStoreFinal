<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@include file="/template/header.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Order</h1>

            <p class="lead">Order confirmation</p>
        </div>

        <div class="container">

            <div class="row">

                

                    <div class="well col-xs-10 col-sm-10 col-md-6 col-xs-offset-1 col-sm-offset-1 co-md-offset-3">

                        <div class="text-center">
                            <h1>Order Details</h1>
                        </div>
                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6" >
                               
                            </div>
                            <div class="col-xs-6 col-sm-6 col-md-6 text-right">
                                <p>Order Date: <strong>${ orderDetails.get(0).toDate}</strong>
                            </div>
                        </div>

                        
                        <div class="row">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Product</th>
                                    <th>Manufactrur</th>
                                    <th>#</th>
                                    <th class="text-center">Base Price</th>
                                    <th class="text-center">Price Paided</th>
                                    
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="order"   items="${orderDetails}" >
                                    <tr>
                                        <td class="col-md-9"><em>${order.productName}</em></td>
                                        <td class="col-md-9"><em>${order.manufactratur}</em></td>
                                        <td class="col-md-1" style="text-align: center">${order.quantity}</td> 
                                        <td class="col-md-1" style="text-align: center">${order.basePrice}</td>
                                        <td class="col-md-1" style="text-align: center">${order.totalPrice}</td>
                                       
                                    </tr>
                                    	<c:if test="${order.accessoryName != null }">
                                   <tr>  <td class="col-md-1" style="text-align: center"> Accessories <strong>${order.accessoryName}</strong></td></c:if></tr>
                                  </c:forEach>
                                   
                                  
                               

                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td class="text-right">
                                        <h4><strong>Grand Total: </strong></h4>
                                    </td>
                                    <td class="text-center text-danger">
                                        <h4><strong>$ ${orderDetails.get(0).grandTotal}</strong></h4>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                    
                        <br><br>
                     
                         <a class="btn btn-default" href="/checkout/checkOutCancelled">Cancel</a>
                    </div>
         

            </div>
        </div>


        <%@include file="/template/footer.jsp" %>
