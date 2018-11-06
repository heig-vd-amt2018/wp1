package ch.heigvd.amt.wp1.rest.resources;


import ch.heigvd.amt.wp1.model.entities.Administrator;
import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;
import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.rest.dto.AdministratorDTO;
import ch.heigvd.amt.wp1.rest.dto.ApplicationDTO;
import ch.heigvd.amt.wp1.rest.dto.ApplicationDeveloperDTO;
import ch.heigvd.amt.wp1.rest.dto.DataTables.ApplicationDeveloperDataTablesDTO;
import ch.heigvd.amt.wp1.rest.dto.DataTables.DataTablesDTO;
import ch.heigvd.amt.wp1.rest.dto.DataTables.UserDataTablesDTO;
import ch.heigvd.amt.wp1.rest.dto.UserDTO;
import ch.heigvd.amt.wp1.services.dao.AdministratorsDAOLocal;
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

    @EJB
    private AdministratorsDAOLocal adminDAO;

    @GET
    @Produces("application/json")
    public DataTablesDTO getUsers(@QueryParam("length") int length, @QueryParam("start") int start, @QueryParam("draw") int draw) {

        List<UserDTO> data = new ArrayList<>();

        List<User> users = new ArrayList<>();

        users.addAll(appDevsDAO.findAll());
        users.addAll(adminDAO.findAll());



        for (User u : users) {
            UserDTO dto;
            if(u instanceof ApplicationDeveloper){
                dto = new ApplicationDeveloperDTO();
            }
            else {
                dto = new AdministratorDTO();
            }
            dto.fromEntity(u);
            data.add(dto);
        }

        long recordsTotal = appDevsDAO.count() + adminDAO.count();

        DataTablesDTO result = new UserDataTablesDTO(draw, recordsTotal, data);

        return result;
    }
}
