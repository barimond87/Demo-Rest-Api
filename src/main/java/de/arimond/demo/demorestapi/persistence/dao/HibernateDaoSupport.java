package de.arimond.demo.demorestapi.persistence.dao;

import de.arimond.demo.demorestapi.persistence.entity.HibernateModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class HibernateDaoSupport<T extends HibernateModel> implements IGenericDao<T> {

    private static final Logger logger = LoggerFactory.getLogger(HibernateDaoSupport.class);

    @Autowired
    private SessionFactory sessionFactory;

    private final Class<T> domainClass;

    public HibernateDaoSupport(Class<T> domainClass) {
        this.domainClass = domainClass;
    }

    @Override
    public T load(Long id) {
        return getSession().get(domainClass, id);
    }

    @Override
    public void save(T object) {
        getSession().saveOrUpdate(object);
    }

    @Override
    public void update(T object) {
        getSession().update(object);
    }

    @Override
    public void delete(T object) {
        getSession().delete(object);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> loadAll() {
        return getSession().createCriteria(domainClass).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T merge(T object) {
        return (T) getSession().merge(object);
    }

    protected Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            logger.debug("New hibernate session was createtd", e);
            return sessionFactory.openSession();
        }
    }

}