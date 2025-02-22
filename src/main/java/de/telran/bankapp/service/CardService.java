package de.telran.bankapp.service;


import de.telran.bankapp.dto.AccountCreateDto;
import de.telran.bankapp.dto.CardCreateDto;
import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Card;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.enums.AccountType;
import de.telran.bankapp.entity.enums.CardType;
import de.telran.bankapp.exception.BankAppBadRequestException;
import de.telran.bankapp.repository.CardRepository;
import de.telran.bankapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository repository;
    private final AccountService accountService;
    private final ClientRepository clientRepository;

    @Autowired
    public CardService(CardRepository repository, AccountService accountService, ClientRepository clientRepository) {
        this.repository = repository;
        this.accountService = accountService;
        this.clientRepository = clientRepository;
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

    @Transactional
    public Card addCard(Card card) {
        return repository.save(card);
    }

    @Transactional
    public Optional<Card> updateCard(Card card) {
        String id = card.getId();
        Optional<Card> optional = repository.findById(id);
        if(optional.isPresent()) {
            Card found = repository.save(card);
            return Optional.of(found);
        }
        return Optional.empty();
    }

    @Transactional
    public Integer changeType(String id, CardType type) {
           CardType cardType = type == null? CardType.VISA : type;
           return repository.changeType(id, cardType);
    }

    @Transactional
    public void deleteCard(String id) {
        repository.deleteById(id);
    }

    @Transactional
    public Card newCard(CardCreateDto cardCreateDto) {
        String clientId = cardCreateDto.getClientId();
        Optional<Client> clientById = clientRepository.findById(clientId);
        if (clientById.isEmpty()) {
            throw new BankAppBadRequestException("Client with id = " + clientId + " not found");
        }

        AccountCreateDto accountCreateDto = new AccountCreateDto(clientId, 1L, new BigDecimal("0.00"), AccountType.DEBIT_CARD);
        Account newAccount = accountService.createNewAccount(accountCreateDto);

        CardType cardType = CardType.valueOf(cardCreateDto.getCardType());
        Card card = createNewCard(cardType, clientById.get(), newAccount);
        return repository.save(card);
    }

    private static Card createNewCard(CardType cardType, Client client, Account account) {
        String cardHolder = client.getFirstName() + " " + client.getLastName();
        String cardNumber = "1215 1532 7818 4135";
        int cvv = 255;
        String expiryDate = "12/30";
        return new Card(null, cardType, cardNumber, cardHolder, cvv, expiryDate, account);
    }
}

