package kg.tilek.mib.service.mapper;

import kg.tilek.mib.entity.User;
import kg.tilek.mib.service.mib.search.dto.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static UserDto mapToUserDto(User user) {

        return UserDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .accNumber(user.getBankAccount().getAccNumber())
                .balance(user.getBankAccount().getBalance())
                .build();
    }
}
