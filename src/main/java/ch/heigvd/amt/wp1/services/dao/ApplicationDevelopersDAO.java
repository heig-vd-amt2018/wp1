package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

@Stateless
public class ApplicationDevelopersDAO extends GenericDAO<ApplicationDeveloper, Long> implements ApplicationDevelopersDAOLocal {
    @Override
    public ApplicationDeveloper findByFirstName(String firstName) throws BusinessDomainEntityNotFoundException {
        ApplicationDeveloper result = null;

        try {
            result = (ApplicationDeveloper) em.createNamedQuery("ApplicationDeveloper.findByFirstName").setParameter("firstName", firstName).getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }

    @Override
    public ApplicationDeveloper findByLastName(String lastName) throws BusinessDomainEntityNotFoundException {
        ApplicationDeveloper result = null;

        try {
            result = (ApplicationDeveloper) em.createNamedQuery("ApplicationDeveloper.findByLastName").setParameter("lastName", lastName).getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }

    @Override
    public ApplicationDeveloper findByEmail(String email) throws BusinessDomainEntityNotFoundException {
        ApplicationDeveloper result = null;

        try {
            result = (ApplicationDeveloper) em.createNamedQuery("ApplicationDeveloper.findByEmail").setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }
}
