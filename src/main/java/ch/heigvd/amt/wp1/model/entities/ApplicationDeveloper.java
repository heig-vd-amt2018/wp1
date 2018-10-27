package ch.heigvd.amt.wp1.model.entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "application_developer")
@NamedQueries({
        @NamedQuery(name = "ApplicationDeveloper.findByFirstName", query = "SELECT a FROM ApplicationDeveloper a WHERE a.firstName = :firstName"),
        @NamedQuery(name = "ApplicationDeveloper.findByLastName", query = "SELECT a FROM ApplicationDeveloper a WHERE a.lastName = :lastName"),
        @NamedQuery(name = "ApplicationDeveloper.findByEmail", query = "SELECT a FROM ApplicationDeveloper a WHERE a.email = :email"),
        @NamedQuery(name = "ApplicationDeveloper.findAllOwnedApplications", query = "SELECT a FROM Application a WHERE a.applicationDeveloper = :application_developer_id"),
})
public class ApplicationDeveloper extends User {

    @OneToMany(
            mappedBy = "applicationDeveloper",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
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
