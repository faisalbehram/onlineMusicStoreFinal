
    
    <%@include file="/template/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="css/profileCss.css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places&key=AIzaSyCDnuuZ-7HMrsJs2aRwRz6cccza0_11708"></script>
	<script type="text/javascript">
	var searchInput = 'search_input';
	var searchInput1 = 'search_input1';
	$(document).ready(function () {
	    var autocomplete;
	    autocomplete = new google.maps.places.Autocomplete((document.getElementById(searchInput)), {
	        types: ['geocode'],
	    });
		
	    google.maps.event.addListener(autocomplete, 'place_changed', function () {
	        var near_place = autocomplete.getPlace();
	        document.getElementById('loc_lat').value = near_place.geometry.location.lat();
	        document.getElementById('loc_long').value = near_place.geometry.location.lng();
			
	        document.getElementById('latitude_view').innerHTML = near_place.geometry.location.lat();
	        document.getElementById('longitude_view').innerHTML = near_place.geometry.location.lng();
	    });
	});
	
	$(document).on('change', '#'+searchInput, function () {
	    document.getElementById('latitude_input').value = '';
	    document.getElementById('longitude_input').value = '';
		
	    document.getElementById('latitude_view').innerHTML = '';
	    document.getElementById('longitude_view').innerHTML = '';
	});
	
	$(document).on('change', '#'+searchInput1, function () {
	    document.getElementById('latitude_input').value = '';
	    document.getElementById('longitude_input').value = '';
		
	    document.getElementById('latitude_view').innerHTML = '';
	    document.getElementById('longitude_view').innerHTML = '';
	});
	
	</script>


<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

 <br><br> <br><br> <br><br>
<div class="container emp-profile">
            <form method="post" action="/profile/editProfile"  enctype="multipart/form-data">
                <div class="row">
                    <div class="col-md-4">
                        <div class="profile-img">
                           
                           <img src="<c:url value="/resources/images/${customer.customerId}.png" /> " alt="image" style="width:100%"/>
                    
                           
                            <div class="file btn btn-lg btn-primary">
                                
                                <input type="hidden" value="${customer.customerId}" name="customerId" id = "customerId">
                                 <input type="hidden" value="${customer.username}" name="username" id = "customerId">
                                
                                <input type="file" name="profileImage"  />
                                 
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="profile-head">
                        			
                                    <h3> Customer Name <input type="text" value="  ${customer.customerName}" name="custmerName">
                                    
                                     
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
                             <div class="col-md-4">
                        <div class="profile-work">
                            <h3>Info </h3>
                            <h4> ${customer.username}</h4>
                            <h5>Email <input type="text" value=" ${customer.customerEmail}" name="customerEmail"> </h5>
                             <h5>Phone Number  <input type="text" value=" ${customer.customerPhone}" name="customerPhone"> </h5>
                            <h3>Addresses</h3>
                           
                            <h4>Shipping Address</h4>
                          
                            <input type="hidden" name="shippingId" value="${customer.shippingAddress.shippingAddressId}">
                            <h5><textarea rows="4" cols="" id="search_input" name="shippingAddress" > ${customer.shippingAddress.address}</textarea></h5>
                           <br/>
                            <h4>Billing Address</h4>
                            <input type="hidden" name="billingId" value="${customer.shippingAddress.billingAddressId}">
                           <h5><textarea rows="4" cols="" name="billingAddress" > ${customer.shippingAddress.billingAddress}</textarea></h5>
                            
                        </div>
                    </div>
                        </div>
                    </div>
                   
                </div>
             <input type="submit" name="change" value="Save" style="color: black; align-content: center;"/>
            </form>           
        </div>
