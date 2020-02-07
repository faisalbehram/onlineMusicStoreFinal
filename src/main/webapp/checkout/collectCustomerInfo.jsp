<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/template/header.jsp" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script>window.jQuery ||
	document.write('<script src="<c:url value="/resources/js/jquery-1.11.3.min.js" />"><\/script>')</script>
	<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
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



<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Customer</h1>

            <p class="lead">Customer Details</p>
        </div>

        <form:form modelAttribute="cart" class="form-horizaontal" action="/checkout/savingOrder/${cart.cartId}">

        <h3>Basic Info</h3>

        <div class="form-group">
            <label for="name">Name</label>
            <form:input path="customer.customerName" id="name" class="form-Control" />
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <form:input path="customer.customerEmail" id="email" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="phone">Phone</label>
            <form:input path="customer.customerPhone" id="phone" class="form-Control"/>
        </div>

        
        <div class = "shipping">		
		 <h3>Shipping Address</h3>
		 
		 <div class="form-group">
    <label>Location:</label>
    <input type="text" class="form-control" id="search_input" placeholder="Type address..." name ="Shipping" value="${cart.customer.shippingAddress.address}" />
    <input type="hidden" id="loc_lat" />
    <input type="hidden" id="loc_long" />
	</div>
		 </div>
		
          	<div class="billing">
       		 <h3>Billing Address</h3>
		 <div class="form-group">
    <label>Location:</label>
    <input type="text" class="form-control" id="search_input1" placeholder="Type address..." name ="Billing" value="${cart.customer.shippingAddress.address}" />
    <input type="hidden" id="loc_lat" />
    <input type="hidden" id="loc_long" />
	</div>
		 </div>
        
        
        
        
        
      
<%-- 
        <div class="form-group">
            <label for="billingApartmentNumber">Apartment Number</label>
            <form:input path="customer.shippingAddress.apartmentNumber" id="billingApartmentNumber" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="billingCity">City</label>
            <form:input path="customer.shippingAddress.city" id="billingCity" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="billingState">State</label>
            <form:input path="customer.shippingAddress.state" id="billingState" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="billingCountry">Country</label>
            <form:input path="customer.shippingAddress.country" id="billingCountry" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="billingZip">Zipcode</label>
            <form:input path="customer.shippingAddress.zipCode" id="billingZip" class="form-Control"/>
        </div>

 --%>

        <br><br>
        <input type="submit" value="Next" class="btn btn-default"/>
        <a class="btn btn-default" href="/checkout/checkOutCancelled">Cancel</a>
        </form:form>

        <%@include file="/template/footer.jsp" %>
