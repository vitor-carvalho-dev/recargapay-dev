package br.com.recargapay.digitalwallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.recargapay.digitalwallet.entities.Wallet;
import jakarta.persistence.LockModeType;

public interface WalletRepository extends JpaRepository<Wallet, Long>{
	
	// Verificar se uma carteira com o mesmo owner ja existe. *boolean
	boolean existsByOwner(String owner);	
	
	// Utilizar locks pessimistas durante a operacao bloqueia a carteira durante a atualizacao, assim evita problemas de concorrencia.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM Wallet w WHERE w.owner = :owner")
    Optional<Wallet> findByOwnerWithLock(@Param("owner") String owner);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM Wallet w WHERE w.id = :id")
    Optional<Wallet> findByIdWithLock(@Param("id") Long id);

}
