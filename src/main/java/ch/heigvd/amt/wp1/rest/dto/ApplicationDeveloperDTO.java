package ch.heigvd.amt.wp1.rest.dto;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;
import ch.heigvd.amt.wp1.model.entities.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ApplicationDeveloperDTO extends UserDTO {

    private List<ApplicationDTO> ownedApplications = new LinkedList<>();

    public ApplicationDeveloperDTO() {}

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

    @Override
    public void fromEntity(User user) {
        this.setId(user.getId());
        this.setEmail(user.getEmail());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setRole(convertRole((user.getRole())));
        this.setState(convertState(user.getState()));
        this.setOwnedApplications(appToAppDTO(((ApplicationDeveloper)user).getOwnedApplications()));
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
}
