<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/template/header.jsp" %>

<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
    $("#checkit").click(function(){
        if($(this).is(':checked')){
        	var shippingAddress = $("#search_input").val();
      
        //set the variable in lower form with vars set above
       
        $("#search_input1").val(shippingAddress); 
        
        }else{
//uncheck - clear input
        $("#Address1b").val("");
        $("#Address2b").val(""); 
        $("#Cityb").val("");
        $("#Countryb").val("");
        }
    });
});

</script>


	
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
            <h1>Register Customer</h1>

            <p class="lead">Please fill in your information below:</p>
        </div>

        
		<form:form action="/registercustomer" method="post"
                   modelAttribute="billingAddress" >
        <h3>Basic Info</h3>

        <div class="form-group">
            <label for="name">Name</label><form:errors path="customer.customerName" cssStyle="color: #ff0000"/>
            <form:input path="customer.customerName" id="name" class="form-Control"/>
        </div>

       <div class="form-group">
            <label for="email">Email</label><span style="color: #ff0000">${emailMsg}</span><form:errors
                path="customer.customerEmail" cssStyle="color: #ff0000"/>
            <form:input path="customer.customerEmail" id="email" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="phone">Phone</label>
            <form:input path="customer.customerPhone" id="phone" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="username">Username</label><span style="color: #ff0000">${usernameMsg}</span><form:errors
                path="customer.username" cssStyle="color: #ff0000"/>
            <form:input path="customer.username" id="username" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="password">Password</label><form:errors path="customer.password" cssStyle="color: #ff0000"/>
            <form:password path="customer.password" id="password" class="form-Control"/>
        </div>

	
	 	
			
		<div class = "shipping">		
		 <h3>Shipping Address</h3>
		 
		 <div class="form-group">
    <label>Location:</label>
    <input type="text" class="form-control" id="search_input" placeholder="Type address..." name ="Shipping" />
    <input type="hidden" id="loc_lat" />
    <input type="hidden" id="loc_long" />
	</div>
		 </div>
		 
		 <input type="checkbox" name="same" id = "checkit" value="same" >Same As shipping Address<br>
         	
          	<div class="billing">
       		 <h3>Billing Address</h3>
		 <div class="form-group">
    <label>Location:</label>
    <input type="text" class="form-control" id="search_input1" placeholder="Type address..." name ="Billing" />
    <input type="hidden" id="loc_lat" />
    <input type="hidden" id="loc_long" />
	</div>
		 </div>
		 <div>
		  <input type="submit" value="submit" class="btn btn-default">
        <a href="<c:url value="/" />" class="btn btn-default">Cancel</a>

			</div>

        <br><br>
       
       	</form:form>	

		 
		
       <%--  <div class="form-group">
            <label for="shippingStreet">Street Name</label>
            <form:input path="shippingAddress.streetName" id="shippingStreet" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingApartmentNumber">Apartment Number</label>
            <form:input path="shippingAddress.apartmentNumber" id="shippingApartmentNumber" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingCity">City</label>
            <form:input path="shippingAddress.city" id="shippingCity" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingState">State</label>
            <form:input path="shippingAddress.state" id="shippingState" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingCountry">Country</label>
            <form:input path="shippingAddress.country" id="shippingCountry" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingZip">Zipcode</label>
            <form:input path="shippingAddress.zipCode" id="shippingZip" class="form-Control"/>
        </div>
        
         </div>
       
          <input type="checkbox" name="same" id = "checkit" value="same" >Same As shipping Address<br>
         	
          	<div class="billing">
       		 <h3>Billing Address</h3>

        <div class="form-group">
            <label for="shippingStreet">Street Name</label>
            <form:input path="streetName" id="shippingStreetb" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingApartmentNumber">Apartment Number</label>
            <form:input path="apartmentNumber" id="shippingApartmentNumberb" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingCity">City</label>
            <form:input path="city" id="shippingCityb" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingState">State</label>
            <form:input path="state" id="shippingStateb" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingCountry">Country</label>
            <form:input path="country" id="shippingCountryb" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingZip">Zipcode</label>
            <form:input path="zipCode" id="shippingZipb" class="form-Control"/>
        </div>
        	 --%>  
        	

        <%@include file="/template/footer.jsp" %>
        
  </div></div>
        
