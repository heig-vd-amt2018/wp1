package ch.heigvd.amt.wp1.rest.resources;


import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;
import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.rest.dto.ApplicationDTO;
import ch.heigvd.amt.wp1.rest.dto.ApplicationDeveloperDTO;
import ch.heigvd.amt.wp1.rest.dto.DataTables.ApplicationDeveloperDataTablesDTO;
import ch.heigvd.amt.wp1.rest.dto.DataTables.DataTablesDTO;
import ch.heigvd.amt.wp1.rest.dto.UserDTO;
import ch.heigvd.amt.wp1.services.dao.ApplicationDevelopersDAOLocal;
import ch.heigvd.amt.wp1.services.dao.BusinessDomainEntityNotFoundException;

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
@Path("/users")
public class UsersRessource {

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpServletRequest request;

    @EJB
    private ApplicationDevelopersDAOLocal appDevsDAO;

    @GET
    @Produces("application/json")
    public DataTablesDTO getUsers(@QueryParam("length") int length, @QueryParam("start") int start, @QueryParam("draw") int draw) {

        List<ApplicationDeveloperDTO> data = new ArrayList<>();

        List<ApplicationDeveloper> devs = null;

        devs = appDevsDAO.findAll();

        for (ApplicationDeveloper dev : devs) {
            ApplicationDeveloperDTO dto = new ApplicationDeveloperDTO();
            populateDTOFromEntity(dev, dto);
            data.add(dto);
        }

        long recordsTotal = appDevsDAO.count();

        DataTablesDTO result = new ApplicationDeveloperDataTablesDTO(draw, recordsTotal, data);

        return result;
    }

    private void populateDTOFromEntity(ApplicationDeveloper dev, ApplicationDeveloperDTO dto) {
        dto.setId(dev.getId());
        dto.setOwnedApplications(appToAppDTO(dev.getOwnedApplications()));
        dto.setEmail(dev.getEmail());
        dto.setFirstName(dev.getFirstName());
        dto.setLastName(dev.getLastName());
        dto.setRole(convertRole((dev.getRole())));
        dto.setState(convertState(dev.getState()));
    }

    private List<ApplicationDTO> appToAppDTO(List<Application> appList) {
        List<ApplicationDTO> result = new ArrayList<>();

        for (Application app : appList) {
            ApplicationDTO dto = new ApplicationDTO();
            dto.setId(app.getId());
            dto.setCreatedDate(app.getCreatedDate());
            dto.setName(app.getName());
            dto.setDescription(app.getDescription());
            dto.setApiKey(app.getApiKey());
            dto.setApiSecret(app.getApiSecret());

            result.add(dto);
        }

        return result;
    }

    private UserDTO.Role convertRole(User.Role uRole) {
        if (uRole == User.Role.ADMINISTRATOR) {
            return UserDTO.Role.ADMINISTRATOR;
        } else {
            return UserDTO.Role.APPLICATION_DEVELOPER;
        }
    }

    private UserDTO.State convertState(User.State uState) {
        if (uState == User.State.DISABLED) {
            return UserDTO.State.DISABLED;
        } else if (uState == User.State.ENABLED) {
            return UserDTO.State.ENABLED;
        } else {
            return UserDTO.State.RESET;
        }
    }
}
