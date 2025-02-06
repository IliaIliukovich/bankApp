package de.telran.bankapp.controller;

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
@RequestMapping("/card")
public class CardController {

    private CardServices cardServices;

    @Autowired
    public CardController(CardServices cardServices) {
        this.cardServices = cardServices;
    }

    @GetMapping("/all")
    public List<Card> findAll() {
        return cardServices.findAll();
    }

    @GetMapping("{number}")
    public Optional<Card> findByNumber(@PathVariable String number) {
        return Optional.ofNullable(cardServices.findByNumber(number));
    }

    @GetMapping("/search")
    public List<Card> findByName(@RequestParam String name) {
        return cardServices.findByName(name);
    }

    @PostMapping("/add")
    public ResponseEntity<Card> addCard(@RequestBody Card card) {
        Card created = cardServices.addCard(card);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Card> updateCard(@RequestBody Card card) {
        Optional<Card> updated = cardServices.updateCard(card);
        if(updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/updateType")
    public ResponseEntity<Card> updateCardType(@RequestParam String id, @RequestParam CardType type) {
        Optional<Card> updated = cardServices.updateCardType(id, type);
        if(updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable String id) {
        cardServices.deleteCard(id);
        return ResponseEntity.accepted().build();
    }
}
