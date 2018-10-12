package ch.heigvd.amt.wp1.services;

import ch.heigvd.amt.wp1.model.Application;
import ch.heigvd.amt.wp1.model.ApplicationDeveloper;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DeveloperDataStoreLocal {

    List<ApplicationDeveloper> getAllDevs();

    ApplicationDeveloper getDevByID(int id);

    ApplicationDeveloper getDevByName(String name);

    Boolean addDev(ApplicationDeveloper dev);

    Boolean deleteDev(int id);

}
