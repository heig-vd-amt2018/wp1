package ch.heigvd.amt.wp1.model.entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
        @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
        @NamedQuery(name = "User.findAllOwnedApplications", query = "SELECT a FROM Application a WHERE a.owner = :owner"),
})
public class User extends AbstractDomainModelEntity<Long> {

    public enum Role {
        ADMINISTRATOR,
        APPLICATION_DEVELOPER
    }

    public enum State {
        ENABLED,
        DISABLED,
        RESET
    }

    //! First name of the user.
    @Column(name = "first_name", nullable = false)
    private String firstName;

    //! Last name of the user.
    @Column(name = "last_name", nullable = false)
    private String lastName;

    //! Email of the user.
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    //! Password of the user's account.
    @Column(name = "password", nullable = false)
    private String password;

    //! Role of the user's account.
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    //! State of the user's account.
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    //! User's owned applications.
    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Application> ownedApplications = new LinkedList<>();

    public User() {
        // Only here for JPA
    }

    public User(
            String firstName,
            String lastName,
            String email,
            String password,
            Role role,
            State state,
            List<Application> ownedApplications
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.state = state;
        this.ownedApplications = ownedApplications;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Application> getOwnedApplications() {
        return ownedApplications;
    }

    public void setOwnedApplications(List<Application> ownedApplications) {
        this.ownedApplications = ownedApplications;
    }

    public void addOwnedApplication(Application application) {
        ownedApplications.add(application);
    }
}
