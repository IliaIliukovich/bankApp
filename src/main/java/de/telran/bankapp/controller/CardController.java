package de.telran.bankapp.controller;

import de.telran.bankapp.dto.CardCreateDto;
import de.telran.bankapp.entity.enums.CardType;
import de.telran.bankapp.service.CardServices;
import de.telran.bankapp.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardServices service;

    @Autowired
    public CardController(CardServices service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Card>> findAll() {
        List<Card> cards = service.findAll();
        if (!cards.isEmpty()) {
            return new ResponseEntity<>(cards, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> findById(@PathVariable String id) {
        Optional<Card> card = service.findById(id);
        if (card.isPresent()) {
            return new ResponseEntity<>(card.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/number")
    public ResponseEntity<Card> findByNumber(@RequestParam String number) {
        Optional<Card> card = service.findByNumber(number);
        if (card.isPresent()) {
            return new ResponseEntity<>(card.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/name")
    public ResponseEntity<Card> findByName(@RequestParam String name) {
        Optional<Card> card = service.findByName(name);
        if (card.isPresent()) {
            return new ResponseEntity<>(card.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/type")
    public ResponseEntity<List<Card>> findByType(@RequestParam CardType type) {
        List<Card> cards = service.findByType(type);
        if (!cards.isEmpty()) {
            return new ResponseEntity<>(cards, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add")
    public ResponseEntity<Card> addCard(@RequestBody Card card) {
        Card created = service.addCard(card);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/new")
    public ResponseEntity<Card> newCard(@RequestBody CardCreateDto cardCreateDto) {
        Card created = service.newCard(cardCreateDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Card> updateCard(@RequestBody Card card) {
        Optional<Card> updated = service.updateCard(card);
        if(updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/changeType")
    public ResponseEntity<Void> changeType(@RequestParam String id, @RequestParam CardType type) {
        Integer changed = service.changeType(id, type);
        if(!changed.equals(0)) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCard(@RequestParam String id) {
        service.deleteCard(id);
        return ResponseEntity.accepted().build();
    }
}
