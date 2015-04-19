package com.scheduler.daoImpl;

import com.scheduler.dao.UserDAO;
import com.scheduler.dbmodel.User;
//import org.hibernate.Query;
//import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;

/**
 * Created by vlanderson on 4/13/15.
 */
@Service
public class UserDAOImpl implements UserDAO {


//    @PersistenceContext
//    EntityManager em;

    @Transactional
    public User getUserByCredentials(User user) {
        String queryStr = "Select DISTINCT from User u where u.username=:usr and u.password=:pwd";
//        Session session = em.unwrap(Session.class);
//        Query q = session.createQuery(queryStr);
//        q.setParameter("usr", user.getUsername());
//        q.setParameter("pwd", user.getPassword());
       return null; //(User) q.uniqueResult();
    }
}
