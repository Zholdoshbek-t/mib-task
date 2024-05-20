package kg.tilek.mib.entity.repository;

import kg.tilek.mib.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Page<User> findByDateOfBirthGreaterThan(LocalDate date, Pageable pageable);
    Page<User> findByFullNameStartingWith(String fullName, Pageable pageable);
}
