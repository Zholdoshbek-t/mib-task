package kg.tilek.mib.service.mib.search.dto;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import kg.tilek.mib.entity.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userId;

    private String username;

    private String password;

    private String fullName;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String accNumber;

    private BigDecimal balance;
}
