package kg.tilek.mib.entity;

import jakarta.persistence.*;
import kg.tilek.mib.entity.base.BaseEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class User extends BaseEntity implements UserDetails {

    private String username;

    private String password;

    private String fullName;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    @Access(AccessType.FIELD)
    private boolean valid;

    @OneToOne
    @JoinColumn(
            name = "bank_account_id",
            referencedColumnName = "id"
    )
    private BankAccount bankAccount;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return valid;
    }

    @Override
    public boolean isAccountNonLocked() {
        return valid;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return valid;
    }

    @Override
    public boolean isEnabled() {
        return valid;
    }
}
