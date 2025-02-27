package de.telran.bankapp.controller;

import de.telran.bankapp.dto.AccountCreateDto;
import de.telran.bankapp.dto.AccountDto;
import de.telran.bankapp.dto.AccountPostCreateDto;
import de.telran.bankapp.entity.enums.CurrencyCode;
import de.telran.bankapp.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }


    @GetMapping("/all")
    public List<AccountDto> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    AccountDto getAccountById(@PathVariable Long id) {
        return service.getAccountById(id);
    }

    @GetMapping("/currencyCode/{currencyCode}")
    public List<AccountDto> getAllAccountsByCurrencyCode(@PathVariable CurrencyCode currencyCode) {
        return service.getAllAccountsByCurrencyCode(currencyCode);
    }

    @GetMapping("/search")
    public List<AccountDto> getAllAccountsByBalance(
            @RequestParam(name = "minValue", required = false, defaultValue = "0") BigDecimal minValue,
            @RequestParam(name = "maxValue", required = false, defaultValue = "1000000") BigDecimal maxValue) {
        return service.getAllAccountsByBalance(minValue, maxValue);
    }


    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody @Valid AccountPostCreateDto dto) {
        AccountDto accountDto = service.create(dto);
        return new ResponseEntity<>(accountDto, HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<AccountDto> updateAccount(@RequestBody @Valid AccountDto dto) {
        AccountDto accountDto = service.updateAccount(dto);
        return new ResponseEntity<>(accountDto, HttpStatus.ACCEPTED);
    }

    @PutMapping("/new")
    public AccountDto createNewAccount(@RequestBody @Valid AccountCreateDto dto) {
        return service.create(dto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
        return "success";
    }

}
