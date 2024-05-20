package kg.tilek.mib.service.mib.search;

import kg.tilek.mib.common.exception.NotFoundException;
import kg.tilek.mib.entity.repository.UserRepository;
import kg.tilek.mib.service.mapper.UserMapper;
import kg.tilek.mib.service.mib.search.dto.SearchDto;
import kg.tilek.mib.service.mib.search.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static kg.tilek.mib.common.enums.SystemCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;

    public UserDto searchByPhoneNumber(String phoneNumber) {

        var uuid = UUID.randomUUID().toString();

        log.info("REQUEST --> {} | SearchService.searchByPhoneNumber : {}", uuid, phoneNumber);

        var user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> {
                    log.error("RESPONSE -> {} | SearchService.searchByPhoneNumber : {}",
                            uuid, USER_NOT_FOUND.getMessage());
                    return new NotFoundException(USER_NOT_FOUND);
                });

        var response = UserMapper.mapToUserDto(user);

        log.info("RESPONSE -> {} | SearchService.searchByPhoneNumber : {}", uuid, response);

        return response;
    }

    public UserDto searchByEmail(String email) {

        var uuid = UUID.randomUUID().toString();

        log.info("REQUEST --> {} | SearchService.searchByEmail : {}", uuid, email);

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("RESPONSE -> {} | SearchService.searchByEmail : {}",
                            uuid, USER_NOT_FOUND.getMessage());
                    return new NotFoundException(USER_NOT_FOUND);
                });

        var response = UserMapper.mapToUserDto(user);

        log.info("RESPONSE -> {} | SearchService.searchByEmail : {}", uuid, response);

        return response;
    }

    public List<UserDto> searchByDate(SearchDto searchDto) {

        var uuid = UUID.randomUUID().toString();

        log.info("REQUEST --> {} | SearchService.searchByDate : {}", uuid, searchDto);

        var paging =
                PageRequest.of(searchDto.getPageNumber(), searchDto.getPageSize(), Sort.by(searchDto.getOrders()));

        var response = userRepository.findByDateOfBirthGreaterThan(searchDto.getDate(), paging).stream()
                .map(UserMapper::mapToUserDto)
                .toList();

        log.info("RESPONSE -> {} | SearchService.searchByDate : {}", uuid, response);

        return response;
    }

    public List<UserDto> searchByFullName(SearchDto searchDto) {

        var uuid = UUID.randomUUID().toString();

        log.info("REQUEST --> {} | SearchService.searchByFullName : {}", uuid, searchDto);

        var paging =
                PageRequest.of(searchDto.getPageNumber(), searchDto.getPageSize(), Sort.by(searchDto.getOrders()));

        var response = userRepository.findByFullNameStartingWith(searchDto.getFullName(), paging).stream()
                .map(UserMapper::mapToUserDto)
                .toList();

        log.info("RESPONSE -> {} | SearchService.searchByFullName : {}", uuid, response);

        return response;
    }
}
