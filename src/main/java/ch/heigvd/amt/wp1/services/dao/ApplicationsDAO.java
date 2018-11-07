package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.User;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.List;

@Stateless
public class ApplicationsDAO extends GenericDAO<Application, Long> implements ApplicationsDAOLocal {

    @Override
    public Application findByIdByDeveloper(Long id, User user) throws BusinessDomainEntityNotFoundException {
        Application result = null;

        try {
            result = (Application) em
                    .createNamedQuery("Application.findByIdByUser")
                    .setParameter("id", id)
                    .setParameter("owner", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }

    @Override
    public Application findByNameByDeveloper(String name, User user) throws BusinessDomainEntityNotFoundException {
        Application result = null;

        try {
            result = (Application) em
                    .createNamedQuery("Application.findByNameByUser")
                    .setParameter("name", name)
                    .setParameter("owner", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }

    @Override
    public List<Application> findAllByUser(User user, int length, int start) throws BusinessDomainEntityNotFoundException {
        List<Application> result = null;

        try {
            result = (List<Application>) em
                    .createNamedQuery("Application.findAllByUser")
                    .setParameter("owner", user)
                    .setMaxResults(length)
                    .setFirstResult(start).getResultList();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }
}
