package br.com.recargapay.digitalwallet.entities;

import java.math.BigDecimal;

import br.com.recargapay.digitalwallet.audit.Auditable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "wallets", uniqueConstraints = @UniqueConstraint(columnNames = "owner")) // indice unico no banco de dados.
@Data
public class Wallet extends Auditable {	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private BigDecimal balance;

    @Version
    private Long version;

}
