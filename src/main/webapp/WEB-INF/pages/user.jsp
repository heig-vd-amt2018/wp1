<%@include file="includes/header.jsp" %>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><a href="pages/users">Users</a> > ${user.firstName} ${user.lastName}</h1>
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
                    <form id="form" role="form" method="post">
                        <div class="form-group">
                            <label>ID</label>
                            <input name="userId" value="<c:out value="${user.id}"/>" class="form-control" readonly="">
                        </div>
                        <div class="form-group">
                            <label>First Name</label>
                            <input name="userFirstName" value="<c:out value="${user.firstName}"/>" class="form-control"
                                   placeholder="Enter first name" required="required">
                        </div>
                        <div class="form-group">
                            <label>Last Name</label>
                            <input id="lastname" name="userLastName" value="<c:out value="${user.lastName}"/>" class="form-control"
                                   placeholder="Enter last name" required="required">
                        </div>
                        <div class="form-group">
                            <label>email</label>
                            <input id="email" name="userEmail" value="<c:out value="${user.email}"/>" class="form-control"
                                   placeholder="Enter email" required="required" readonly="">
                        </div>
                        <div class="form-group">
                            <label>Role</label>
                            <select class="form-control" name="userRole">
                                <c:forTokens items="APPLICATION_DEVELOPER/ADMINISTRATOR" delims="/" var="role">
                                    <option value="${role}" ${role == user.role ? 'selected' : ''}>${role}</option>
                                </c:forTokens>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>State</label>
                            <c:set var="resetState" value="RESET"/>
                            <c:choose>
                                <c:when test="${user.state == resetState}">
                                    <input name="reset" value="${user.state}" class="form-control" readonly="">
                                </c:when>
                                <c:otherwise>
                                    <select class="form-control" name="userState">
                                        <c:forTokens items="ENABLED/DISABLED" delims="/" var="role">
                                            <option value="${role}" ${role == user.state ? 'selected' : ''}>${role}</option>
                                        </c:forTokens>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <button id="user-update-btn" name="update" class="btn btn-default btn-block">Save</button>
                        <button id="user-delete-btn" name="delete" class="btn btn-default btn-block">Delete</button>
                        <button id="user-reset-btn" name="resetPassword" class="btn btn-default btn-block">Reset password</button>
                    </form>
                </div>
                <!-- /.panel-body -->
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    User's applications
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="applications">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th><code>API_KEY</code></th>
                            <th><code>API_SECRET</code></th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                    <!-- /.table-responsive -->
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>

<script>

    document.addEventListener("DOMContentLoaded", function () {
        $("button[name=update]").click(function () {
            $("#form").attr("action", "pages/users?action=update");
            $("#form").submit();
        });

        $("button[name=delete]").click(function () {
            $("#form").attr("action", "pages/users?action=delete");
            $("#form").submit();
        });

        $("button[name=resetPassword]").click(function () {
            $("#form").attr("action", "pages/users?action=resetPassword");
            $("#form").submit();
        });

        $('#applications').DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": "api/applications/${user.id}",
                "method": "GET"
            },
            "columns": [
                {"data": "name"},
                {"data": "description"},
                {"data": "apiKey"},
                {"data": "apiSecret"},
                {"data": "id"}
            ],
            "columnDefs": [
                {
                    "targets": 2,
                    "data": "id",
                    "render": function (data) {
                        return '<code>' + data + '</code>';
                    }
                },
                {
                    "targets": 3,
                    "data": "id",
                    "render": function (data) {
                        return '<code>' + data + '</code>';
                    }
                },
                {
                    "targets": -1,
                    "render": function (data) {
                        actions = '<a href="pages/applications?appId=' + data + '" class="btn btn-default"><i class="fa fa-eye"></a>';

                        return actions;
                    }
                }
            ],
            "language": {
                "emptyTable": "No application in table.",
                "zeroRecords": "There were no matching applications found."
            },
            "searching": false,
            "ordering": false,
            "paging": true,
            "responsive": true
        });

    });
</script>

<%@include file="includes/footer.jsp" %>