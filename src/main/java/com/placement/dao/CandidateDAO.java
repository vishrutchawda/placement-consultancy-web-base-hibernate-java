package com.placement.dao;

import com.placement.model.Candidate;
import com.placement.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class CandidateDAO {


    public Candidate findByUserId(String userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Candidate> query = session.createQuery("from Candidate where userId = :userId", Candidate.class);
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveCandidate(Candidate candidate) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(candidate);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Candidate findById(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Candidate.class, id);
        }
    }

    public List<Candidate> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Candidate> query = session.createQuery("from Candidate", Candidate.class);
            return query.list();
        }
    }


}
