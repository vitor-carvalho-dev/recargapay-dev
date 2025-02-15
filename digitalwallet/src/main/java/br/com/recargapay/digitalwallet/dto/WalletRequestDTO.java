package br.com.recargapay.digitalwallet.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WalletRequestDTO {

	@NotBlank(message = "Owner name cannot be blank")
    private String owner;
}
