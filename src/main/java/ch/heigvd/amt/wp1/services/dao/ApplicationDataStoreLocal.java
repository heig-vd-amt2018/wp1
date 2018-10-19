package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.Application;

import javax.ejb.Local;
import java.util.List;
import java.util.UUID;

@Local
public interface ApplicationDataStoreLocal {

    List<Application> getAllApp();

    Application getApp(int id);

    boolean removeApp(int id);

    boolean editApp(int id, Application app);

    boolean addApp(Application app);
}
