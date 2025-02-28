package hieu.javarestapi.controller;

import hieu.javarestapi.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @GetMapping("/mock/list")
    public Map<String, Object> getUsersList() {
        Map<String, Object> result = new LinkedHashMap<>();

        UserResponse userResponse1 = UserResponse.builder().

                build();
        UserResponse userResponse2 = UserResponse.builder().build();

        List<UserResponse> userList = new ArrayList<>();
        userList.add(userResponse1);
        userList.add(userResponse2);

        result.put("status", HttpStatus.OK);
        result.put("message", "User list");
        result.put("data", userList);

        return result;
    }
}
