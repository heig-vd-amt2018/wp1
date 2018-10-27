package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.Administrator;

import javax.ejb.Local;

@Local
public interface AdministratorsDAOLocal extends IGenericDAO<Administrator, Long> {
    Administrator findByFirstName(String firstName) throws BusinessDomainEntityNotFoundException;

    Administrator findByLastName(String lastName) throws BusinessDomainEntityNotFoundException;

    Administrator findByEmail(String email) throws BusinessDomainEntityNotFoundException;
}
