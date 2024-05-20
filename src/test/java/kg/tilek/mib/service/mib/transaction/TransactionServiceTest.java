package kg.tilek.mib.service.mib.transaction;

import kg.tilek.mib.api.dto.BaseResponse;
import kg.tilek.mib.common.enums.SystemCode;
import kg.tilek.mib.common.exception.NotFoundException;
import kg.tilek.mib.common.exception.ServerErrorException;
import kg.tilek.mib.entity.BankAccount;
import kg.tilek.mib.entity.User;
import kg.tilek.mib.entity.repository.BankAccountRepository;
import kg.tilek.mib.entity.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import static kg.tilek.mib.common.enums.SystemCode.SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private ReentrantLock lock;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void makeTransaction_Success() {
        BigDecimal sum = new BigDecimal("100");

        var senderBA = BankAccount.builder()
                .accNumber("senderAcc")
                .balance(new BigDecimal("1000"))
                .build();
        senderBA.setId(1L);

        var sender = User.builder()
                .fullName("ABC")
                .dateOfBirth(LocalDate.now())
                .email("mock@email.com")
                .valid(true)
                .phoneNumber("7070707")
                .username("sender")
                .password("sender")
                .bankAccount(senderBA)
                .build();
        sender.setId(1L);

        var receiverBA = BankAccount.builder()
                .accNumber("receiverAcc")
                .balance(new BigDecimal("1000"))
                .build();
        receiverBA.setId(2L);

        var receiver = User.builder()
                .fullName("ABC")
                .dateOfBirth(LocalDate.now())
                .email("mock@email.com")
                .valid(true)
                .phoneNumber("7070707")
                .username("receiver")
                .password("receiver")
                .bankAccount(receiverBA)
                .build();
        receiver.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));

        var response = transactionService.makeTransaction(1L, 2L, sum);

        assertEquals(new BigDecimal("900"), sender.getBankAccount().getBalance());
        assertNotNull(response);
        assertNotNull(response.getMessage());
        assertEquals(SUCCESS.getMessage(), response.getMessage());
    }

    @Test
    void makeTransaction_UserNotFound() {
        Long senderId = 1L;
        Long receiverId = 2L;
        BigDecimal sum = new BigDecimal("100");

        when(userRepository.findById(senderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> transactionService.makeTransaction(senderId, receiverId, sum));

        verify(userRepository).findById(senderId);
        verify(userRepository, never()).findById(receiverId);
    }

    @Test
    void makeTransaction_InsufficientBalance() {
        BigDecimal sum = new BigDecimal("10000");

        var senderBA = BankAccount.builder()
                .accNumber("senderAcc")
                .balance(new BigDecimal("1000"))
                .build();
        senderBA.setId(1L);

        var sender = User.builder()
                .fullName("ABC")
                .dateOfBirth(LocalDate.now())
                .email("mock@email.com")
                .valid(true)
                .phoneNumber("7070707")
                .username("sender")
                .password("sender")
                .bankAccount(senderBA)
                .build();
        sender.setId(1L);

        var receiverBA = BankAccount.builder()
                .accNumber("receiverAcc")
                .balance(new BigDecimal("1000"))
                .build();
        receiverBA.setId(2L);

        var receiver = User.builder()
                .fullName("ABC")
                .dateOfBirth(LocalDate.now())
                .email("mock@email.com")
                .valid(true)
                .phoneNumber("7070707")
                .username("receiver")
                .password("receiver")
                .bankAccount(receiverBA)
                .build();
        receiver.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));


        assertThrows(ServerErrorException.class, () ->
                transactionService.makeTransaction(1L, 2L, sum));

    }
}
