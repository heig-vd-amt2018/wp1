package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UsersDAOLocal extends IGenericDAO<User, Long> {
    User findByFirstName(String firstName) throws BusinessDomainEntityNotFoundException;

    User findByLastName(String lastName) throws BusinessDomainEntityNotFoundException;

    User findByEmail(String email) throws BusinessDomainEntityNotFoundException;
}
