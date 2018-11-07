package ch.heigvd.amt.wp1.services.dao;

import ch.heigvd.amt.wp1.model.entities.AbstractDomainModelEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDAO<T extends AbstractDomainModelEntity<PK>, PK> implements IGenericDAO<T, PK> {

    @PersistenceContext
    EntityManager em;

    private final Class<T> jpaEntityClass;

    public GenericDAO() {
        this.jpaEntityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public PK create(T t) {
        T managedEntity = createAndReturnManagedEntity(t);
        PK id = managedEntity.getId();
        return id;
    }

    @Override
    public T createAndReturnManagedEntity(T t) {
        em.persist(t);
        em.flush();
        return t;
    }

    @Override
    public void update(T t) throws BusinessDomainEntityNotFoundException {
        findById(t.getId());
        em.merge(t);
    }

    @Override
    public void delete(T t) throws BusinessDomainEntityNotFoundException {
        if (!em.contains(t)) {
            t = findById(t.getId());
        }
        em.remove(t);
    }

    @Override
    public long count() {
        return (long) em.createQuery("Select count(t) from " + jpaEntityClass.getSimpleName() + " t").getSingleResult();
    }

    @Override
    public T findById(PK id) throws BusinessDomainEntityNotFoundException {
        T result = em.find(jpaEntityClass, id);
        if (result == null) {
            throw new BusinessDomainEntityNotFoundException("Entity with id " + id + " not found");
        }
        return em.find(jpaEntityClass, id);
    }

    /*
    @Override
    public List<T> findAll(int length, int start) throws BusinessDomainEntityNotFoundException {
        return em.createNamedQuery("Select t from " + jpaEntityClass.getSimpleName() + " t")
                    .getResultList();
    }
    */
}
