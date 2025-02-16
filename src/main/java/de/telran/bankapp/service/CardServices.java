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

    private final CardRepository repository;

    @Autowired
    public CardServices(CardRepository repository) {
        this.repository = repository;
    }

    public List<Card> findAll() {
        return repository.findAll();
    }

    public Optional<Card> findById(String id) {
        return repository.findById(id);
    }

    public Optional<Card> findByNumber(String number) {
        return Optional.ofNullable(repository.findByCardNumber(number));
    }

    public Optional<Card> findByName(String name) {
        return Optional.ofNullable(repository.findByCardHolder(name));
    }

    public List<Card> findByType(CardType type) {
        return repository.findCardByCardType(type);
    }

    public Card addCard(Card card) {
        return repository.save(card);
    }

    public Optional<Card> updateCard(Card card) {
        String id = card.getId();
        Optional<Card> optional = repository.findById(id);
        if(optional.isPresent()) {
            Card found = repository.save(card);
            return Optional.of(found);
        }
        return Optional.empty();
    }

    public Optional<Card> changeCardType(String id, String type) {
        Optional<Card> optional = repository.findById(id);
        if(optional.isPresent()) {
            Card card = optional.get();
            CardType cardType = type == null? CardType.VISA : CardType.valueOf(type);
            card.setCardType(cardType);
            Card saved = repository.save(card);
            return Optional.of(saved);
        }
        return Optional.empty();
    }

    public void deleteCard(String id) {
        repository.deleteById(id);
    }
}

