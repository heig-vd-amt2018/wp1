<%@include file="includes/header.jsp" %>

<div id="page" value="profile"></div>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">My profile</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <c:if test="${alert != null}">
            <div class="col-lg-12">
                <div class="alert alert-dismissable ${alert.cssClass}">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        ${alert.message}
                </div>
            </div>
        </c:if>
        <div class="col-lg-12">
            <div class="panel panel-default">
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <form id="form" role="form" action="pages/profile" method="post">
                        <div class="form-group">
                            <input type="text" name="firstName" class="form-control" placeholder="First name"
                                   required value="<c:out value="${principal.firstName}"/>">
                        </div>
                        <div class="form-group">
                            <input type="text" name="lastName" class="form-control" placeholder="Last name"
                                   required value="<c:out value="${principal.lastName}"/>">
                        </div>
                        <div class="form-group">
                            <input type="email" name="email" class="form-control" placeholder="Email"
                                   required value="<c:out value="${principal.email}"/>" readonly>
                        </div>
                        <div class="form-group">
                            <input type="password" id="password" name="password" class="form-control" placeholder="Password">
                        </div>
                        <div class="form-group">
                            <input type="password" id="confirmPassword" name="passwordConfirmation" class="form-control"
                                   placeholder="Confirm password">
                        </div>
                        <button type="submit" id="save" class="btn btn-primary btn-block">Save</button>
                    </form>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>

<%@include file="includes/footer.jsp" %>
