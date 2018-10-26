package ch.heigvd.amt.wp1.model.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "application_developer")
public class ApplicationDeveloper extends User {

    @OneToMany
    private List<Application> ownedApplications = new LinkedList<>();

    public ApplicationDeveloper() {
        super();
    }

    public ApplicationDeveloper(
            String firstName,
            String lastName,
            String email,
            String password,
            State state,
            List<Application> ownedApplications
    ) {
        super(firstName, lastName, email, password, Role.APPLICATION_DEVELOPER, state);
        this.ownedApplications = ownedApplications;
    }

    public List<Application> getOwnedApplications() {
        return ownedApplications;
    }

    public void setOwnedApplications(List<Application> ownedApplications) {
        this.ownedApplications = ownedApplications;
    }
}
