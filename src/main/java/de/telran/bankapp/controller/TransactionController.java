package de.telran.bankapp.controller;

import de.telran.bankapp.dto.TransactionCreateDto;
import de.telran.bankapp.dto.TransactionDto;
import de.telran.bankapp.dto.TransactionTransferDto;
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
import java.util.Optional;

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
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        return new ResponseEntity<>(service.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public Optional<TransactionDto> getTransactionById(
            @PathVariable @Pattern(regexp = UUID_REGEX, message = "{validation.transaction.id}") String id) {
        return service.getTransactionById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TransactionDto>> findTransactionsByTypeAndAmount(
            @RequestParam TransactionType type,
            @RequestParam BigDecimal minAmount) {
        List<TransactionDto> transactions = service.findTransactionsByTypeAndAmount(type, minAmount);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/searchTransactionsByType/{type}")
    public ResponseEntity<List<TransactionDto>> searchTransactionsByType(@PathVariable TransactionType type) {
        List<TransactionDto> transactions = service.findTransactionsByType(type);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/searchTransactionsByStatusNotAndAmountBetween")
    public ResponseEntity<List<TransactionDto>> searchTransactionsByStatusNotAndAmountBetween(
            @RequestParam TransactionStatus status,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount) {
        List<TransactionDto> transactions = service.findTransactionByStatusNotAndAmountBetween(status, minAmount, maxAmount);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/searchTransactionsByTypeAndByStatus")
    public ResponseEntity<List<TransactionDto>> searchTransactionsByTypeAndByStatus(
            @RequestParam TransactionType type,
            @RequestParam TransactionStatus status) {
        List<TransactionDto> transactions = service.findTransactionsByTypeAndByStatus(type, status);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransactionDto> addTransaction(@RequestBody @Valid TransactionCreateDto transaction) {
        TransactionDto added = service.addTransaction(transaction);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<TransactionDto> updateTransaction(@RequestBody @Valid TransactionDto transaction) {
        return new ResponseEntity<>(service.updateTransaction(transaction), HttpStatus.ACCEPTED);
    }

    @PutMapping("/transfer")
    public ResponseEntity<Void> transferMoney(@RequestBody @Valid TransactionTransferDto dto) {
        service.transferMoney(dto);
        return ResponseEntity.ok().build();
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
