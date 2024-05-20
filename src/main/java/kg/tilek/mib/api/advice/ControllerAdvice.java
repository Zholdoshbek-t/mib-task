package kg.tilek.mib.api.advice;

import kg.tilek.mib.api.dto.BaseResponse;
import kg.tilek.mib.common.enums.SystemCode;
import kg.tilek.mib.common.exception.AlreadyExistsException;
import kg.tilek.mib.common.exception.NotFoundException;
import kg.tilek.mib.common.exception.ServerErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    private BaseResponse createRestResponse(SystemCode systemCode) {
        return BaseResponse.builder()
                .code(systemCode.getCode())
                .message(systemCode.getMessage())
                .build();
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<BaseResponse> handleAlreadyExistsException(AlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(createRestResponse(exception.getSystemCode()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.badRequest().body(createRestResponse(exception.getSystemCode()));
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<BaseResponse> handleServerErrorException(ServerErrorException exception) {
        return ResponseEntity.badRequest().body(createRestResponse(exception.getSystemCode()));
    }
}
