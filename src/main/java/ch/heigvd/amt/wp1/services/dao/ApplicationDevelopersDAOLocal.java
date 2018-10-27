package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;

import javax.ejb.Local;

@Local
public interface ApplicationDevelopersDAOLocal extends IGenericDAO<ApplicationDeveloper, Long> {
    ApplicationDeveloper findByFirstName(String firstName) throws BusinessDomainEntityNotFoundException;

    ApplicationDeveloper findByLastName(String lastName) throws BusinessDomainEntityNotFoundException;

    ApplicationDeveloper findByEmail(String email) throws BusinessDomainEntityNotFoundException;
}
