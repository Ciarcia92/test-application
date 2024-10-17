package com.example.bankApp.user;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toUser(UserDto userDto) {
        if (userDto == null) {
            throw new NullPointerException("The user dto that is trying to add is null");
        }
        User user = new User();
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setDateOfBirth(userDto.dateOfBirth());
        user.setEmail(userDto.email());
        user.setFiscalCode(userDto.fiscalCode());
        user.setAddress(userDto.address());
        user.setCreatedDate(userDto.created());
        return user;
    }
//    public UserDtoToBank toUserDto(User user) {
//        if (user == null) {
//            throw new NullPointerException("The user dto that is trying to add is null");
//        }
//        UserDtoToBank dto = new UserDtoToBank();
//        dto.
//    }

    public UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth(),
                user.getEmail(),
                user.getFiscalCode(),
                user.getAddress(),
                user.getCreatedDate()
        );
    }
}
