package de.telran.bankapp.entity;

import de.telran.bankapp.entity.enums.CardType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Card {

    private String id;
    private CardType cardType;
    private String cardNumber;
    private String cardHolder;
    private Integer cvv;
    //private LocalDateTime expirationDate; //Replaced with String to simplify data entry.
    private String expiryDate;
    private Long accountId;

}