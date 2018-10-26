package ch.heigvd.amt.wp1.model.entities;

import ch.heigvd.amt.wp1.model.entities.AbstractDomainModelEntity;

import javax.persistence.*;

@MappedSuperclass
public abstract class User extends AbstractDomainModelEntity<Long> {

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
    @Column(name="first_name", nullable = false)
    private String firstName;

    //! Last name of the user.
    @Column(name="last_name", nullable = false)
    private String lastName;

    //! Email of the user.
    @Column(name="email", nullable = false)
    private String email;

    //! Password of the user's account.
    @Column(name="password", nullable = false)
    private String password;

    //! Role of the user's account.
    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    //! State of the user's account.
    @Column(name="state", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    public User() {
        // Only here for JPA
    }

    public User(
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
}
