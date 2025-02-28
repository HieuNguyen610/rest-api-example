package hieu.javarestapi.service;

import hieu.javarestapi.model.request.UserCreateRequest;
import hieu.javarestapi.model.request.UserSearchRequest;
import hieu.javarestapi.model.request.UserUpdateRequest;
import hieu.javarestapi.model.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long userId);

    UserResponse updateUserById(UserUpdateRequest userUpdateRequest);

    List<UserResponse> getUsersByCriterias(UserSearchRequest usersearchRequest);

    UserResponse createUser(UserCreateRequest request);
}
