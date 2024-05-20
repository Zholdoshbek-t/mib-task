package kg.tilek.mib.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.tilek.mib.api.dto.BaseResponse;
import kg.tilek.mib.service.mib.user.UserService;
import kg.tilek.mib.service.mib.user.dto.UserRegDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kg.tilek.mib.common.constants.Endpoints.USER_URL;

@RestController
@RequestMapping(USER_URL)
@RequiredArgsConstructor
@Tag(name = "User API", description = "API пользователей")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Создать пользователя и его счет")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createAccount(@RequestBody UserRegDto userRegDto) {
        return ResponseEntity.ok(userService.createAccount(userRegDto));
    }

    @Operation(summary = "Изменить номер телефона")
    @PutMapping("/set/phone-number")
    public ResponseEntity<BaseResponse> setPhoneNumber(@RequestParam Long userId,
                                                       @RequestParam String phoneNumber) {
        return ResponseEntity.ok(userService.setPhoneNumber(userId, phoneNumber));
    }

    @Operation(summary = "Удалить номер телефона")
    @PutMapping("/remove/phone-number")
    public ResponseEntity<BaseResponse> removePhoneNumber(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.removePhoneNumber(userId));
    }

    @Operation(summary = "Изменить почту")
    @PutMapping("/set/email")
    public ResponseEntity<BaseResponse> setEmail(@RequestParam Long userId,
                                                 @RequestParam String phoneNumber) {
        return ResponseEntity.ok(userService.setEmail(userId, phoneNumber));
    }

    @Operation(summary = "Удалить почту")
    @PutMapping("/remove/email")
    public ResponseEntity<BaseResponse> removeEmail(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.removeEmail(userId));
    }
}
