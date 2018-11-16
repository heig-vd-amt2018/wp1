package ch.heigvd.amt.wp1.rest.resources;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.rest.dto.ApplicationDTO;
import ch.heigvd.amt.wp1.rest.dto.DataTables.ApplicationDataTablesDTO;
import ch.heigvd.amt.wp1.rest.dto.DataTables.DataTablesDTO;
import ch.heigvd.amt.wp1.services.dao.ApplicationsDAOLocal;
import ch.heigvd.amt.wp1.services.dao.BusinessDomainEntityNotFoundException;
import ch.heigvd.amt.wp1.services.dao.UsersDAOLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/applications")
public class ApplicationsResource {

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpServletRequest request;

    @EJB
    private ApplicationsDAOLocal applicationsDAO;

    @EJB
    private UsersDAOLocal usersDAO;

    @GET
    @Produces("application/json")
    public DataTablesDTO getApplications(@QueryParam("length") int length, @QueryParam("start") int start, @QueryParam("draw") int draw) {

        String userIdParam = request.getParameter("userId");

        User user = (User) request.getSession().getAttribute("principal");

        if (user.getRole() == User.Role.ADMINISTRATOR && userIdParam != null) {
            try {
                user = usersDAO.findById(Long.parseLong(userIdParam));
            } catch (BusinessDomainEntityNotFoundException e) {
                // Continue
            }
        }

        List<ApplicationDTO> data = new ArrayList<>();
        long recordsTotal = 0;

        if (user != null) {
            try {
                List<Application> applications = applicationsDAO.findAllByUser(user, length, start);

                for (Application application : applications) {
                    ApplicationDTO dto = new ApplicationDTO(application);
                    data.add(dto);
                }

                recordsTotal = applicationsDAO.countByUser(user);
            } catch (BusinessDomainEntityNotFoundException e) {
                // Continue
            }
        }

        DataTablesDTO result = new ApplicationDataTablesDTO(draw, recordsTotal, data);

        return result;
    }
}
