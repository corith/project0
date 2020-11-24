package com.revature.project0.service;

import com.revature.project0.dao.DatabaseCardDao;
import com.revature.project0.models.Card;

import java.util.ArrayList;

public class CardService {

    private final DatabaseCardDao cardDao;

    public CardService() {
        this.cardDao = new DatabaseCardDao();
    }

    /**
     * This will return all the cards in the database no matter the owner.
     * It should only be accessible for the admin user...but this may not be
     * implemented currently...
     * @return ArrayList<Card>
     */
    public ArrayList<Card> getAllCards() {
        return cardDao.getAllCards();
    }

    public Card getCard(int id) {
        return cardDao.getCard(id);
    }

    public Card insertCard(Card newCard) {
        return cardDao.insertCard(newCard);
    }

    public void deleteCard(int id) {
        cardDao.deleteCard(id);
    }
}
