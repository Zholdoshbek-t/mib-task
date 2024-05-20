package kg.tilek.mib.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.tilek.mib.service.mib.search.SearchService;
import kg.tilek.mib.service.mib.search.dto.SearchDto;
import kg.tilek.mib.service.mib.search.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kg.tilek.mib.common.constants.Endpoints.SEARCH_URL;

@RestController
@RequestMapping(SEARCH_URL)
@RequiredArgsConstructor
@Tag(name = "Search API", description = "API поиска")
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "Найти пользователя по номеру телефона")
    @GetMapping("/phone-number")
    public ResponseEntity<UserDto> searchByPhoneNumber(@RequestParam String phoneNumber) {

        return ResponseEntity.ok(searchService.searchByPhoneNumber(phoneNumber));
    }

    @Operation(summary = "Найти пользователя по почте")
    @GetMapping("/email")
    public ResponseEntity<UserDto> searchByEmail(@RequestParam String email) {

        return ResponseEntity.ok(searchService.searchByEmail(email));
    }

    @Operation(summary = "Найти пользователей по дате рождения")
    @GetMapping("/date")
    public ResponseEntity<List<UserDto>> searchByDate(@RequestBody SearchDto searchDto) {

        return ResponseEntity.ok(searchService.searchByDate(searchDto));
    }

    @Operation(summary = "Найти пользователей по ФИО")
    @GetMapping("/full-name")
    public ResponseEntity<List<UserDto>> searchByFullName(@RequestBody SearchDto searchDto) {

        return ResponseEntity.ok(searchService.searchByFullName(searchDto));
    }
}
