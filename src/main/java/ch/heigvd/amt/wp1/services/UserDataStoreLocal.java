package ch.heigvd.amt.wp1.services;

import ch.heigvd.amt.wp1.model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserDataStoreLocal {

    List<User> getAllUser();

    List<User> getAllAdmin();

    List<User> getAllAppDev();

    User getUserByID(int id);

    Boolean addUser(User user);

    Boolean deleteUser(int id);

    Boolean editUser(int id, User user);
}
