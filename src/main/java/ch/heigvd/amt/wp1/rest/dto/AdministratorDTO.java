package ch.heigvd.amt.wp1.rest.dto;

import ch.heigvd.amt.wp1.model.entities.Administrator;
import ch.heigvd.amt.wp1.model.entities.User;

public class AdministratorDTO extends UserDTO {

    public AdministratorDTO(){}

    public AdministratorDTO(
            int id,
            String firstName,
            String lastName,
            String email,
            String password,
            State state
    ) {
        super(firstName, lastName, email, password, Role.ADMINISTRATOR, state);
    }

    @Override
    public void fromEntity(User user) {
        this.setId(user.getId());
        this.setEmail(user.getEmail());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setRole(convertRole((user.getRole())));
        this.setState(convertState(user.getState()));
    }
}
