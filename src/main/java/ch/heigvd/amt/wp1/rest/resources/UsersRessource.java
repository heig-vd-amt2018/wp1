package ch.heigvd.amt.wp1.rest.resources;


import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.rest.dto.DataTables.DataTablesDTO;
import ch.heigvd.amt.wp1.rest.dto.DataTables.UserDataTablesDTO;
import ch.heigvd.amt.wp1.rest.dto.UserDTO;
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
@Path("/users")
public class UsersRessource {

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpServletRequest request;

    @EJB
    private UsersDAOLocal usersDAO;

    @GET
    @Produces("application/json")
    public DataTablesDTO getUsers(@QueryParam("length") int length, @QueryParam("start") int start, @QueryParam("draw") int draw) {

        List<UserDTO> data = new ArrayList<>();

        List<User> users = null;
        try {
            users = usersDAO.findAll(length, start);

            for (User user: users) {
                UserDTO dto = new UserDTO();
                dto.fromEntity(user);
                data.add(dto);
            }
        } catch (BusinessDomainEntityNotFoundException e) {
            // Continue
        }

        long recordsTotal = usersDAO.count();

        DataTablesDTO result = new UserDataTablesDTO(draw, recordsTotal, data);

        return result;
    }
}
