package ch.heigvd.amt.wp1.model.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "administrator")
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
