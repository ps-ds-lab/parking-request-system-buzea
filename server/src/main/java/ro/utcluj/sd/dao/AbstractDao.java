/*************************************************************************
 * ULLINK CONFIDENTIAL INFORMATION
 * _______________________________
 *
 * All Rights Reserved.
 *
 * NOTICE: This file and its content are the property of Ullink. The
 * information included has been classified as Confidential and may
 * not be copied, modified, distributed, or otherwise disseminated, in
 * whole or part, without the express written permission of Ullink.
 ************************************************************************/
package ro.utcluj.sd.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ro.utcluj.sd.dao.util.HibernateUtil;

public abstract class AbstractDao<T> implements AutoCloseable {
    protected static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Class clazz;

    protected AbstractDao(Class clazz) {
        this.clazz = clazz;
    }

    public void save(T obj) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
        currentSession.saveOrUpdate(obj);
        currentSession.getTransaction().commit();
    }

    public T findById(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
        Object value = currentSession.get(clazz, id);
        currentSession.getTransaction().commit();
        return (T) value;
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}
