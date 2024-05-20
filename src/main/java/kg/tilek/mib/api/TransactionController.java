package kg.tilek.mib.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.tilek.mib.api.dto.BaseResponse;
import kg.tilek.mib.service.mib.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static kg.tilek.mib.common.constants.Endpoints.TRANSACTION_URL;

@RestController
@RequestMapping(TRANSACTION_URL)
@RequiredArgsConstructor
@Tag(name = "Transaction API", description = "API транзакций")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Совершить транзакцию")
    @PostMapping
    public ResponseEntity<BaseResponse> makeTransaction(@RequestParam Long senderId,
                                                        @RequestParam Long receiverId,
                                                        @RequestParam BigDecimal sum) {

        return ResponseEntity.ok(transactionService.makeTransaction(senderId, receiverId, sum));
    }
}
