package ch.heigvd.amt.wp1.model;

public class Admin extends User{
    public Admin(int id, String firstName, String lastName, Adress adress, String email, String passwordHash, Role role) {
        super(id, firstName, lastName, adress, email, passwordHash, role);
    }
}
