package br.com.recargapay.digitalwallet.utils;

import org.apache.commons.lang3.StringUtils;

public class ValidationUtils {
	
	// Valida se um nome é vazio ou contém caracteres inválidos
    public static void validateOwnerName(String owner) {
        if (StringUtils.isBlank(owner)) {
            throw new IllegalArgumentException("Owner name cannot be null or empty.");
        }

        if (!owner.matches("^[a-zA-Z\\s]+$")) {
            throw new IllegalArgumentException("Owner name must contain only letters and spaces.");
        }
    }
}
