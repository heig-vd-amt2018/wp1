package ch.heigvd.amt.wp1.services;

import ch.heigvd.amt.wp1.model.Application;
import ch.heigvd.amt.wp1.model.ApplicationDeveloper;

import java.util.LinkedList;
import java.util.List;

public class DeveloperDataStore implements DeveloperDataStoreLocal{

    private final List<ApplicationDeveloper> devs;

    public DeveloperDataStore() {
        this.devs = new LinkedList<>();
    }


    @Override
    public List<ApplicationDeveloper> getAllDevs() {
        return devs;
    }

    @Override
    public ApplicationDeveloper getDevByID(int id) {
        for(ApplicationDeveloper dev : devs){
            if(dev.getId() == id){
                return dev;
            }
        }
        return null;
    }

    @Override
    public ApplicationDeveloper getDevByName(String name) {
        for(ApplicationDeveloper dev : devs){
            if(dev.getLastName().equals(name)){
                return dev;
            }
        }
        return null;
    }

    @Override
    public Boolean addDev(ApplicationDeveloper dev) {
        return devs.add(dev);
    }

    @Override
    public Boolean deleteDev(int id) {
        for(ApplicationDeveloper dev : devs){
            if(dev.getId() == id){
                return devs.remove(dev);
            }
        }
        return false;
    }
}
