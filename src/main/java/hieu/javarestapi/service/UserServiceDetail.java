package hieu.javarestapi.service;

import hieu.javarestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceDetail {

    private final UserRepository userRepository;

    public UserDetailsService UserServiceDetail() {
        return username -> userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
