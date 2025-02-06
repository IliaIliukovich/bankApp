package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Card;
import de.telran.bankapp.entity.enums.CardType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Repository
public class CardRepository {

    List<Card> cards = new ArrayList<>();

    public CardRepository(){
        cards.add(new Card(UUID.randomUUID().toString(), CardType.MASTERCARD, "4411 0522 7708 5132", "John Smith", 001, "01/27", 1001L));
        cards.add(new Card(UUID.randomUUID().toString(), CardType.VISA, "1415 1532 7818 4133", "Emily Johnson", 002, "03/29", 1002L));
        cards.add(new Card(UUID.randomUUID().toString(), CardType.VISA, "2425 3542 9817 3234", "Michael Brown", 201, "12/30", 1003L));
        cards.add(new Card(UUID.randomUUID().toString(), CardType.MASTERCARD, "4326 1532 0814 2244", "Sarah Davis", 111, "03/28", 1004L));
        cards.add(new Card(UUID.randomUUID().toString(), CardType.MASTERCARD, "3215 2418 1439 5083", "David Martinez", 015, "05/31", 1005L));
        cards.add(new Card(UUID.randomUUID().toString(), CardType.VISA, "9734 7382 9341 3850", "Sophia Wilson", 321, "04/27", 1006L));
    }

    public List<Card> findAll() {
        return cards;
    }

    public Card findByNumber(String number) {
        return cards.stream().filter(c -> c.getCardNumber()
                .equals(number)).findAny().orElse(null);
    }

    public List<Card> findByName(String name) {
        return cards.stream().filter(client -> client
                .getCardHolder().equals(name)).toList();
    }

    public Card addCard(Card card) {
        card.setId(UUID.randomUUID().toString());
        cards.add(card);
        return card;
    }

    public void deleteCard(String id) {
        cards.removeIf(c -> c.getId().equals(id));
    }

}

