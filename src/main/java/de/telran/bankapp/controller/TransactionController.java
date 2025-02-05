package de.telran.bankapp.controller;

import de.telran.bankapp.entity.Transaction;
import de.telran.bankapp.entity.enums.TransactionStatus;
import de.telran.bankapp.entity.enums.TransactionType;
import de.telran.bankapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
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

    @GetMapping("{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String id) {
        Optional<Transaction> optional = service.getTransactionById(id);
        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        Transaction added = service.addTransaction(transaction);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }


    @PatchMapping
    public ResponseEntity<Transaction> changeStatus(@RequestParam String transactionId, @RequestParam TransactionStatus transactionStatus) {
        Optional<Transaction> changedStatus = service.changeStatusById(transactionId, transactionStatus);
        if (changedStatus.isPresent()) {
            return new ResponseEntity<>(changedStatus.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Transaction>> findTransactionsByTypeAndAmount(@RequestParam TransactionType type, @RequestParam BigDecimal minAmount) {
        List<Transaction> transactions = service.findTransactionsByTypeAndAmount(type, minAmount);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteNewTransactions() {
        service.deleteNewTransactions();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  //??? if status 'New' not exists -> 204 NO CONTENT
    }

    @PutMapping
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) {
        Optional<Transaction> updated = service.updateTransaction(transaction);
        if (updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
