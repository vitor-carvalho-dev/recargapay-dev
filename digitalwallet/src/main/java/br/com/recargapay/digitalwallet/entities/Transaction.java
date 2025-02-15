package br.com.recargapay.digitalwallet.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import br.com.recargapay.digitalwallet.audit.Auditable;
import br.com.recargapay.digitalwallet.enums.TransactionType;

import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet; // Relacionamento com a carteira

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(unique = true, nullable = false)
    private String requestId; // Identificador único para chave de idempotência

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    // Método que garante que o requestId será gerado automaticamente
    @PrePersist
    public void generateRequestId() {
        if (this.requestId == null || this.requestId.isBlank()) {
            this.requestId = UUID.randomUUID().toString();
        }
    }
}


