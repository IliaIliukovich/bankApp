package de.telran.bankapp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/testAcc")
public class AccountController {
    List<Account> testAccount = new ArrayList<>();

    public AccountController() {

        testAccount.add(new Account(1L, "John Doe", AccountType.SAVINGS, AccountStatus.ACTIVE,
                new BigDecimal("1500.50"), CurrencyCode.USD));

            testAccount.add(new Account(2L, "Jane Smith", AccountType.CHECKING, AccountStatus.INACTIVE,
                    new BigDecimal("250.75"), CurrencyCode.EUR));

            testAccount.add(new Account(3L, "Alice Brown", AccountType.LOAN, AccountStatus.ACTIVE,
                    new BigDecimal("50000.00"), CurrencyCode.GBR));

            testAccount.add(new Account(4L, "Bob White", AccountType.DEBIT_CARD, AccountStatus.BLOCKED,
                    new BigDecimal("0.00"), CurrencyCode.USD));

            testAccount.add(new Account(5L, "Charlie Green", AccountType.CREDIT_CARD, AccountStatus.ACTIVE,
                    new BigDecimal("3200.10"), CurrencyCode.USD));

            testAccount.add(new Account(6L, "David Black", AccountType.OTHER, AccountStatus.INACTIVE,
                    new BigDecimal("125000.75"), CurrencyCode.EUR));

            testAccount.add(new Account(7L, "Eva Blue", AccountType.SAVINGS, AccountStatus.ACTIVE,
                    new BigDecimal("980.30"), CurrencyCode.EUR));

            testAccount.add(new Account(8L, "Frank Yellow", AccountType.CHECKING, AccountStatus.ACTIVE,
                    new BigDecimal("2750.00"), CurrencyCode.GBR));

            testAccount.add(new Account(9L, "Grace Orange", AccountType.DEBIT_CARD, AccountStatus.BLOCKED,
                    new BigDecimal("0.00"), CurrencyCode.USD));

            testAccount.add(new Account(10L, "Henry Purple", AccountType.CREDIT_CARD, AccountStatus.ACTIVE,
                    new BigDecimal("6800.90"), CurrencyCode.EUR));
    }

    @GetMapping("/allAcc")
    public  List<Account> getAllAccount(){return testAccount;}

    @GetMapping("{id}")
    public Optional<Account> getAccountById(@PathVariable Long id){
        return testAccount.stream().filter(account ->(
             account.getId().equals(id))).findAny();
    }

    @GetMapping("/search")
    public List<Account> findByCurrencyCode(@RequestParam CurrencyCode currencyCode){
       return testAccount.stream().filter(account -> (
           account.getCurrencyCode().equals(currencyCode))).toList();
    }

    @GetMapping("/searchBalance")
    public ResponseEntity<List<Account>> findByBalanceIn(/*@RequestParam(name="balance",required = false, defaultValue = "false")
                                             BigDecimal balance,*/
                                        @PathVariable(name="minValue",required = false) BigDecimal minValue,
                                         @PathVariable(name="maxValue",required = false) BigDecimal maxValue){
        List<Account> balanceIn = new ArrayList<>();
//        return testAccount.stream().filter(account -> (
//               (account.getBalance().compareTo(minValue)) >= 0)&&(account.getBalance().compareTo(maxValue)) <= 0).toList();
        for(Account ac : testAccount){
            if((ac.getBalance().compareTo(minValue) >= 0) && (ac.getBalance().compareTo(maxValue) <= 0)){
                balanceIn.add(ac);
            }
        }
        return ResponseEntity.ok(balanceIn) ;
    }
}




    /*
    CHECKING,
    SAVINGS,
    LOAN,
    DEBIT_CARD,
    CREDIT_CARD,
    OTHER
     ACTIVE,
    INACTIVE,
    BLOCKED
      EUR,
    USD,
    GBR
     */