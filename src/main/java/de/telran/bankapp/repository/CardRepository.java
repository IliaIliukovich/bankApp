package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Card;
import de.telran.bankapp.entity.enums.CardType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CardRepository extends JpaRepository<Card, String> {

    Card findByCardNumber(String cardNumber);

    Card findByCardHolder(String cardHolder);

    List<Card> findCardByCardType(CardType type);

    @Query("update Card c set c.cardType = :type where c.id = :id")
    @Modifying
    @Transactional
    int changeType(String id, CardType type);
}
