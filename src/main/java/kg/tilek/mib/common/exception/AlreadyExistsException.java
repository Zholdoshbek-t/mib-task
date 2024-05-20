package kg.tilek.mib.common.exception;

import kg.tilek.mib.common.enums.SystemCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlreadyExistsException extends RuntimeException {

    private SystemCode systemCode;
}
