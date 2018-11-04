<%@include file="includes/header.jsp" %>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">View all users</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true"
                           class="">Add User</a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">
                <div class="panel-body">
                        <form role="form" method="post">
                            <div class="form-group">
                                <label>First name</label>
                                <input class="form-control" placeholder="Enter text" name="firstName">
                            </div>
                            <div class="form-group">
                                <label>Last name</label>
                                <input class="form-control" placeholder="Enter text" name="lastName">
                            </div>
                            <div class="form-group">
                                <label>Email</label>
                                <input type="email" class="form-control" placeholder="Enter text" name="email">
                            </div>
                            <div class="form-group">
                                <label>Password</label>
                                <input class="form-control" placeholder="Enter text" name="password">
                            </div>
                            <div class="form-group">
                                <label>Role</label>
                                <select class="form-control" name="role">
                                    <option>Administrator</option>
                                    <option>Application developer</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>State</label>
                                <select class="form-control" name="state">
                                    <option>Enable</option>
                                    <option>Disable</option>
                                    <option>Reset</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-default btn-block">Add</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    Users
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="applications">
                        <thead>
                        <tr>
                            <th>First name</th>
                            <th>Last name</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>State</th>
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
        $('#applications').DataTable({
            "responsive": true,
            //"ajax": "api/applications",
            "columns": [
                {"data": "firstName"},
                {"data": "lastName"},
                {"data": "email"},
                {"data": "role"},
                {"data": "state"},
            ],
            "columnDefs": [
                {
                    "targets": -1,
                    "data": null,
                    "defaultContent": "<button>Click!</button>"
                }
            ],
            "language": {
                "emptyTable": "No data available in table"
            }
        });
    });
</script>

<%@include file="includes/footer.jsp" %>
