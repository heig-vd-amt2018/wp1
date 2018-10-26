package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.Application;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ApplicationsDAOLocal extends IGenericDAO<Application, Long> {

    Application findByName(String name) throws BusinessDomainEntityNotFoundException;
}
