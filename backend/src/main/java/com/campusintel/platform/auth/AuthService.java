package com.campusintel.platform.auth;

import com.campusintel.platform.user.Role;
import com.campusintel.platform.user.RoleRepository;
import com.campusintel.platform.user.User;
import com.campusintel.platform.user.UserRepository;
import com.campusintel.platform.user.UserRole;
import com.campusintel.platform.user.UserRoleId;
import com.campusintel.platform.user.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserRoleRepository userRoleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(String email, String password, String displayName) {

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        String passwordHash = passwordEncoder.encode(password);

        User user = new User(
                email,
                passwordHash,
                displayName
        );

        userRepository.save(user);

        Role studentRole = roleRepository.findByName("STUDENT")
                .orElseThrow(() ->
                        new IllegalStateException("STUDENT role not found")
                );

        UserRoleId userRoleId = new UserRoleId(
                user.getId(),
                studentRole.getId()
        );

        userRoleRepository.save(
                new UserRole(userRoleId)
        );

        return jwtService.generateToken(
                user.getEmail(),
                studentRole.getName()
        );
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                password,
                user.getPasswordHash()
        )) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        Role role = roleRepository.findByName("STUDENT")
                .orElseThrow(() ->
                        new IllegalStateException("STUDENT role not found")
                );

        user.setLastLoginAt(
                java.time.LocalDateTime.now()
        );

        userRepository.save(user);

        return jwtService.generateToken(
                user.getEmail(),
                role.getName()
        );
    }
}