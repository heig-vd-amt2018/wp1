package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ApplicationsDAOLocal extends IGenericDAO<Application, Long> {

    Application findByIdByDeveloper(Long id, User user) throws BusinessDomainEntityNotFoundException;

    Application findByNameByDeveloper(String name, User user) throws BusinessDomainEntityNotFoundException;

    List<Application> findAllByUser(User user, int length, int start) throws BusinessDomainEntityNotFoundException;

    long countByUser(User user) throws BusinessDomainEntityNotFoundException;
}
