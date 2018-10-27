package ch.heigvd.amt.wp1.model.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "administrator")
@NamedQueries({
        @NamedQuery(name = "Administrator.findByFirstName", query = "SELECT a FROM Administrator a WHERE a.firstName = :firstName"),
        @NamedQuery(name = "Administrator.findByLastName", query = "SELECT a FROM Administrator a WHERE a.lastName = :lastName"),
        @NamedQuery(name = "Administrator.findByEmail", query = "SELECT a FROM Administrator a WHERE a.email = :email"),
})
public class Administrator extends User {

    public Administrator() {
        super();
    }

    public Administrator(
            String firstName,
            String lastName,
            String email,
            String password,
            State state
    ) {
        super(firstName, lastName, email, password, Role.ADMINISTRATOR, state);
    }
}
