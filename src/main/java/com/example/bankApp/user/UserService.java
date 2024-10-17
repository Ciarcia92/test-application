package com.example.bankApp.user;

import com.example.bankApp.exceptions.DuplicateResourceException;
import com.example.bankApp.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            log.warn("No users found");
            throw new ResourceNotFoundException("No users found");
        }
        return users;
    }

    public User getUser(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = String.format("User with ID %d not found", id);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });
    }

    public UserResponseDto getUserDtoById(Integer id) {
        return userRepository.findById(id).map(userMapper::toUserResponseDto)
                .orElseThrow(() -> {
                    String errorMsg = String.format("User with ID %d not found", id);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });
    }

    public User getUserByFiscalCode(String fiscalCode) {
        return userRepository.findUserByFiscalCodeContaining(fiscalCode)
                .orElseThrow(() -> {
                    String errorMsg = String.format("User with fiscal code %s not found", fiscalCode);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });
    }

    public List<UserResponseDto> getAllUsersDto() {
        List<UserResponseDto> userResponseDtos = userRepository.findAll()
                .stream().map(userMapper::toUserResponseDto)
                .collect(Collectors.toList());
        if (userResponseDtos.isEmpty()) {
            log.warn("No users found");
            throw new ResourceNotFoundException("No users found");
        }
        return userResponseDtos;
    }

    public User addUser(User newUser) {
        Optional<User> existingUser = userRepository.findUserByFiscalCode(newUser.getFiscalCode());
        if (existingUser.isPresent()) {
            String errorMsg = String.format("User with fiscal code %s already present", newUser.getFiscalCode());
            log.warn(errorMsg);
            throw new DuplicateResourceException(errorMsg);
        }
        return userRepository.save(newUser);
    }

    public UserResponseDto addUserResponseDto(UserDto userDto) {
        Optional<User> existingUser = userRepository.findUserByFiscalCode(userDto.fiscalCode());
        if (existingUser.isPresent()) {
            String errorMsg = String.format("User with fiscal code %s already present", userDto.fiscalCode());
            log.warn(errorMsg);
            throw new DuplicateResourceException(errorMsg);
        }
        User user = userMapper.toUser(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(savedUser);
    }

    public void deleteUserById(Integer id) {
        userRepository.findById(id)
                .orElseThrow(
                        () -> {
                            String errorMsg = String.format("User with ID %d not found", id);
                            log.warn(errorMsg);
                            return new ResourceNotFoundException(errorMsg);
                        });
        userRepository.deleteById(id);
        log.info("deleteUserById - deleted user with id {}", id);
    }

    public void deleteUserByFiscalCode(String fiscalCode) {
        User user = userRepository.findUserByFiscalCode(fiscalCode)
                .orElseThrow(() -> {
                    String errorMsg = String.format("User with fiscal code %s not found", fiscalCode);
                    log.warn(errorMsg);
                    return new ResourceNotFoundException(errorMsg);
                });
        userRepository.delete(user);
        log.info("deleteUserByFiscalCode - success - deleted user with fiscal code {}", fiscalCode);
    }

    public String  generateFiscalCode() {
        Random random = new Random();
        StringBuilder fiscalCode = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            if ( i <= 5 || i == 8 || i == 11 || i==15) {
                fiscalCode.append((char) ('A' + random.nextInt(26)));
            } else fiscalCode.append(random.nextInt(10));
        } return fiscalCode.toString();
    }
//    public User addCardToUser(Integer userId, CreditCard card) {
//
//    }
/*

    public Optional<UserResponseDto> getUserDtoById(Integer id) {
        return userRepository.findById(id).map(userMapper::toUserResponseDto);
    }


    public Optional<User> getUserByFiscalCode(String fiscalCode) {
        return userRepository.findUserByFiscalCodeContaining(fiscalCode);
    }

    public Optional<User> getUserByEmail(String fiscalCode) {
        return userRepository.findUserByEmailContaining(fiscalCode);
    }

    public Optional<User> getUserByEmailOrFiscalCode(String email, String fiscalCode) {
        return userRepository.findByEmailOrFiscalCode(email, fiscalCode);
    }

    public List<UserResponseDto> getAllUsersDto() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponseDto)
                .collect(Collectors.toList());
    }

    public User addUser(User newUser) {
        return userRepository.save(newUser);
    }

    public UserResponseDto addUserDto(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(savedUser);
    }

    public User addBankToUser(Integer userId, Bank bank) {
        try {
            Optional<User> userToUpdate = userRepository.findById(userId);
            if (userToUpdate.isPresent()) {
                User user = userToUpdate.get();

                Optional<Bank> existingBank = bankService.getBankByBankNumberIdentifier(bank.getBankNumberIdentifier());
                if (existingBank.isPresent()) {
                    Bank bankToAdd = existingBank.get();
                    user.getBanks().add(bankToAdd);
                    userRepository.save(user);
                } else {
                    log.error("The bank with address {} doesn't exist in the database.", bank.getAddress());
                    return null;
                }
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("error occurred during association to the bank: ", e);
            return null;
        }
    }*/
}
