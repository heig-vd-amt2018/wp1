package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.User;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

@Stateless
public class UsersDAO extends GenericDAO<User, Long> implements UsersDAOLocal {

    @Override
    public User findByName(String name) throws BusinessDomainEntityNotFoundException {
        User result = null;

        try {
            result = (User) em.createNamedQuery("UserDTO.findByName").setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }
}
