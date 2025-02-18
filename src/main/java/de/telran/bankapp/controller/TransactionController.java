package de.telran.bankapp.controller;

import de.telran.bankapp.entity.Transaction;
import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.entity.enums.TransactionType;
import de.telran.bankapp.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transaction")
@Validated
public class TransactionController {

    private static final String UUID_REGEX = "^[a-f0-9\\-]{36}$";

    private TransactionService service;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return new ResponseEntity<>(service.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Transaction> getTransactionById(
            @PathVariable @Pattern(regexp = UUID_REGEX, message = "{validation.transaction.id}") String id) {
        return new ResponseEntity<>(service.getTransactionById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Transaction>> findTransactionsByTypeAndAmount(
            @RequestParam TransactionType type,
            @RequestParam BigDecimal minAmount) {
        List<Transaction> transactions = service.findTransactionsByTypeAndAmount(type, minAmount);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/searchTransactionsByType/{type}")
    public ResponseEntity<List<Transaction>> searchTransactionsByType(@PathVariable TransactionType type) {
        List<Transaction> transactions = service.findTransactionsByType(type);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/searchTransactionsByStatusNotAndAmountBetween")
    public ResponseEntity<List<Transaction>> searchTransactionsByStatusNotAndAmountBetween(
            @RequestParam TransactionStatus status,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount) {
        List<Transaction> transactions = service.findTransactionByStatusNotAndAmountBetween(status, minAmount, maxAmount);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/searchTransactionsByTypeAndByStatus")
    public ResponseEntity<List<Transaction>> searchTransactionsByTypeAndByStatus(
            @RequestParam TransactionType type,
            @RequestParam TransactionStatus status) {
        List<Transaction> transactions = service.findTransactionsByTypeAndByStatus(type, status);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody @Valid Transaction transaction) {
        Transaction added = service.addTransaction(transaction);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Transaction> updateTransaction(@RequestBody @Valid Transaction transaction) {
        return new ResponseEntity<>(service.updateTransaction(transaction), HttpStatus.ACCEPTED);
    }


    @PatchMapping
    public ResponseEntity<Void> changeStatus(
            @RequestParam @Pattern(regexp = UUID_REGEX, message = "{validation.transaction.id}") String id,
            @RequestParam TransactionStatus status) {
        service.changeStatusById(id, status);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteNewTransactions() {
        service.deleteNewTransactions();
        return ResponseEntity.accepted().build();
    }
}
