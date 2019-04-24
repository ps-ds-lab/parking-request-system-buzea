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

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ro.utcluj.sd.entities.Request;
import ro.utcluj.sd.util.HibernateUtil;

public class RequestDao implements AutoCloseable
{
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public List<Request> findByParkingLotId(long parkingLotId)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
        List<Request> result = currentSession.createQuery("FROM Request r "
            + " join fetch r.parkingLots lot"
            + " where lot.id = :parkingLotId"
            + " order by r.submissionTime", Request.class)
            .setParameter("parkingLotId", parkingLotId)
            .list();
        currentSession.getTransaction().commit();
        return result;
    }

    public void save(Request request)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
        currentSession.saveOrUpdate(request);
        currentSession.getTransaction().commit();
    }

    @Override
    public void close()
    {
        sessionFactory.getCurrentSession().close();
        sessionFactory.close();
    }
}
