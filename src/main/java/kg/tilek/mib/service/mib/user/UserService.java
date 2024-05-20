package kg.tilek.mib.service.mib.user;

import kg.tilek.mib.api.dto.BaseResponse;
import kg.tilek.mib.common.exception.AlreadyExistsException;
import kg.tilek.mib.common.exception.NotFoundException;
import kg.tilek.mib.common.exception.ServerErrorException;
import kg.tilek.mib.entity.BankAccount;
import kg.tilek.mib.entity.User;
import kg.tilek.mib.entity.repository.BankAccountRepository;
import kg.tilek.mib.entity.repository.UserRepository;
import kg.tilek.mib.service.mib.user.dto.UserRegDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static kg.tilek.mib.common.enums.SystemCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;

    public BaseResponse createAccount(UserRegDto userRegDto) {

        var uuid = UUID.randomUUID().toString();

        log.info("REQUEST --> {} | UserService.createAccount : {}", uuid, userRegDto);

        if (userRegDto.getEmail().isBlank() && userRegDto.getPassword().isBlank()) {
            log.error("RESPONSE -> {} | UserService.createAccount : {}", uuid, PARAMS_NOT_FOUND.getMessage());
            throw new NotFoundException(PARAMS_NOT_FOUND);
        } else if (userRepository.existsByEmail(userRegDto.getEmail())) {
            log.error("RESPONSE -> {} | UserService.createAccount : {}", uuid, EMAIL_EXISTS.getMessage());
            throw new AlreadyExistsException(EMAIL_EXISTS);
        } else if (userRepository.existsByUsername(userRegDto.getUsername())) {
            log.error("RESPONSE -> {} | UserService.createAccount : {}", uuid, USERNAME_EXISTS.getMessage());
            throw new AlreadyExistsException(USERNAME_EXISTS);
        } else if (userRepository.existsByPhoneNumber(userRegDto.getPhoneNumber())) {
            log.error("RESPONSE -> {} | UserService.createAccount : {}", uuid, PHONE_NUMBER_EXISTS.getMessage());
            throw new AlreadyExistsException(PHONE_NUMBER_EXISTS);
        } else if (userRegDto.getEntrySum().compareTo(BigDecimal.ZERO) <= 0) {
            log.error("RESPONSE -> {} | UserService.createAccount : {}", uuid, ENTRY_SUM_ZERO.getMessage());
            throw new ServerErrorException(ENTRY_SUM_ZERO);
        }

        var bankAccount = BankAccount.builder()
                .accNumber(UUID.randomUUID().toString())
                .balance(userRegDto.getEntrySum())
                .build();

        bankAccountRepository.save(bankAccount);

        var user = User.builder()
                .username(userRegDto.getUsername())
                .password(userRegDto.getPassword())
                .fullName(userRegDto.getFullName())
                .email(userRegDto.getEmail() != null ? userRegDto.getEmail() : "")
                .phoneNumber(userRegDto.getPhoneNumber() != null ? userRegDto.getPhoneNumber() : "")
                .dateOfBirth(userRegDto.getDateOfBirth())
                .valid(true)
                .bankAccount(bankAccount)
                .build();

        userRepository.save(user);

        log.info("RESPONSE -> {} | UserService.createAccount : {}", uuid, SUCCESS.getMessage());

        return BaseResponse.builder().code(SUCCESS.getCode()).message(SUCCESS.getMessage()).build();
    }

    public BaseResponse setPhoneNumber(Long userId, String phoneNumber) {

        var uuid = UUID.randomUUID().toString();

        log.info("REQUEST --> {} | UserService.setPhoneNumber : {}, {}", uuid, userId, phoneNumber);

        var user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("RESPONSE -> {} | UserService.setPhoneNumber : {}",
                            uuid, USER_NOT_FOUND.getMessage());
                    return new NotFoundException(USER_NOT_FOUND);
                });

        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            log.error("RESPONSE -> {} | UserService.createAccount : {}", uuid, PHONE_NUMBER_EXISTS.getMessage());
            throw new AlreadyExistsException(PHONE_NUMBER_EXISTS);
        }

        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);

        log.info("RESPONSE -> {} | UserService.createAccount : {}", uuid, SUCCESS.getMessage());

        return BaseResponse.builder().code(SUCCESS.getCode()).message(SUCCESS.getMessage()).build();
    }

    public BaseResponse removePhoneNumber(Long userId) {

        var uuid = UUID.randomUUID().toString();

        log.info("REQUEST --> {} | UserService.removePhoneNumber : {}", uuid, userId);

        var user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("RESPONSE -> {} | UserService.removePhoneNumber : {}",
                            uuid, USER_NOT_FOUND.getMessage());
                    return new NotFoundException(USER_NOT_FOUND);
                });

        if(user.getEmail() == null || user.getEmail().isBlank()) {
            log.error("RESPONSE -> {} | UserService.removePhoneNumber : {}", uuid, EMAIL_NOT_FOUND.getMessage());
            throw new NotFoundException(EMAIL_NOT_FOUND);
        }

        user.setPhoneNumber("");
        userRepository.save(user);

        log.info("RESPONSE -> {} | UserService.removePhoneNumber : {}", uuid, SUCCESS.getMessage());

        return BaseResponse.builder().code(SUCCESS.getCode()).message(SUCCESS.getMessage()).build();
    }

    public BaseResponse setEmail(Long userId, String email) {

        var uuid = UUID.randomUUID().toString();

        log.info("REQUEST --> {} | UserService.setEmail : {}, {}", uuid, userId, email);

        var user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("RESPONSE -> {} | UserService.setEmail : {}",
                            uuid, USER_NOT_FOUND.getMessage());
                    return new NotFoundException(USER_NOT_FOUND);
                });

        if (userRepository.existsByEmail(email)) {
            log.error("RESPONSE -> {} | UserService.setEmail : {}", uuid, EMAIL_EXISTS.getMessage());
            throw new AlreadyExistsException(EMAIL_EXISTS);
        }

        user.setEmail(email);
        userRepository.save(user);

        log.info("RESPONSE -> {} | UserService.setEmail : {}", uuid, SUCCESS.getMessage());

        return BaseResponse.builder().code(SUCCESS.getCode()).message(SUCCESS.getMessage()).build();
    }

    public BaseResponse removeEmail(Long userId) {

        var uuid = UUID.randomUUID().toString();

        log.info("REQUEST --> {} | UserService.removeEmail : {}", uuid, userId);

        var user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("RESPONSE -> {} | UserService.removeEmail : {}",
                            uuid, USER_NOT_FOUND.getMessage());
                    return new NotFoundException(USER_NOT_FOUND);
                });

        if(user.getPhoneNumber() == null || user.getPhoneNumber().isBlank()) {
            log.error("RESPONSE -> {} | UserService.removeEmail : {}", uuid, PHONE_NUMBER_NOT_FOUND.getMessage());
            throw new NotFoundException(PHONE_NUMBER_NOT_FOUND);
        }

        user.setEmail("");
        userRepository.save(user);

        log.info("RESPONSE -> {} | UserService.removeEmail : {}", uuid, SUCCESS.getMessage());

        return BaseResponse.builder().code(SUCCESS.getCode()).message(SUCCESS.getMessage()).build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var userDb = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        return new org.springframework.security.core.userdetails
                .User(username, userDb.getPassword(), userDb.getAuthorities());
    }
}
