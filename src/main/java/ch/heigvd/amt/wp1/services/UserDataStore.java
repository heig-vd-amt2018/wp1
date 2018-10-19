package ch.heigvd.amt.wp1.services;

import ch.heigvd.amt.wp1.model.Role;
import ch.heigvd.amt.wp1.model.User;

import javax.ejb.Singleton;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class UserDataStore implements UserDataStoreLocal {

    private List<User> database = new LinkedList<>();

    @Override
    public List<User> getAllUser() {
        return database;
    }

    @Override
    public List<User> getAllAdmin() {
        List<User> tmp = new LinkedList<>();
        for(User u : database){
            if(u.getRole() == Role.Admin){
                tmp.add(u);
            }
        }
        return tmp;
    }

    @Override
    public List<User> getAllAppDev() {
        List<User> tmp = new LinkedList<>();
        for(User u : database){
            if(u.getRole() == Role.AppDev){
                tmp.add(u);
            }
        }
        return tmp;
    }

    @Override
    public User getUserByID(int id) {
        for(User u : database){
            if(u.getId() == id){
                return u;
            }
        }
        return null;
    }

    @Override
    public Boolean addUser(User user) {
        return database.add(user);
    }

    @Override
    public Boolean deleteUser(int id) {
        for(User u : database){
            if(u.getId() == id)
                return database.remove(u);
        }
        return false;
    }

    @Override
    public Boolean editUser(int id, User user) {
        for(User u : database){
            if(u.getId() == id) {
                database.remove(u);
                return database.add(user);
            }
        }
        return false;
    }
}
