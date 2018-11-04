package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.List;

@Stateless
public class ApplicationsDAO extends GenericDAO<Application, Long> implements ApplicationsDAOLocal {

    @Override
    public Application findByIdByDeveloper(Long id, ApplicationDeveloper applicationDeveloper) throws BusinessDomainEntityNotFoundException {
        Application result = null;

        try {
            result = (Application) em
                    .createNamedQuery("Application.findByIdByDeveloper")
                    .setParameter("id", id)
                    .setParameter("applicationDeveloper", applicationDeveloper)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }

    @Override
    public Application findByNameByDeveloper(String name, ApplicationDeveloper applicationDeveloper) throws BusinessDomainEntityNotFoundException {
        Application result = null;

        try {
            result = (Application) em
                    .createNamedQuery("Application.findByNameByDeveloper")
                    .setParameter("name", name)
                    .setParameter("applicationDeveloper", applicationDeveloper)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }

    @Override
    public List<Application> findAllByDeveloper(ApplicationDeveloper applicationDeveloper, int length, int start) throws BusinessDomainEntityNotFoundException {
        List<Application> result = null;

        try {
            result = (List<Application>) em.createNamedQuery("Application.findAllByDeveloper").setParameter("applicationDeveloper", applicationDeveloper).setMaxResults(length).setFirstResult(start).getResultList();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }
}
