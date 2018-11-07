<%@include file="includes/header.jsp" %>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><a href="pages/users">${user.firstName} ${user.lastName}</a></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <form id="form" role="form">
                        <div class="form-group">
                            <label>ID</label>
                            <input name="userId" value="${principal.id}" class="form-control" readonly="">
                        </div>
                        <div class="form-group">
                            <label>First Name</label>
                            <input name="firstName" value="${principal.firstName}" class="form-control" placeholder="Enter first name">
                        </div>
                        <div class="form-group">
                            <label>Last Name</label>
                            <input name="lastName" value="${principal.lastName}" class="form-control" placeholder="Enter last name">
                        </div>
                        <div class="form-group">
                            <label>email</label>
                            <input name="email" value="${principal.email}" class="form-control" placeholder="Enter email">
                        </div>
                        <div class="form-group">
                            <label>Role</label>
                            <select class="form-control" name="role">
                                <option value="ADMINISTRATOR">Administrator</option>
                                <option value="APPLICATION_DEVELOPER">Application developer</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>State</label>
                            <select class="form-control" name="state">
                                <option value="ENABLED">Enabled</option>
                                <option value="DISABLED">Disabled</option>
                                <option value="RESET">Reset</option>
                            </select>
                        </div>
                        <button name="save" class="btn btn-default btn-block">Save</button>
                        <button name="delete" class="btn btn-default btn-block">Delete</button>
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

<script>
    document.addEventListener("DOMContentLoaded", function() {
        $("button[name=save]").click(function (e) {
            $.ajax({
                method: "POST",
                url: "pages/users?" + $("#form").serialize(),
                complete: function () {
                    window.location.href = "pages/users";
                }
            });

            e.preventDefault();
            return false;
        });

        $("button[name=delete]").click(function (e) {
            $.ajax({
                method: "DELETE",
                url: "pages/users?" + $("#form").serialize(),
                complete: function () {
                    window.location.href = "pages/users";
                }
            });

            e.preventDefault();
            return false;
        });
    });
</script>

<%@include file="includes/footer.jsp" %>