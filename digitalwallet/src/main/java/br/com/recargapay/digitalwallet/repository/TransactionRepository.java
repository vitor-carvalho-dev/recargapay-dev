package br.com.recargapay.digitalwallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.recargapay.digitalwallet.entities.Transaction;
import br.com.recargapay.digitalwallet.entities.Wallet;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.time.LocalTime;

// Utilizei uma query SQL personalizada para somar os valores diretamente no banco de dados.
// Reduzindo a quantidade de dados transferidos para a aplicacao.
// A query calcula o saldo no banco, reduzindo o volume de dados trafegados para a aplicacao.


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	// Verifica se uma transacao com o requestId ja existe.
    boolean existsByRequestId(String requestId);

    // Calcula o saldo acumulado ate uma data e hora especifica
    @Query("SELECT COALESCE(SUM(CASE " +
           "WHEN t.type = 'DEPOSIT' OR t.type = 'TRANSFER' THEN t.amount " +
           "WHEN t.type = 'WITHDRAW' THEN -t.amount ELSE 0 END), 0) " +
           "FROM Transaction t WHERE t.wallet = :wallet AND " +
           "(t.date < :date OR (t.date = :date AND t.time <= :time))")
    BigDecimal calculateBalanceUpTo(@Param("wallet") Wallet wallet, 
                                    @Param("date") LocalDate date, 
                                    @Param("time") LocalTime time);
}