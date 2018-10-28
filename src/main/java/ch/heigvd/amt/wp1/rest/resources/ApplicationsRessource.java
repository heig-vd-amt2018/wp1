package ch.heigvd.amt.wp1.rest.resources;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;
import ch.heigvd.amt.wp1.rest.dto.ApplicationDTO;
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
    public List<ApplicationDTO> getApplications() {

        ApplicationDeveloper user = (ApplicationDeveloper) request.getSession().getAttribute("principal");

        List<ApplicationDTO> result = new ArrayList<>();

        List<Application> applications = null;
        try {
            applications = applicationsDAO.findAllByDeveloper(user);

            for (Application application : applications) {
                ApplicationDTO dto = new ApplicationDTO();
                populateDTOFromEntity(application, dto);
                result.add(dto);
            }
        } catch (BusinessDomainEntityNotFoundException e) {
            // Continue
        }

        return result;
    }

    @POST
    @Consumes("application/json")
    public Response createApplication(ApplicationDTO applicationDTO) {

        ApplicationDeveloper user = (ApplicationDeveloper) request.getSession().getAttribute("principal");

        boolean alreadyBeenCreated = true;
        long applicationId;

        try {
            applicationId = applicationsDAO.findByName(applicationDTO.getName(), user).getId();
        } catch (BusinessDomainEntityNotFoundException ex) {
            alreadyBeenCreated = false;

            applicationId = applicationsDAO.create(
                    new Application(
                            user,
                            applicationDTO.getName(),
                            applicationDTO.getDescription()
                    )
            );
        }

        URI applicationUri = uriInfo
                .getBaseUriBuilder()
                .path(ApplicationsRessource.class)
                .path(ApplicationsRessource.class, "getApplication")
                .build(applicationId);

        ResponseBuilder builder;
        if (alreadyBeenCreated) {
            builder = Response.created(applicationUri);
        } else {
            builder = Response.ok().location(applicationUri);
        }

        return builder.build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Application getApplication(@PathParam(value = "id") long id) throws BusinessDomainEntityNotFoundException {
        return applicationsDAO.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response updateApplication(ApplicationDTO applicationDTO, @PathParam(value = "id") long id) throws BusinessDomainEntityNotFoundException {
        Application application = applicationsDAO.findById(id);
        application.setName(applicationDTO.getName());
        application.setDescription(applicationDTO.getDescription());
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteApplication(@PathParam(value = "id") long id) throws BusinessDomainEntityNotFoundException {
        Application application = applicationsDAO.findById(id);
        applicationsDAO.delete(application);
        return Response.ok().build();
    }

    private void populateDTOFromEntity(Application application, ApplicationDTO dto) {
        ApplicationDeveloper user = (ApplicationDeveloper) request.getSession().getAttribute("principal");

        dto.setId(application.getId());
        dto.setCreatedDate(application.getCreatedDate());
        dto.setName(application.getName());
        dto.setDescription(application.getDescription());
        dto.setApiKey(application.getApiKey());
        dto.setApiSecret(application.getApiSecret());
    }
}
