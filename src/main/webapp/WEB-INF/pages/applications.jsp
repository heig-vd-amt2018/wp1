<%@include file="includes/header.jsp" %>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">${pageTitle}</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <table id="applicationsTable" class="table">
            <thead>
            <th>Company</th>
            <th>Sectors</th>
            </thead>
            <tbody>
            <c:forEach items="${companies}" var="company">
                <tr>
                    <td>
                        <a href="pages/companyDetails?id=${company.id}">${company.name}</a>
                    </td>
                    <td>
                        <c:forEach items="${company.sectors}" var="sector">
                            ${sector.name} &nbsp;
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <!-- /.row -->
</div>

<%@include file="includes/footer.jsp" %>
