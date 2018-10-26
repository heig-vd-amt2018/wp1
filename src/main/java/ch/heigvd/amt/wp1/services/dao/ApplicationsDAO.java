package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.Application;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

@Stateless
public class ApplicationsDAO extends GenericDAO<Application, Long> implements ApplicationsDAOLocal {

    @Override
    public Application findByName(String name) throws BusinessDomainEntityNotFoundException {
        Application result = null;

        try {
            result = (Application) em.createNamedQuery("ApplicationDTO.findByName").setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessDomainEntityNotFoundException();
        }

        return result;
    }
}
