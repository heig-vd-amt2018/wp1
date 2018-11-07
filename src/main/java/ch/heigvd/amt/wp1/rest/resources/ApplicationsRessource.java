package ch.heigvd.amt.wp1.rest.resources;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.rest.dto.ApplicationDTO;
import ch.heigvd.amt.wp1.rest.dto.DataTables.ApplicationDataTablesDTO;
import ch.heigvd.amt.wp1.rest.dto.DataTables.DataTablesDTO;
import ch.heigvd.amt.wp1.services.dao.ApplicationsDAOLocal;
import ch.heigvd.amt.wp1.services.dao.BusinessDomainEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/applications")
public class ApplicationsRessource {

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpServletRequest request;

    @EJB
    private ApplicationsDAOLocal applicationsDAO;

    @GET
    @Produces("application/json")
    public DataTablesDTO getApplications(@QueryParam("length") int length, @QueryParam("start") int start, @QueryParam("draw") int draw) {

        User user = (User) request.getSession().getAttribute("principal");

        List<ApplicationDTO> data = new ArrayList<>();

        List<Application> applications = null;
        try {
            applications = applicationsDAO.findAllByUser(user, length, start);

            for (Application application : applications) {
                ApplicationDTO dto = new ApplicationDTO(application);
                data.add(dto);
            }
        } catch (BusinessDomainEntityNotFoundException e) {
            // Continue
        }

        long recordsTotal = applicationsDAO.count();

        DataTablesDTO result = new ApplicationDataTablesDTO(draw, recordsTotal, data);

        return result;
    }
}
