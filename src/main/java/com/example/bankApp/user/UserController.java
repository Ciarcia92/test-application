package com.example.bankApp.user;

import com.example.bankApp.user.UserFacade;
import com.example.bankApp.user.User;
import com.example.bankApp.user.UserDto;
import com.example.bankApp.user.UserResponseDto;
import com.example.bankApp.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService, UserFacade userFacade) {
        this.userService = userService;
    }

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        log.info("getUsers - start");
        List<User> users = userService.getAllUsers();
        log.info("getUsers - success, found {} users", users.size());
        return users;
    }

    @GetMapping(path = "/{user-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(
            @PathVariable("user-id") Integer id) {
        log.info("getUser - start");
        User user = userService.getUser(id);
        log.info("getUser - success, user with id {} found", id);
        return user;

    }

    @GetMapping(path = "/dto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto getUserDtoById(
            @PathVariable("id") Integer id
    ) {
        log.info("getUserDto - start");
        UserResponseDto userResponseDto = userService.getUserDtoById(id);
        log.info("getUserDto - success, user with id {} found", id);
        return userResponseDto;
    }

    @GetMapping(path = "/fiscal-code/{fiscal-code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByFiscalCode(
            @PathVariable("fiscal-code") String fiscalCode
    ) {
        log.info("getUserByFiscalCode - start");
        User user = userService.getUserByFiscalCode(fiscalCode);
        log.info("getUserByFiscalCode - success, user with fiscal code {} found", fiscalCode);
        return user;
    }

    @GetMapping(path = "/dto", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponseDto> getAllUsersDto() {
        log.info("getAllUsersDto - start");
        List<UserResponseDto> userResponseDtos = userService.getAllUsersDto();
        log.info("getAllUsersDto - success, found {} userDto,", userResponseDtos.size());
        return userResponseDtos;
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User newUser) {
        log.info("addUser - start");
        User savedUser = userService.addUser(newUser);
        log.info("addUser - success, user with id {} created", savedUser.getId());
        return savedUser;
    }

    @PostMapping(path = "/dto/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto addUserDto(@RequestBody UserDto userDto) {
        log.info("addUserDto - start");
        UserResponseDto userResponseDto = userService.addUserResponseDto(userDto);
        log.info("addUserDto - user {} {} created", userResponseDto.firstName(), userResponseDto.lastName());
        return userResponseDto;
    }

    @DeleteMapping(path = "/{user-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("user-id") Integer id) {
        log.info("deleteUser - start - deleting user with id {}", id);
        userService.deleteUserById(id);
        log.info("deleteUser - success - deleted user with id {}", id);
    }
    @DeleteMapping(path = "/{fiscal-code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByFiscalCode(@PathVariable("fiscal-code") String fiscalCode) {
        log.info("deleteUserByFiscalCode - start - deleting user with fiscal code {}", fiscalCode);
        userService.deleteUserByFiscalCode(fiscalCode);
        log.info("deleteUserByFiscalCode - success - deleted user with fiscal code {}", fiscalCode);
    }

  /*  @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers() {
        log.info("getUsers - start");
        try {
            List<User> userList = userService.getAllUsers();
            if (userList.isEmpty()) {
                log.warn("No users found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("getUsers - success, found {} users", userList.size());
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getUsers - error occurred", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
/*
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(
            @PathVariable("id") Integer id
    ) {
        log.info("getUser - start");
        Optional<User> user = userService.getUserById(id);
        try {
            if (user.isEmpty()) {
                log.warn("getUser - user with id {} not found", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("getUser - success, user with id {} found", id);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);

        } catch (Exception e) {
            log.error("getUser - error occurred: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/
    /*
    @GetMapping(path = "/dto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> getUserDtoById(
            @PathVariable("id") Integer id
    ) {
        log.info("getUserDto - start");
        Optional<UserResponseDto> userResponseDto = userService.getUserDtoById(id);
        try {
            if (userResponseDto.isEmpty()) {
                log.warn("no user found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("getUserDto - success, user with id {} found", id);
            return new ResponseEntity<>(userResponseDto.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("getUserDto - error occurred", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    */
/*
    @GetMapping(path = "/fiscal-code/{fiscal-code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByFiscalCode(
            @PathVariable("fiscal-code") String fiscalCode
    ) {
        log.info("getUserByFiscalCode - start");
        Optional<User> user = userService.getUserByFiscalCode(fiscalCode);
        try {
            if (user.isEmpty()) {
                log.warn("getUserByFiscalCode - user with fiscal code {} not found", fiscalCode);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("getUserByFiscalCode - success, user with fiscal code {} found", fiscalCode);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("getUserByFiscalCode - error occurred: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/
    /*
    @GetMapping(path = "/dto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponseDto>> getAllUsersDto() {
        log.info("getAllUsersDto - start");
        try {
            List<UserResponseDto> userResponseDtos = userService.getAllUsersDto();
            if (userResponseDtos.isEmpty()) {
                log.warn("no users found ");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("getAllUsersDto - success, found {} userDto,", userResponseDtos.size());
            return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getAllUsersDto - error occurred: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/
    /*
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User newUser) {
        log.info("addUser - start");
        try {
            Optional<User> existingUser = userService.getUserByFiscalCode(newUser.getFiscalCode());
            if (existingUser.isPresent()) {
                log.warn("addUser - user with fiscal code {} already present", newUser.getFiscalCode());
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            User savedUser = userService.addUser(newUser);
            log.info("addUser - success, user with id {} created", savedUser.getId());
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("addUser - error occurred: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/
    /*
    @PostMapping(path = "/dto/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> addUserDto(@RequestBody UserDto userDto) {
        log.info("addUserDto - start");
        try {
            Optional<User> existingUser = userService.getUserByFiscalCode(userDto.fiscalCode());
            if (existingUser.isPresent()) {
                log.warn("addUserDto - user with fiscal code {} already present", userDto.fiscalCode());
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            UserResponseDto savedUser = userService.addUserDto(userDto);
            log.info("addUserDto - user {} {} created", savedUser.firstName(), savedUser.lastName());
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } catch (Exception e) {
            log.error("addUserDto - error occurred: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/
    /*
    @PatchMapping(path = "/bank/{user-id}")
    public ResponseEntity<User> addBankToUser(@PathVariable("user-id") Integer userId, @RequestBody Bank bank) {
        log.info("addBankToUser - start");
        User user = userService.addBankToUser(userId, bank);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
}
