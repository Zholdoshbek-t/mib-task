package kg.tilek.mib.entity.repository;

import kg.tilek.mib.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByAccNumber(String accNumber);
}
