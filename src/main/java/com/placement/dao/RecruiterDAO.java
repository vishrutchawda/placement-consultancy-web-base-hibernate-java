package com.placement.dao;

import com.placement.model.Recruiter;
import com.placement.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.*;

public class RecruiterDAO {

    public void saveRecruiter(Recruiter recruiter) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(recruiter);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Recruiter> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Recruiter> query = session.createQuery("from Recruiter", Recruiter.class);
            List<Recruiter> recruiters = query.list();
            return recruiters;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Recruiter findById(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Recruiter.class, id);
        }
    }

    public Recruiter findByUserId(String userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Recruiter> query = session.createQuery(
                "FROM Recruiter WHERE userId = :userId", Recruiter.class);
            query.setParameter("userId", userId);
            Recruiter recruiter = query.uniqueResult();
            return recruiter;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
