package de.telran.bankapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardCreateDto {
    @NotNull(message = "{validation.card.clientId}")
    @Pattern(regexp = "^[a-f0-9\\-]{36}$", message = "{validation.card.clientId}")
    private String clientId;

    @NotBlank(message = "validation.card.cardType")
    private String cardType;

}
