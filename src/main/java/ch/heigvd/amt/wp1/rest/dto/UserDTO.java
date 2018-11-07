package ch.heigvd.amt.wp1.rest.dto;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.User;

import java.util.LinkedList;
import java.util.List;

public class UserDTO extends AbstractDTO<Long> {

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
    private String firstName;

    //! Last name of the user.
    private String lastName;

    //! Email of the user.
    private String email;

    //! Password of the user's account.
    private String password;

    //! Role of the user's account.
    private Role role;

    //! State of the user's account.
    private State state;

    //! User's owned applications.
    private List<Application> ownedApplications = new LinkedList<>();

    public UserDTO() {

    }

    public UserDTO(
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

    public void fromEntity(User user) {

    }
}
