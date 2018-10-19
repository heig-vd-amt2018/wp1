package ch.heigvd.amt.wp1.model;

import java.util.LinkedList;
import java.util.List;

public class ApplicationDeveloper extends User{

    private List<Application> ownedApp;

    public ApplicationDeveloper(int id, String firstName, String lastName, Adress adress, String email, String passwordHash, Role role, List<Application> ownedApp) {
        super(id, firstName, lastName, adress, email, passwordHash, role);
        this.ownedApp = ownedApp;
    }

    public List<Application> getOwnedApp() {
        return ownedApp;
    }

    public void setOwnedApp(List<Application> ownedApp) {
        this.ownedApp = ownedApp;
    }
}
