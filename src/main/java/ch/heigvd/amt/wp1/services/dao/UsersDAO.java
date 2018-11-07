package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.User;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.List;

@Stateless
public class UsersDAO extends GenericDAO<User, Long> implements UsersDAOLocal {
    @Override
    public User findByFirstName(String firstName) throws BusinessDomainEntityNotFoundException {
        User result = null;

        try {
            result = (User) em.createNamedQuery("User.findByFirstName").setParameter("firstName", firstName).getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }

    @Override
    public User findByLastName(String lastName) throws BusinessDomainEntityNotFoundException {
        User result = null;

        try {
            result = (User) em.createNamedQuery("User.findByLastName").setParameter("lastName", lastName).getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }

    @Override
    public User findByEmail(String email) throws BusinessDomainEntityNotFoundException {
        User result = null;

        try {
            result = (User) em.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }

    @Override
    public List<User> findAll(int length, int start) throws BusinessDomainEntityNotFoundException {
        List<User> result = null;

        try {
            result = (List<User>) em
                    .createNamedQuery("User.findAll")
                    .setMaxResults(length)
                    .setFirstResult(start).getResultList();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }
}
