<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@include file="/template/header.jsp" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>OMS</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:url value="/resources/css/carousel.css" />" rel="stylesheet">
</head>
<!-- NAVBAR
================================================== -->
<body>



<!-- Carousel
================================================== -->
<div id="myCarousel" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
        <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner" role="listbox">
        <div class="item active">
            <img class="first-slide home-image" src="<c:url value="resources/images/back1.jpg"></c:url>" alt="First slide">
            <div class="container">
                <div class="carousel-caption">
                    <h1>Example headline.</h1>
                    <p>Note: If you're viewing this page via a <code>file://</code> URL, the "next" and "previous" Glyphicon buttons on the left and right might not load/display properly due to web browser security rules.</p>
                    <p><a class="btn btn-lg btn-primary" href="#" role="button">Sign up today</a></p>
                </div>
            </div>
        </div>
        <div class="item">
            <img class="second-slide home-image" src="<c:url value="resources/images/back2	.jpg"></c:url>" alt="Second slide">
            <div class="container">
                <div class="carousel-caption">
                    <h1>Another example headline.</h1>
                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    <p><a class="btn btn-lg btn-primary" href="#" role="button">Learn more</a></p>
                </div>
            </div>
        </div>
        <div class="item">
            <img class="third-slide home-image" src="<c:url value="resources/images/back3.jpg"></c:url>" alt="Third slide">
            <div class="container">
                <div class="carousel-caption">
                    <h1>One more for good measure.</h1>
                    <p>Cras justo odio, dapibus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    <p><a class="btn btn-lg btn-primary" href="#" role="button">Browse gallery</a></p>
                </div>
            </div>
        </div>
    </div>
    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div><!-- /.carousel -->


<!-- Marketing messaging and featurettes
================================================== -->
<!-- Wrap the rest of the page in another container to center all the content. -->

<div class="container marketing">

    <!-- Three columns of text below the carousel -->
   <div class="row">
        <div class="col-lg-4">
            <a class="btn btn-default" href="<c:url value="/productListcat?searchCondition=Instrument" />"
               role="button">
                <img class="img-circle" src="<c:url value="/resources/images/instrument.jpg" />" alt="Instrument
                Image" width="140" height="140"></a>

            <h2>Vehicle	</h2>
            <p>Well crafted and delicate instruments.</p>

        </div><!-- /.col-lg-4 -->



    <div class="row">
        <div class="col-lg-4">
            <a class="btn btn-default" href="<c:url value="/productListcat?searchCondition=Record" />"
               role="button">
                <img class="img-circle" src="<c:url value="/resources/images/record.jpg" />" alt="Instrument
                Image" width="140" height="140"></a>

            <h2>Mobile</h2>
            <p>An exceptional collections of music records in favor of the traditional and modern genre of jazz.</p>

        </div><!-- /.col-lg-4 -->

    <div class="row">
        <div class="col-lg-4">
            <a class="btn btn-default" href="<c:url value="/showProduct" />"
               role="button">
                <img class="img-circle" src="<c:url value="/resources/images/accessory.jpg" />" alt="Instrument
                Image" width="140" height="140"></a>

            <h2>Furniture</h2>
            <p>All musical and related geeky goods..</p>
</div></div></div></div>

<!-- Autocomplete location search input --> 
<div class="form-group">
    <label>Location:</label>
    <input type="text" class="form-control" id="search_input" placeholder="Type address..." name = "address" />
    <input type="hidden" id="loc_lat" />
    <input type="hidden" id="loc_long" />
</div>

<!-- Display latitude and longitude -->


 
</div><!-- /.container -->

   <%@include file="/template/footer.jsp" %>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>window.jQuery ||
document.write('<script src="<c:url value="/resources/js/jquery-1.11.3.min.js" />"><\/script>')</script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places&key=AIzaSyCDnuuZ-7HMrsJs2aRwRz6cccza0_11708"></script>
<script type="text/javascript">
var searchInput = 'search_input';

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

</script>


</body>
</html>
