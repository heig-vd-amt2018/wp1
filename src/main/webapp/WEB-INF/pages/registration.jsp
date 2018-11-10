<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <base href="${pageContext.request.contextPath}/">

    <meta name="description" content="WP1 for the AMT Project">
    <meta name="author" content="L. Delafontaine, T. Gallandat, J. Kaufmann & X. Vaz Afonso">

    <title>AMT - WP1</title>

    <!-- Bootstrap Core CSS -->
    <link href="static/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="static/css/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="static/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="static/css/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="static/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<div id="page" value="registration"></div>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Register</h3>
                </div>
                <div class="panel-body">
                    <p class="text-center">Already an account ? Please <a id="login" href="login">login</a> !</p>
                    <c:if test="${alert != null}">
                        <div class="alert alert-dismissable ${alert.cssClass}">
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                ${alert.message}
                        </div>
                    </c:if>
                    <form id="form" role="form" action="register" method="post">
                        <fieldset>
                            <div class="form-group">
                                <input type="text" id="firstname" name="firstName" class="form-control" placeholder="First name"
                                       required value="<c:out value="${firstName}"/>">
                            </div>
                            <div class="form-group">
                                <input type="text" id="lastname" name="lastName" class="form-control" placeholder="Last name"
                                       required value="<c:out value="${lastName}"/>">
                            </div>
                            <div class="form-group">
                                <input type="email" id="email" name="email" class="form-control" placeholder="Email"
                                       required value="<c:out value="${email}"/>">
                            </div>
                            <div class="form-group">
                                <input type="password" id="password" name="password" class="form-control" placeholder="Password">
                            </div>
                            <div class="form-group">
                                <input type="password" id="confirmPassword" name="passwordConfirmation" class="form-control"
                                       placeholder="Confirm password">
                            </div>
                            <button id="submit" type="submit" class="btn btn-primary btn-block">Register</button>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery -->
<script src="static/js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="static/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="static/js/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="static/js/sb-admin-2.js"></script>

</body>

</html>