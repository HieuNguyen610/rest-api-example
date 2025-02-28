package hieu.javarestapi.controller;

import hieu.javarestapi.common.Gender;
import hieu.javarestapi.model.request.UserCreateRequest;
import hieu.javarestapi.model.request.UserSearchRequest;
import hieu.javarestapi.model.request.UserUpdateRequest;
import hieu.javarestapi.model.response.UserResponse;
import hieu.javarestapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "USER-CONTROLLER")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    @Operation(summary = "Get user list", description = "get all users")
    public Map<String, Object> getUsersList() {
        log.info("get users list");
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK);
        result.put("message", "User list");
        result.put("data", userService.getAllUsers());
        return result;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user details", description = "Get user details by userId")
    public Map<String, Object> getUserDetailById(@PathVariable("userId") Long userId) {
        log.info("get user id: {}", userId);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK);
        result.put("message", "User details");
        result.put("data", userService.getUserById(userId));
        return result;
    }

    @GetMapping("/search")
    public Map<String, Object> findUsers (@RequestParam UserSearchRequest usersearchRequest) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK);
        result.put("message", "Search users");
        result.put("data", userService.getUsersByCriterias(usersearchRequest));
        return result;
    }

    @PostMapping("/create")
    @Operation(summary = "Create new user", description = "Create new user")
    public Map<String, Object> createUser(@RequestBody UserCreateRequest request) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK);
        result.put("message", "User created");
        result.put("data", userService.createUser(request));
        return result;
    }

    @PutMapping("/update")
    @Operation(summary = "Update user details", description = "Update user details by userId")
    public Map<String, Object> updateUserDetailById(@RequestBody UserUpdateRequest userUpdateRequest) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK);
        result.put("message", "User details updated");
        result.put("data", userService.updateUserById(userUpdateRequest));
        return result;
    }

    @GetMapping("/mock/list")
    public Map<String, Object> getUsersListMock() {
        Map<String, Object> result = new LinkedHashMap<>();

        UserResponse userResponse1 = UserResponse.builder().
                id(1L).
                firstName("John").
                lastName("Doe").
                email("john.doe@example.com").
                phone("1234567890").
                gender(Gender.MALE).
                birthday(new Date()).
                build();

        UserResponse userResponse2 = UserResponse.builder().
                id(2L).
                firstName("John").
                lastName("Terry").
                email("john.terry26@example.com").
                phone("1234567890").
                gender(Gender.MALE).
                birthday(new Date()).
                build();

        List<UserResponse> userList = new ArrayList<>();
        userList.add(userResponse1);
        userList.add(userResponse2);

        result.put("status", HttpStatus.OK);
        result.put("message", "User list");
        result.put("data", userList);

        return result;
    }
}
