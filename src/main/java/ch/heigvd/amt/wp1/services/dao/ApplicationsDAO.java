package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.List;

@Stateless
public class ApplicationsDAO extends GenericDAO<Application, Long> implements ApplicationsDAOLocal {

    @Override
    public Application findByName(String name, ApplicationDeveloper applicationDeveloper) throws BusinessDomainEntityNotFoundException {
        Application result = null;

        try {
            result = (Application) em
                    .createNamedQuery("Application.findByName")
                    .setParameter("name", name)
                    .setParameter("applicationDeveloper", applicationDeveloper)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }

    @Override
    public List<Application> findAllByDeveloper(ApplicationDeveloper applicationDeveloper) throws BusinessDomainEntityNotFoundException {
        List<Application> result = null;

        try {
            result = (List<Application>) em.createNamedQuery("Application.findAllByDeveloper").setParameter("applicationDeveloper", applicationDeveloper).getResultList();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }
}
