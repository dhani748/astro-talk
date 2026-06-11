package com.astrotalk.security;

import com.astrotalk.entity.Astrologer;
import com.astrotalk.entity.Role;
import com.astrotalk.entity.User;
import com.astrotalk.repository.AstrologerRepository;
import com.astrotalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AstrologerRepository astrologerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getPassword(), List.of(() -> "ROLE_" + user.getRole().name()));
        }

        Astrologer astrologer = astrologerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                astrologer.getEmail(), astrologer.getPassword(),
                List.of(() -> "ROLE_" + astrologer.getRole().name()));
    }
}
