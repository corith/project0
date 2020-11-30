package com.revature.project0.service;

import com.revature.project0.dao.DatabaseCardDao;
import com.revature.project0.models.Card;

import java.util.ArrayList;

public class CardService {

    private final DatabaseCardDao cardDao;

    public CardService() {
        this.cardDao = new DatabaseCardDao();
    }

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

    public void updateCard(Card CardToBeUpdated, Card newFields) {
        cardDao.updateCard(CardToBeUpdated, newFields);
    }
}
