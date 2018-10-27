package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.Administrator;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

@Stateless
public class AdministratorsDAO extends GenericDAO<Administrator, Long> implements AdministratorsDAOLocal {
    @Override
    public Administrator findByFirstName(String firstName) throws BusinessDomainEntityNotFoundException {
        Administrator result = null;

        try {
            result = (Administrator) em.createNamedQuery("Administrator.findByFirstName").setParameter("firstName", firstName).getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }

    @Override
    public Administrator findByLastName(String lastName) throws BusinessDomainEntityNotFoundException {
        Administrator result = null;

        try {
            result = (Administrator) em.createNamedQuery("Administrator.findByLastName").setParameter("lastName", lastName).getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }

    @Override
    public Administrator findByEmail(String email) throws BusinessDomainEntityNotFoundException {
        Administrator result = null;

        try {
            result = (Administrator) em.createNamedQuery("Administrator.findByEmail").setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }
}
