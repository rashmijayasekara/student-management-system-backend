package lk.ijse.dep9.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUtilTest {
    @Test
    void getSessionFactory(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        SessionFactory sessionFactory1 = HibernateUtil.getSessionFactory();

        assertEquals(sessionFactory1,sessionFactory);
        Session session = sessionFactory.openSession();
        assertNotNull(session);
        session.close();

        sessionFactory1.close();
        sessionFactory.close();
    }

}