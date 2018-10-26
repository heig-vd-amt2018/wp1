package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.User;

import javax.ejb.Local;

@Local
public interface UsersDAOLocal extends IGenericDAO<User, Long> {

    User findByName(String name) throws BusinessDomainEntityNotFoundException;
}
