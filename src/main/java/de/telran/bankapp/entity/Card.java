package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.CardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    private String cardNumber;
    private String cardHolder;
    private Integer cvv;
    private String expiryDate;

    @OneToOne
    private Account account;
}