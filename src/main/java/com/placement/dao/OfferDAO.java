package com.placement.dao;

import com.placement.model.Offer;
import com.placement.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class OfferDAO {

    public void saveOffer(Offer offer) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(offer);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Offer findById(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Offer.class, id);
        }
    }

    public List<Offer> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Offer> query = session.createQuery("from Offer", Offer.class);
            return query.list();
        }
    }


}
