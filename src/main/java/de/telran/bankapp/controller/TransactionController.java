package de.telran.bankapp.controller;

import de.telran.bankapp.entity.Transaction;
import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.entity.enums.TransactionType;
import de.telran.bankapp.exception.InsufficientFundsException;
import de.telran.bankapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private TransactionService service;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = service.getAllTransactions();
        if (!transactions.isEmpty()) {
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String id) {
        Optional<Transaction> optional = service.getTransactionById(id);
        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Transaction>> findTransactionsByTypeAndAmount(@RequestParam TransactionType type, @RequestParam BigDecimal minAmount) {
        List<Transaction> transactions = service.findTransactionsByTypeAndAmount(type, minAmount);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/searchTransactionsByType/{type}")
    public ResponseEntity<List<Transaction>> searchTransactionsByType(@PathVariable TransactionType type) {
        List<Transaction> transactions = service.findTransactionsByType(type);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/searchTransactionsByStatusNotAndAmountBetween")
    public ResponseEntity<List<Transaction>> searchTransactionsByStatusNotAndAmountBetween(@RequestParam TransactionStatus status, @RequestParam(required = false) BigDecimal minAmount, @RequestParam(required = false) BigDecimal maxAmount) {
        List<Transaction> transactions = service.findTransactionByStatusNotAndAmountBetween(status, minAmount, maxAmount);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/searchTransactionsByTypeAndByStatus")
    public ResponseEntity<List<Transaction>> searchTransactionsByTypeAndByStatus(@RequestParam TransactionType type, @RequestParam TransactionStatus status) {
        List<Transaction> transactions = service.findTransactionsByTypeAndByStatus(type, status);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        Transaction added = service.addTransaction(transaction);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) {
        Optional<Transaction> updated = service.updateTransaction(transaction);
        if (updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestParam Long fromId,
                                           @RequestParam Long toId,
                                           @RequestParam BigDecimal amount) {
        try {
            service.transferMoney(fromId, toId, amount);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("One or both accounts not found.");
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds for transfer.");
        }
    }


    @PatchMapping
    public ResponseEntity<Void> changeStatus(@RequestParam String id, @RequestParam TransactionStatus status) {
        Integer integer = service.changeStatusById(id, status);
        if (!integer.equals(0)) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteNewTransactions() {
        service.deleteNewTransactions();
        return ResponseEntity.accepted().build();
    }
}
