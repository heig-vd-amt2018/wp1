package ch.heigvd.amt.wp1.rest.dto;

import java.util.LinkedList;
import java.util.List;

public class ApplicationDeveloperDTO extends UserDTO {

    private List<ApplicationDTO> ownedApplications = new LinkedList<>();

    public ApplicationDeveloperDTO(
            String firstName,
            String lastName,
            String email,
            String password,
            State state,
            List<ApplicationDTO> ownedApplications
    ) {
        super(firstName, lastName, email, password, Role.APPLICATION_DEVELOPER, state);
        this.ownedApplications = ownedApplications;
    }

    public List<ApplicationDTO> getOwnedApplications() {
        return ownedApplications;
    }

    public void setOwnedApplications(List<ApplicationDTO> ownedApplications) {
        this.ownedApplications = ownedApplications;
    }
}
