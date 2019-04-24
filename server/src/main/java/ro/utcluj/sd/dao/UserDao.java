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

import java.util.Optional;
import org.hibernate.Session;
import ro.utcluj.sd.entities.User;

public class UserDao extends AbstractDao<User> {

    public UserDao() {
        super(User.class);
    }

    public Optional<User> findByUsername(String username) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
        Optional<User> user = currentSession.bySimpleNaturalId(User.class).loadOptional(username);
        currentSession.getTransaction().commit();
        return user;
    }
}
