package kg.tilek.mib.service.mib.transaction;

import kg.tilek.mib.api.dto.BaseResponse;
import kg.tilek.mib.common.exception.NotFoundException;
import kg.tilek.mib.common.exception.ServerErrorException;
import kg.tilek.mib.entity.repository.BankAccountRepository;
import kg.tilek.mib.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static kg.tilek.mib.common.enums.SystemCode.*;
import static kg.tilek.mib.common.enums.SystemCode.SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final Lock lock = new ReentrantLock();

    @Transactional
    public BaseResponse makeTransaction(Long senderId, Long receiverId, BigDecimal sum) {

        var uuid = UUID.randomUUID().toString();

        log.info("REQUEST --> {} | TransactionService.makeTransaction : {}, {}, {}",
                uuid, senderId, receiverId, sum);

        lock.lock();

        try {

            var sender = userRepository.findById(senderId)
                    .orElseThrow(() -> {
                        log.error("RESPONSE -> {} | TransactionService.makeTransaction : {}",
                            uuid, USER_NOT_FOUND.getMessage());
                        return new NotFoundException(USER_NOT_FOUND);
                    });

            var receiver = userRepository.findById(receiverId)
                    .orElseThrow(() -> {
                        log.error("RESPONSE -> {} | TransactionService.makeTransaction : {}",
                            uuid, USER_NOT_FOUND.getMessage());
                        return new NotFoundException(USER_NOT_FOUND);
                    });

            var senderAccount = sender.getBankAccount();
            var receiverAccount = receiver.getBankAccount();

            if(senderAccount.getBalance().subtract(sum).compareTo(BigDecimal.ZERO) < 0) {
                log.error("RESPONSE -> {} | TransactionService.makeTransaction : {}",
                        uuid, NOT_ENOUGH_MONEY.getMessage());
                throw new ServerErrorException(NOT_ENOUGH_MONEY);
            }

            senderAccount.setBalance(senderAccount.getBalance().subtract(sum));
            receiverAccount.setBalance(receiverAccount.getBalance().add(sum));

            bankAccountRepository.save(senderAccount);
            bankAccountRepository.save(receiverAccount);

            log.info("RESPONSE -> {} | TransactionService.makeTransaction : {}", uuid, SUCCESS.getMessage());

            return BaseResponse.builder().code(SUCCESS.getCode()).message(SUCCESS.getMessage()).build();

        } finally {
            lock.unlock();
        }
    }
}
