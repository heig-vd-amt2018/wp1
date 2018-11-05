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
                    <table width="100%" class="table table-striped table-bordered table-hover" id="users">
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
    document.addEventListener("DOMContentLoaded", function() {
        $('#users').DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": "api/users",
                "method": "GET",
            },
            "columns": [
                { "data": "firstName" },
                { "data": "lastName" },
                { "data": "email" },
                { "data": "role" },
                { "data": "state" },
                { "data": "id"}
            ],
            "columnDefs": [
                {
                    "targets": 3,
                    "data": "id",
                    "render": function(data) {
                        return '<code>' + data + '</code>';
                    }
                },
                {
                    "targets": 4,
                    "data": "id",
                    "render": function(data) {
                        return '<code>' + data + '</code>';
                    }
                },
                {
                    "targets": -1,
                    "render": function(data) {
                        actions = '<a href="pages/users?userId=' + data + '" class="btn btn-default"><i class="fa fa-eye"></a>';

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
