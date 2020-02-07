<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Spring Boot reCAPTCHA with AOP</title>
        <script type="text/javascript"
                src="webjars/jquery/3.3.1/jquery.min.js"></script>

        <script src='https://www.google.com/recaptcha/api.js'></script>
    </head>
    <body>

        <script>
            $(document).ready(function () {
                $("#button").click(function () {
                    var captchaResponse = grecaptcha.getResponse();
                    var name = $("#name").val();
                    var helloRequest = {
                        'name': name,
                        'captchaResponse': captchaResponse
                    };

                    $.ajax({
                        type: "POST",
                        contentType: 'application/json',
                        dataType: "json",
                        data: JSON.stringify(helloRequest),
                        url: "http://localhost:8080/hello",
                        success: function (data) {
                            alert(data.message);
                        }
                    });
                });
            });
        </script>

        <div>
            <input type="text" id="name"/>
            <button type="submit" id="button">Hello</button>
            <div class="g-recaptcha" data-sitekey="CLIENT_KEY"></div>
        </div>
    </body>
</html>