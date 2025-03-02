package hieu.javarestapi.service;

import hieu.javarestapi.model.request.UserCreateRequest;
import hieu.javarestapi.model.request.UserUpdateRequest;
import hieu.javarestapi.model.response.UserPageResponse;
import hieu.javarestapi.model.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long userId);

    UserResponse updateUserById(UserUpdateRequest userUpdateRequest);

    List<UserResponse> getUsersByCriterias(int page, int pageSize, String keyword);

    UserResponse createUser(UserCreateRequest request);

    UserPageResponse findAll(String keyword, String sort, int page, int size);
}
