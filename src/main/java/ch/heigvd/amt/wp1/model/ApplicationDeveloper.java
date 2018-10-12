package ch.heigvd.amt.wp1.model;

import java.util.LinkedList;
import java.util.List;

public class ApplicationDeveloper {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final Adress adress;
    private final String email;
    private final String passwordHash;
    private final List<Application> ownedApp;

    public ApplicationDeveloper(int id, String firstName, String lastName, Adress adress, String email, String passwordHash) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.email = email;
        this.passwordHash = passwordHash;
        this.ownedApp = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Adress getAdress() {
        return adress;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public List<Application> getOwnedApp() {
        return ownedApp;
    }
}
