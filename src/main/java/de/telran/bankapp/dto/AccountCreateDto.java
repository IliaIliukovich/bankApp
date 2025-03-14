package de.telran.bankapp.dto;

import de.telran.bankapp.entity.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountCreateDto {

    @Pattern(regexp = "^[a-f0-9\\-]{36}$", message = "{validation.account.clientId}")
    private String clientId;

    @NotNull(message = "package de.telran.onlineshopgarden.controller;\n" +
            "\n" +
            "import de.telran.onlineshopgarden.entity.Category;\n" +
            "import de.telran.onlineshopgarden.service.CategoryService;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.http.ResponseEntity;\n" +
            "import org.springframework.web.bind.annotation.GetMapping;\n" +
            "import org.springframework.web.bind.annotation.PathVariable;\n" +
            "import org.springframework.web.bind.annotation.RequestMapping;\n" +
            "import org.springframework.web.bind.annotation.RestController;\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "@RestController\n" +
            "@RequestMapping(\"/categories\")\n" +
            "public class CategoryController {\n" +
            "    private final CategoryService service;\n" +
            "\n" +
            "    @Autowired\n" +
            "    public CategoryController(CategoryService service) {\n" +
            "        this.service = service;\n" +
            "    }\n" +
            "\n" +
            "    @GetMapping\n" +
            "    public ResponseEntity<List<Category>> getAll() {\n" +
            "\n" +
            "        return ResponseEntity.ok(service.getAll());\n" +
            "    }\n" +
            "\n" +
            "    @GetMapping(\"/{id}\")\n" +
            "    public ResponseEntity<Category> getById(@PathVariable Integer id) {\n" +
            "\n" +
            "        return ResponseEntity.ok(service.getById(id));\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "// Добавление новой категории товаров\n" +
            "//URL: /categories\n" +
            "//Метод: POST\n" +
            "//Тело запроса:\n" +
            "//{\n" +
            "//  \"categoryId\": \"string\",\n" +
            "//  \"category\": \"string\"\n" +
            "//}\n" +
            "//Ожидаемые HTTP статусы: 201 Created, 400 Bad Request\n" +
            "\n" +
            "\n" +
            "//Редактирование категории товаров\n" +
            "//URL: /categories/{categoryId}\n" +
            "//Метод: PUT\n" +
            "//Тело запроса:\n" +
            "//{\n" +
            "//  \"categoryId\": \"string\",\n" +
            "//  \"category\": \"string\"\n" +
            "//}\n" +
            "//Ожидаемые HTTP статусы: 200 OK, 400 Bad Request, 404 Not Found{validation.account.productId}")
    @Positive(message = "{validation.account.productId}")
    private Long productId;

    @NotNull(message = "{validation.account.initialAmount}")
    private BigDecimal initialAmount;


    @NotNull(message = "{validation.account.type}")
    private AccountType accountType;

}
