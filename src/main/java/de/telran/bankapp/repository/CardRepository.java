package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Card;
import de.telran.bankapp.entity.enums.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CardRepository extends JpaRepository<Card, String> {

    Card findByCardNumber(String cardNumber);

    Card findByCardHolder(String cardHolder);

    List<Card> findCardByCardType(CardType type);
}
