package br.com.recargapay.digitalwallet.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.recargapay.digitalwallet.dto.WalletRequestDTO;
import br.com.recargapay.digitalwallet.entities.Wallet;
import br.com.recargapay.digitalwallet.service.WalletService;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/wallets")
public class WalletController {

	private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

	@Autowired
	private WalletService walletService;

	@PostMapping
	public ResponseEntity<?> createWallet(@Valid @RequestBody WalletRequestDTO request) {
		logger.info("Received request to create wallet for owner: {}", request.getOwner());

		// Wallet wallet = walletService.createWallet(request);
		// return ResponseEntity.status(HttpStatus.CREATED).body(wallet);

		try {
			Wallet wallet = walletService.createWallet(request);
			logger.info("Wallet created successfully for owner: {}", request.getOwner());
			return ResponseEntity.ok(wallet);
		} catch (Exception e) {
			logger.error("Error creating wallet for owner: {}", request.getOwner(), e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Endpoint para recuperar o saldo da carteira
	@GetMapping("/{id}/balance")
	public ResponseEntity<?> getWalletBalance(@PathVariable Long id) {
		try {
			Wallet wallet = walletService.getWalletBalance(id);
			return ResponseEntity.ok().body("Saldo atual: " + wallet.getBalance());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Endpoint para recuperar o historico da carteira
	@GetMapping("/{id}/balance/history")
	public ResponseEntity<?> getHistoricalBalance(@PathVariable Long id, @RequestParam String date,
			@RequestParam(required = false) String time) {
		try {
			LocalDate parsedDate = LocalDate.parse(date);
			LocalTime parsedTime = time != null ? LocalTime.parse(time) : LocalTime.of(23, 59, 59); // Hora padrão como
																									// 23:59:59

			BigDecimal balance = walletService.getHistoricalBalance(id, parsedDate, parsedTime);
			return ResponseEntity.ok()
					.body("Saldo em " + parsedDate + " as " + parsedTime + " valor de = $ " + balance);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
		}
	}

	// Endpoint para depositar fundos
	@PostMapping("/{id}/deposit")
	
    public ResponseEntity<?> depositFunds(
            @PathVariable Long id,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String requestId) {

        try {
            // Gerar requestId automaticamente, caso não seja fornecido
            if (requestId == null || requestId.trim().isEmpty()) {
                requestId = UUID.randomUUID().toString();
                logger.info("Generated requestId automatically: {}", requestId);
            }

            // Chama o serviço para processar o depósito
            Wallet wallet = walletService.depositFunds(id, amount, requestId);
            return ResponseEntity.ok().body("Depósito realizado com sucesso. Novo saldo: " + wallet.getBalance());

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao processar depósito: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erro interno ao processar depósito", e);
            return ResponseEntity.status(500).body("Erro interno no servidor.");
        }
    }

	// Endpoint para retirar fundos
	@PostMapping("/{id}/withdraw")
	public ResponseEntity<?> withdrawFunds(@PathVariable Long id, @RequestParam BigDecimal amount) {
		try {
			Wallet wallet = walletService.withdrawFunds(id, amount);
			return ResponseEntity.ok().body("Retirada realizada com sucesso. Novo saldo: " + wallet.getBalance());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
		}
	}

	// Endpoint para transferir fundos entre carteiras
	@PostMapping("/{sourceId}/transfer/{targetId}")
	public ResponseEntity<?> transferFunds(@PathVariable Long sourceId, @PathVariable Long targetId,
			@RequestParam BigDecimal amount) {
		try {
			walletService.transferFunds(sourceId, targetId, amount);
			return ResponseEntity.ok().body("Transferência realizada com sucesso.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
		}
	}

}
