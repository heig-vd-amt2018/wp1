<%--
    Document   : login
    Created on : Sep 9, 2015, 11:37:49 AM
    Author     : Olivier Liechti (olivier.liechti@heig-vd.ch)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <base href="${pageContext.request.contextPath}/">

    <title>Login Page</title>

    <!-- Bootstrap core CSS -->
    <link href="static/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="static/css/signin.css" rel="stylesheet">

</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <c:if test = "${error != null}">
                <p>${error}<p>
                </c:if>
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-6">
                            <a href="#" class="active" id="login-form-link">Login</a>
                        </div>
                        <div class="col-xs-6">
                            <a href="#" id="register-form-link">Register</a>
                        </div>
                    </div>
                    <hr>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form id="login-form" action="auth?action=login" method="post" role="form" style="display: block;">
                                <div class="form-group">
                                    <input type="email" name="email" class="form-control" placeholder="Email address" required autofocus>
                                </div>

                                <div class="form-group">
                                    <input type="password" name ="password" class="form-control" placeholder="Password" required>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="login-submit" class="form-control btn btn-login" value="Log in">
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <form id="register-form" action="register" method="post" role="form" style="display: none;">
                                <div class="form-group">
                                    <input type="text" name="firstName" class="form-control" placeholder="First name" value="">
                                </div>
                                <div class="form-group">
                                    <input type="text" name="lastName" class="form-control" placeholder="Last name" value="">
                                </div>
                                <div class="form-group">
                                    <input type="email" name="email" class="form-control" placeholder="Email" value="">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" class="form-control" placeholder="Password">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="passwordConfirmation" class="form-control" placeholder="Confirm password">
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="register-submit" class="form-control btn btn-register" value="Register now">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery -->
<script src="static/js/jquery.min.js"></script>

<script src="static/js/signin.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="static/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="static/js/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="static/js/sb-admin-2.js"></script>

</body>

</html>
