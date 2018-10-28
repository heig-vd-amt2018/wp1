package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ApplicationsDAOLocal extends IGenericDAO<Application, Long> {

    Application findByName(String name, ApplicationDeveloper applicationDeveloper) throws BusinessDomainEntityNotFoundException;

    List<Application> findAllByDeveloper(ApplicationDeveloper applicationDeveloper) throws BusinessDomainEntityNotFoundException;
}
