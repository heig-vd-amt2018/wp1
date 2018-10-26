package ch.heigvd.amt.wp1.rest.resources;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.rest.dto.ApplicationDTO;
import ch.heigvd.amt.wp1.services.dao.ApplicationsDAOLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/applications")
public class ApplicationsRessource {

    @Context
    UriInfo uriInfo;

    @EJB
    private ApplicationsDAOLocal applicationsDAO;

    @GET
    @Produces("application/json")
    public List<ApplicationDTO> getApplications() {
        List<ApplicationDTO> result = new ArrayList<>();

        List<Application> applications = applicationsDAO.findAll();

        for (Application application : applications) {
            ApplicationDTO dto = new ApplicationDTO();
            populateDTOFromEntity(application, dto);
            result.add(dto);
        }

        return result;
    }

    private void populateDTOFromEntity(Application application, ApplicationDTO dto) {
        dto.setCreatedDate(application.getCreatedDate());
        dto.setName(application.getName());
        dto.setDescription(application.getDescription());
        dto.setApiKey(application.getApiKey());
        dto.setApiSecret(application.getApiSecret());
    }
}
