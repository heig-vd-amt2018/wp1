package ch.heigvd.amt.wp1.rest.dto;

import ch.heigvd.amt.wp1.model.entities.User;

public abstract class UserDTO extends AbstractDTO<Long> {

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

    public UserDTO() {

    }

    public UserDTO(
            String firstName,
            String lastName,
            String email,
            String password,
            Role role,
            State state
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.state = state;
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

    public abstract void fromEntity(User user);

    protected UserDTO.Role convertRole(User.Role uRole) {
        if (uRole == User.Role.ADMINISTRATOR) {
            return UserDTO.Role.ADMINISTRATOR;
        } else {
            return UserDTO.Role.APPLICATION_DEVELOPER;
        }
    }

    protected UserDTO.State convertState(User.State uState) {
        if (uState == User.State.DISABLED) {
            return UserDTO.State.DISABLED;
        } else if (uState == User.State.ENABLED) {
            return UserDTO.State.ENABLED;
        } else {
            return UserDTO.State.RESET;
        }
    }
}
