
    
    <%@include file="/template/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="css/profileCss.css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

 <br><br> <br><br> <br><br>
<div class="container emp-profile">
            <form method="post" action="changeProfile"  enctype="multipart/form-data">
                <div class="row">
                    <div class="col-md-4">
                        <div class="profile-img">
                           
                           <img src="<c:url value="/resources/images/${customer.customerId}.png" /> " alt="image" style="width:100%"/>
                    
                         
                           
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="profile-head">
                                    <h3>
                                       ${customer.customerName }
                                    </h3>
                                  <!--   <h6>
                                        Web Developer and Designer
                                    </h6> -->
                                    <p class="proile-rating">RANKINGS : <span>8/10</span></p>
                            <ul class="nav nav-tabs" id="myTab" role="tablist">
                                <li class="nav-item">
                                    <a class="nav-link active" id="home-tab" href="/showOrdersOfCustomer/${customer.customerId}"  >Orders</a>
                                </li>
                               
                            </ul>
                            <div class="profile-work">
                            <h3>Info </h3>
                            <h5> ${customer.customerEmail }</h5>
                            <h5> ${customer.username}</h5>
                            <h5>${customer.customerPhone }</h5>
                            
                            <h3>Addresses</h3>
                            <h4>Shipping Address</h4>
                           <h5>${customer.shippingAddress.address }</h5><br/>
                            <h4>Billing Address</h4>
                           <h5>${customer.shippingAddress.billingAddress }</h5>
                            
                        </div>
                        </div>
                    </div>
                   
                </div>
                <a href="/changeProfile/${customer.username}"> Edit Profile</a>
            </form>           
        </div>
