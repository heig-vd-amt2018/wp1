package ch.heigvd.amt.wp1.rest.dto;

public class AdministratorDTO extends UserDTO {
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
}
