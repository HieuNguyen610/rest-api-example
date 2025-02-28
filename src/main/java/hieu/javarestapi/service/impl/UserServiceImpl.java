package hieu.javarestapi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hieu.javarestapi.model.entity.UserEntity;
import hieu.javarestapi.model.request.UserCreateRequest;
import hieu.javarestapi.model.request.UserSearchRequest;
import hieu.javarestapi.model.request.UserUpdateRequest;
import hieu.javarestapi.model.response.UserResponse;
import hieu.javarestapi.repository.UserRepository;
import hieu.javarestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream().map(userEntity -> objectMapper.convertValue(userEntity, UserResponse.class))
                .toList();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        return convertEntityToResponse(getUserEntity(userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse updateUserById(UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest.getId() != null) {
            UserEntity updatedUser = getUserEntity(userUpdateRequest.getId());
            updatedUser.setFirstName(userUpdateRequest.getFirstName());
            updatedUser.setLastName(userUpdateRequest.getLastName());
            updatedUser.setEmail(userUpdateRequest.getEmail());
            updatedUser.setPhone(userUpdateRequest.getPhone());
            updatedUser.setUsername(userUpdateRequest.getUsername());
            userRepository.save(updatedUser);
            return objectMapper.convertValue(updatedUser, UserResponse.class);

        }
        return null;
    }

    @Override
    public List<UserResponse> getUsersByCriterias(UserSearchRequest request) {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream().map(userEntity -> objectMapper.convertValue(userEntity, UserResponse.class)).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse createUser(UserCreateRequest request) {
        UserEntity userEntity = UserEntity.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .username(request.getUsername())
                .phone(request.getPhone())
                .gender(request.getGender())
                .birthday(request.getBirthday())
                .build();
        UserEntity savedUser = userRepository.save(userEntity);
        return objectMapper.convertValue(savedUser, UserResponse.class);
    }

    private UserEntity getUserEntity(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );
    }

    private UserResponse convertEntityToResponse(UserEntity entity) {
        return objectMapper.convertValue(entity, UserResponse.class);
    }
}
