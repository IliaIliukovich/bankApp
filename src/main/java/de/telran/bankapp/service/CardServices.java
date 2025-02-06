package de.telran.bankapp.service;


import de.telran.bankapp.entity.enums.CardType;
import de.telran.bankapp.entity.Card;
import de.telran.bankapp.repository.CardRepository;
import de.telran.bankapp.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class CardServices {

    private CardRepository cardRepository;

    @Autowired
    public CardServices(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    public List<Card> findByName(String name) {
        return cardRepository.findByName(name);
    }

    public Card addCard(Card card) {
        return cardRepository.addCard(card);
    }

    public Optional<Card> updateCard(Card card) {
        String number = card.getCardNumber();
        Optional<Card> optional = Optional.ofNullable(cardRepository.findByNumber(number));
        if(optional.isPresent()) {
            Card found = optional.get();
            found.setCardHolder(card.getCardHolder());
            found.setCardType(card.getCardType());
            found.setCardNumber(card.getCardNumber());
            found.setCvv(card.getCvv());
            found.setExpiryDate(card.getExpiryDate());
            found.setAccountId(card.getAccountId());
            return optional.of(found);
        }
        return optional.empty();
    }

    public Optional<Card> updateCardType(String number, CardType type) {
        Optional<Card> optional = Optional.ofNullable(cardRepository.findByNumber(number));
        if(optional.isPresent()) {
            Card card = optional.get();
            card.setCardType(CardType.valueOf(String.valueOf(type)));
            return Optional.of(card);
        }
        return Optional.empty();
    }

    public void deleteCard(String number) {
        cardRepository.deleteCard(number);
    }

}

