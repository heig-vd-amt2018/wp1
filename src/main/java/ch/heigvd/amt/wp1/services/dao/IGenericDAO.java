package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.AbstractDomainModelEntity;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
@Local
public interface IGenericDAO<T extends AbstractDomainModelEntity, PK> {

    public PK create(T t);

    public T createAndReturnManagedEntity(T t);

    public void update(T t) throws BusinessDomainEntityNotFoundException;

    public void delete(T t) throws BusinessDomainEntityNotFoundException;

    public long count();

    public T findById(PK id) throws BusinessDomainEntityNotFoundException;

    public List<T> findAll() throws BusinessDomainEntityNotFoundException;

    public List<T> findAll(int length, int start) throws BusinessDomainEntityNotFoundException;

}
