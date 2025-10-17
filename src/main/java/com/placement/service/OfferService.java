package com.placement.service;

import com.placement.dao.OfferDAO;
import com.placement.model.Offer;
import java.util.List;

public class OfferService {
    private OfferDAO offerDAO = new OfferDAO();

    public void addOffer(Offer offer) {
        offerDAO.saveOffer(offer);
    }

    public Offer getOfferById(String id) {
        return offerDAO.findById(id);
    }

    public List<Offer> getAllOffers() {
        return offerDAO.findAll();
    }

    public void updateOffer(Offer offer) {
        offerDAO.saveOffer(offer);
    }


}
