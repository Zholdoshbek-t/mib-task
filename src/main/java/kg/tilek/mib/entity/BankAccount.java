package kg.tilek.mib.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kg.tilek.mib.entity.base.BaseEntity;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_bank_account")
public class BankAccount extends BaseEntity {

    private String accNumber;

    private BigDecimal balance;
}
