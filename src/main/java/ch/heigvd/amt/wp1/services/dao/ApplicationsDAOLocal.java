package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ApplicationsDAOLocal extends IGenericDAO<Application, Long> {

    Application findByIdByDeveloper(Long id, ApplicationDeveloper applicationDeveloper) throws BusinessDomainEntityNotFoundException;

    Application findByNameByDeveloper(String name, ApplicationDeveloper applicationDeveloper) throws BusinessDomainEntityNotFoundException;

    List<Application> findAllByDeveloper(ApplicationDeveloper applicationDeveloper, int length, int start) throws BusinessDomainEntityNotFoundException;
}
