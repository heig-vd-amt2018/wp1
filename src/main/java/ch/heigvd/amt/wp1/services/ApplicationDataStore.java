package ch.heigvd.amt.wp1.services;

import ch.heigvd.amt.wp1.model.Application;
import ch.heigvd.amt.wp1.services.dao.ApplicationDataStoreLocal;

import javax.ejb.Singleton;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Singleton
public class ApplicationDataStore implements ApplicationDataStoreLocal {

    private List<Application> database = new LinkedList<>();

    @Override
    public List<Application> getAllApp() {
        return database;
    }

    @Override
    public Application getApp(int id) {
        for(Application app : database){
            if(app.getId() == id)
                return app;
        }
        return null;
    }

    @Override
    public boolean removeApp(int id) {
        for(Application app : database){
            if(app.getId() == id)
                return database.remove(app);
        }
        return false;
    }

    @Override
    public boolean editApp(int id, Application newApp) {
        for(Application app : database){
            if(app.getId() == id) {
                database.remove(app);
                return  database.add(newApp);
            }
        }
        return false;
    }

    @Override
    public boolean addApp(Application app) {
        return database.add(app);
    }
}
